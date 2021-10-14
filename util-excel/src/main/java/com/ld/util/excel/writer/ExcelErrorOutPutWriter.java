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
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;
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
        TreeMap<Integer, Map<String, String>> faultRows = readResult.getFaultRows();
        if(MapUtils.isEmpty(faultRows)){
            throw ExcelException.messageException(ExcelMessageSource.READ_RESULT_FAULT_ROW_EMPTY);
        }
        List<ExportColumnHeader<Map, ?>> exportColumnHeaderList = Lists.newArrayList();
        if(CollectionUtils.isEmpty(columnHeaderList)){
            columnHeaderList = readResult.getColumnHeaderList();
            columnHeaderList.forEach(columnHeader ->
                            exportColumnHeaderList.add(ExportColumnHeader.<Map,String>exportColumnHeaderBuilder()
                                    .columnName(columnHeader.getColumnName())
                                    .coordinate(columnHeader.getCoordinate())
                                    .coverageRow(columnHeader.getCoverageRow())
                                    .coverageColumn(columnHeader.getCoverageColumn())
                                    .columnFunction(map -> map.getOrDefault(ExcelUtil.excelColIndexToStr(columnHeader.getCoordinateColumn()),"").toString())
                                    .build()));
        }
        standardSheetWriter = StandardSheetWriter.<Map>builder()
                .sheetName("未成功导入数据")
                .columnHeaderList(exportColumnHeaderList)
                .contentList(faultRows.entrySet().stream().map(entry->entry.getValue()).collect(Collectors.toList()))
                .build();
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
