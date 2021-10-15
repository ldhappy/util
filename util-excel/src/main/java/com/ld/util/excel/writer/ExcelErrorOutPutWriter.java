package com.ld.util.excel.writer;

import com.google.common.collect.Lists;
import com.ld.util.excel.core.ColumnHeader;
import com.ld.util.excel.core.ExportColumnHeader;
import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.message.ExcelMessageSource;
import com.ld.util.excel.reader.ReadResult;
import com.ld.util.excel.util.ExcelUtil;
import com.ld.util.excel.writer.sheet.StandardSheetWriter;
import lombok.Builder;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;


/**
 * @ClassName ExcelErrorOutPutWriter
 * @Description 专门负责输出导入excel文件的错误结果
 * @Author 梁聃
 * @Date 2021/10/14 9:16
 */
@Slf4j
public class ExcelErrorOutPutWriter<R> extends AbstractExcelWriter<R>{
    /**
     * 需要书写到工作表的内容
     */
    private StandardSheetWriter standardSheetWriter;

    /**
     * 专门负责输出导入excel文件的错误结果
     * @param fileNamePre 错误导出文件前缀
     * @param readResult 导入结果，导入结果中没有错误信息时将会报错，请有错误需求时再使用本方法
     * @param columnHeaderList 导入列头，如果为空是默认使用readResult中由导入文件解析出的列头
     */
    @Builder
    public ExcelErrorOutPutWriter(String fileNamePre, ReadResult readResult,@Singular("columnHeader") List<ColumnHeader> columnHeaderList) {
        super(StringUtils.isBlank(fileNamePre)?"导入文件错误结果":fileNamePre, 1000);
        //检查数据，确保有错误内容
        TreeMap<Integer, Map<String, String>> faultRows = readResult.getFaultRows();
        if(MapUtils.isEmpty(faultRows)){
            throw ExcelException.messageException(ExcelMessageSource.READ_RESULT_FAULT_ROW_EMPTY);
        }
        //转换表头
        List<ExportColumnHeader<Map, ?>> exportColumnHeaderList = extractColumnHeader(readResult,columnHeaderList);

        standardSheetWriter = StandardSheetWriter.<Map>builder()
                .sheetName("未成功导入数据")
                .columnHeaderList(exportColumnHeaderList)
                .contentList(faultRows.entrySet().stream().map(entry->entry.getValue()).collect(Collectors.toList()))
                .build();
    }

    /**
     * 追加错误列头
     * @param readResult
     * @param columnHeaderList
     */
    private ExportColumnHeader<Map, String> addErrorInfo(ReadResult readResult, List<ColumnHeader> columnHeaderList) {
        //错误列索引
        int errorInfoFieldColumnIndex = columnHeaderList.stream().mapToInt(columnHeader->columnHeader.getCoordinateColumn()+columnHeader.getCoverageColumn()).max().getAsInt();
        int columnHeaderBeginRowIndex = columnHeaderList.stream().mapToInt(columnHeader -> columnHeader.getCoordinateRow()).min().getAsInt();
        int columnContentBeginRowIndex = columnHeaderList.stream().mapToInt(columnHeader -> columnHeader.getCoordinateRow()+columnHeader.getCoverageRow()).max().getAsInt();
        String errorInfoFieldColumnIndexStr = ExcelUtil.excelColIndexToStr(errorInfoFieldColumnIndex);
        ExportColumnHeader<Map, String> errorExportColumnHeader = ExportColumnHeader.<Map,String>exportColumnHeaderBuilder()
                .columnName("错误信息")
                .coordinate(errorInfoFieldColumnIndexStr+columnHeaderBeginRowIndex+1)
                .coverageRow(columnContentBeginRowIndex-columnHeaderBeginRowIndex)
                .columnFunction(map -> map.getOrDefault(readResult.getErrorInfoField(),"").toString())
                .build();
        errorExportColumnHeader.setHeaderStyleFunction((workbook, sheet)->{
            CellStyle cellStyle = errorExportColumnHeader.headerDefaultStyle(workbook,sheet);
            //一排显示20个字的错误信息
            sheet.setColumnWidth(errorInfoFieldColumnIndex,256*40+184);
            return cellStyle;
        });
        return errorExportColumnHeader;
    }

    /**
     * 转换表头
     * @param readResult
     * @param columnHeaderList
     * @return
     */
    private List<ExportColumnHeader<Map, ?>> extractColumnHeader(ReadResult readResult, List<ColumnHeader> columnHeaderList) {
        List<ExportColumnHeader<Map, ?>> exportColumnHeaderList = Lists.newArrayList();
        if(CollectionUtils.isEmpty(columnHeaderList)){
            columnHeaderList = readResult.getColumnHeaderList();
        }
        columnHeaderList.forEach(columnHeader ->
                exportColumnHeaderList.add(ExportColumnHeader.<Map,String>exportColumnHeaderBuilder()
                        .columnName(columnHeader.getColumnName())
                        .coordinate(columnHeader.getCoordinate())
                        .coverageRow(columnHeader.getCoverageRow())
                        .coverageColumn(columnHeader.getCoverageColumn())
                        .columnFunction(map -> map.getOrDefault(ExcelUtil.excelColIndexToStr(columnHeader.getCoordinateColumn()),"").toString())
                        .build()));
        //追加错误列头
        exportColumnHeaderList.add(addErrorInfo(readResult,columnHeaderList));
        return exportColumnHeaderList;
    }


    /**
     * 在扩展类中写入每个工作表的内容
     *
     * @param workbook
     */
    @Override
    protected void writeSheetContent(Workbook workbook) {
        standardSheetWriter.write(workbook);
    }
}
