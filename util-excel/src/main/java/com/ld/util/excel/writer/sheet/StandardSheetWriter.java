package com.ld.util.excel.writer.sheet;

import com.ld.util.excel.core.ExportColumnHeader;
import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.message.ExcelMessageSource;
import com.ld.util.excel.util.ExcelUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * @ClassName StandardSheetWriter
 * @Description 标准的sheet输出
 * @Author 梁聃
 * @Date 2021/9/22 15:09
 */
@Slf4j
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StandardSheetWriter<T> extends AbstactSheetWriter implements ISheetWriter {
    /**
     * 导出列头
     */
    private List<ExportColumnHeader<T, ?>> columnHeaderList;

    /**
     * 导出内容
     */
    private List<T> contentList;

    /**
     * 导出内容起始行
     */
    int columnContentBeginRowIndex;

    /**
     *
     * @param sheetName 工作表名称
     * @param columnHeaderList 列头
     * @param contentList 导出内容
     * @param needDefaultCoordinate 是否需要设置默认的列序号，默认不需要
     */
    @Builder
    public StandardSheetWriter(String sheetName,@Singular("columnHeader") List<ExportColumnHeader<T, ?>> columnHeaderList,@Singular("content") List<T> contentList,Boolean needDefaultCoordinate) {
        super(sheetName);
        if(CollectionUtils.isEmpty(columnHeaderList)){
            throw ExcelException.messageException(ExcelMessageSource.WRITE_SHEET_HEADER_EMPTY);
        }
        if(Objects.nonNull(needDefaultCoordinate) && needDefaultCoordinate){
            //设置默认的列序号
            IntStream.range(0, columnHeaderList.size()).forEach(i -> {
                ExportColumnHeader<T,?> columnHeader = columnHeaderList.get(i);
                if(columnHeader.getCoordinate() == null){
                    columnHeader.setCoordinate(ExcelUtil.excelColIndexToStr(i)+"1");
                }
            });
        }
        this.columnHeaderList = columnHeaderList;
        this.contentList = Objects.isNull(contentList)? Lists.newArrayList():contentList;
        //计算导出内容起始行
        columnContentBeginRowIndex = columnHeaderList.stream().mapToInt(columnHeader -> columnHeader.getCoordinateRow()+columnHeader.getCoverageRow()).max().getAsInt();
    }



    /**
     * 向workbook对象中写入导出内容
     *
     * @param workbook
     */
    @Override
    public void write(Workbook workbook) {
        //建立新的sheet对象（excel的表单）
        Sheet sheet = workbook.createSheet(sheetName);
        log.trace("开始导出工作表：[{}]",sheetName);
        writeHeader(workbook,sheet);
        writeContent(workbook,sheet);
    }

    /**
     * 写入导出列头
     * @param workbook
     * @param sheet
     */
    private void writeHeader(Workbook workbook, Sheet sheet) {
        if(columnContentBeginRowIndex == 0){
            //=0说明没有列头需要打印
            //设置excel样式即可
            columnHeaderList.stream().forEach(columnHeader -> {
                columnHeader.getContentStyleFunction().apply(workbook,sheet);
            });
            return;
        }
        int rowIndex = 0;
        //设置表头
        Map<Integer, Row> columnHeaderRowMap = new HashMap<>(columnContentBeginRowIndex);
        for(;rowIndex<columnContentBeginRowIndex;rowIndex++){
            Row row = sheet.createRow(rowIndex);
            columnHeaderRowMap.put(rowIndex,row);
        }
        log.trace("工作表列头内容：");
        columnHeaderList.stream().forEach(columnHeader -> {
            //excel列头的样式
            CellStyle cellStyle = columnHeader.getHeaderStyleFunction().apply(workbook,sheet);
            //excel内容的样式
            columnHeader.getContentStyleFunction().apply(workbook,sheet);
            if(columnHeader.getCoverageColumn() > 1 || columnHeader.getCoverageRow() > 1){
                createMergedRegion(sheet,columnHeaderRowMap,cellStyle,columnHeader.getCoordinateRow(),columnHeader.getCoverageRow(),columnHeader.getCoordinateColumn(),columnHeader.getCoverageColumn());
            }
            CellUtil.createCell(columnHeaderRowMap.get(columnHeader.getCoordinateRow()),columnHeader.getCoordinateColumn(),columnHeader.getColumnName(),cellStyle);
            log.trace("列头名称：[{}],起始坐标[{}],占{}行{}列",columnHeader.getColumnName(),columnHeader.getCoordinate(),columnHeader.getCoverageRow(),columnHeader.getCoordinateColumn());
        });
    }

    /**
     * 写入导出内容
     * @param workbook
     * @param sheet
     */
    private void writeContent(Workbook workbook, Sheet sheet){
        int rowIndex = columnContentBeginRowIndex;
        //设置表格内容
        if(CollectionUtils.isNotEmpty(contentList)){
            for(T content:contentList){
                //计算当前内容所占行数
                Integer rowSize = columnHeaderList.stream().mapToInt(columnHeader ->{
                    if (Objects.isNull(columnHeader.getContentRowSizeFunction())) {
                        return 1;
                    }else {
                        return columnHeader.getContentRowSizeFunction().apply(content);
                    }
                }).max().getAsInt();

                //创建行信息
                Map<Integer,Row> columnRowMap = new HashMap<>(rowSize);
                Integer columnBeginRowIndex = rowIndex;
                for(; rowIndex < columnBeginRowIndex+rowSize;rowIndex++){
                    Row row = sheet.createRow(rowIndex);
                    columnRowMap.put(rowIndex,row);
                }
                //首行
                Row contentRow=columnRowMap.get(columnBeginRowIndex);

                columnHeaderList.stream().forEach(columnHeader ->{
                    if(columnHeader.getColumnFunction() != null){
                        Object columnValue = columnHeader.getColumnFunction().apply(content);
                        if(Objects.nonNull(columnValue) && columnValue instanceof Content){
                            Content c = (Content)columnValue;
                            if(CollectionUtils.isNotEmpty(c.getColumnValues())){
                                //转换开始行
                                Integer beginRowIndex = columnBeginRowIndex;
                                for(int i = 0;i<c. getColumnValues().size();i++){
                                    String value = c. getColumnValues().get(i);
                                    Integer coverageRow = c.getCoverageRows().get(i);
                                    if(coverageRow != 1){
                                        createMergedRegion(sheet,columnRowMap,columnHeader.getContentStyle(),beginRowIndex,coverageRow,columnHeader.getCoordinateColumn(),1);
                                    }
                                    CellUtil.createCell(columnRowMap.get(beginRowIndex), columnHeader.getCoordinateColumn(),value == null?"":value,columnHeader.getContentStyle());
                                    beginRowIndex+=coverageRow;
                                }
                                return;
                            }else{
                                columnValue = null;
                            }
                        }
                        if(rowSize != 1){
                            createMergedRegion(sheet,columnRowMap,columnHeader.getContentStyle(),columnBeginRowIndex,rowSize,columnHeader.getCoordinateColumn(),1);
                        }
                        CellUtil.createCell(contentRow, columnHeader.getCoordinateColumn(),columnValue == null?"":columnValue.toString(),columnHeader.getContentStyle());

                    }
                });
            }
        }
    }

    /**
     * 创建合并区域
     * @param sheet
     * @param columnRowMap excel行对象map
     * @param cellStyle 单元格样式
     * @param coordinateRow 坐标行
     * @param coverageRow 所占行数
     * @param coordinateColumn 坐标列
     * @param coverageColumn 所占列数
     */
    private static void createMergedRegion(Sheet sheet, Map<Integer, Row> columnRowMap, CellStyle cellStyle, Integer coordinateRow, Integer coverageRow, Integer coordinateColumn, Integer coverageColumn) {
        //当所占（行/列）数大于1时，需要合并单元格
        CellRangeAddress cellRangeAddress = new CellRangeAddress(coordinateRow,coordinateRow+coverageRow-1,coordinateColumn,coordinateColumn+coverageColumn-1);
        sheet.addMergedRegion(cellRangeAddress);
        //保证所有单元格边框划线
        IntStream.range(cellRangeAddress.getFirstRow(),cellRangeAddress.getLastRow()+1).forEach(ri ->{
            IntStream.range(cellRangeAddress.getFirstColumn(),cellRangeAddress.getLastColumn()+1).forEach(ci ->{
                CellUtil.createCell(columnRowMap.get(ri),ci,"",cellStyle);
            });
        });
    }
}
