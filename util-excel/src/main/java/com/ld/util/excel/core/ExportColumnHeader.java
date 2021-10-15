package com.ld.util.excel.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellUtil;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * @ClassName ExportColumnHeader
 * @Description 导出请求列参数对象
 * @Author 梁聃
 * @Date 2021/9/22 11:22
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ExportColumnHeader<T, R> extends ColumnHeader{
    /**
     * 列对应属性方法
     */
    private Function<T, R> columnFunction;

    /**
     * 列对应内容占格计算方法
     * 使用样例
     * transTime.setContentRowSizeFunction(unitTransDetailVo -> {
     *             if(CollectionUtils.isEmpty(unitTransDetailVo.getHistoryTrans())){
     *                 //没数据默认占1行
     *                 return 1;
     *             }else{
     *                 //有数据按数据占行统计
     *                 return unitTransDetailVo.getHistoryTrans().size();
     *             }
     *         });
     */
    private Function<T, Integer> contentRowSizeFunction;
    /**
     * 属性样式
     * 设置样例
     * (workbook,sheet) -> {
     *                 CellStyle cellStyle = workbook.createCellStyle();
     *                 //居中
     *                 cellStyle.setAlignment(HorizontalAlignment.CENTER);
     *                 //字体
     *                 Font font = workbook.createFont();
     *                 font.setColor(Font.COLOR_RED);
     *                 cellStyle.setFont(font);
     *                 //列宽 width：256*width+184，width与excel的列宽值大致相同
     *                 sheet.setColumnWidth(unitNameColumnHeader.getCoordinateColumn(),256*20+184);
     *                 //自动换行
     *                 cellStyle.setWrapText(true);
     *                 //内容格可选下拉列表
     *                 List<String> contentList = new ArrayList<>();
     *                 contentList.add("是");
     *                 contentList.add("否");
     *
     *                 unitNameColumnHeader.dropDownList(workbook,sheet,contentList,10000);
     *                 return cellStyle;
     *             }
     */
    /**
     * 列头对应属性样式设置方法
     */
    private BiFunction<Workbook,Sheet,CellStyle> headerStyleFunction;
    /**
     * 列头对应属性样式
     */
    private CellStyle headerStyle;

    /**
     * 列内容对应属性样式设置方法
     */
    private BiFunction<Workbook, Sheet,CellStyle> contentStyleFunction;
    /**
     * 列头对应属性样式
     */
    private CellStyle contentStyle;

    @Builder(builderMethodName = "exportColumnHeaderBuilder")
    public ExportColumnHeader(String columnName,
                              String coordinate,
                              Integer coverageColumn,
                              Integer coverageRow,
                              Function<T, R> columnFunction,
                              Function<T, Integer> contentRowSizeFunction,
                              BiFunction<Workbook,Sheet,CellStyle> headerStyleFunction,
                              BiFunction<Workbook, Sheet,CellStyle> contentStyleFunction) {
        super(columnName, coordinate, coverageColumn, coverageRow);
        this.columnFunction = columnFunction;
        this.contentRowSizeFunction = contentRowSizeFunction;
        setHeaderStyleFunction(headerStyleFunction);
        setContentStyleFunction(contentStyleFunction);
    }

    public void setHeaderStyleFunction(BiFunction<Workbook, Sheet, CellStyle> headerStyleFunction) {
        if(Objects.isNull(headerStyleFunction)){
            headerStyleFunction = this::headerDefaultStyle;
        }
        this.headerStyleFunction = headerStyleFunction.andThen(cellStyle -> headerStyle = cellStyle);
    }

    public void setContentStyleFunction(BiFunction<Workbook, Sheet, CellStyle> contentStyleFunction) {
        if(Objects.isNull(contentStyleFunction)){
            contentStyleFunction = this::contentDefaultStyle;
        }
        this.contentStyleFunction = contentStyleFunction.andThen(cellStyle -> contentStyle = cellStyle);
    }

    /**
     * 表头默认的样式
     * @param workbook
     * @param sheet
     * @return
     */
    public CellStyle headerDefaultStyle(Workbook workbook,Sheet sheet){
        CellStyle cellStyle = workbook.createCellStyle();
        //设置边框样式---细线
        borderThin(cellStyle);
//        //背景填充色
//        cellStyle.setFillForegroundColor((short) 67);
//        //cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //自适应列宽 width：256*width+184，width与excel的列宽值大致相同
        if(getCoverageColumn() == 1){
            //只占一列时，需要自适应列宽
            Integer width = 256*getColumnName().getBytes().length+184;
            if(sheet.getColumnWidth(getCoordinateColumn())<width){
                //找到最宽的列设置列宽
                sheet.setColumnWidth(getCoordinateColumn(),width);
            }
        }
        //字体
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 标题通用样式的样式
     * @param workbook
     * @param sheet
     * @return
     */
    public CellStyle titleStyle(Workbook workbook,Sheet sheet){
        CellStyle cellStyle = workbook.createCellStyle();
        //设置边框样式---细线
        borderThin(cellStyle);
//        //背景填充色
//        cellStyle.setFillForegroundColor((short) 67);
//        //cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //自适应列宽 width：256*width+184，width与excel的列宽值大致相同
        if(getCoverageColumn() == 1){
            //只占一列时，需要自适应列宽
            Integer width = 256*getColumnName().getBytes().length+184;
            if(sheet.getColumnWidth(getCoordinateColumn())<width){
                //找到最宽的列设置列宽
                sheet.setColumnWidth(getCoordinateColumn(),width);
            }
        }
        //字体
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 内容默认的样式
     * @param workbook
     * @param sheet
     * @return
     */
    public CellStyle contentDefaultStyle(Workbook workbook,Sheet sheet){
        CellStyle cellStyle = workbook.createCellStyle();
        //设置边框样式---细线
        borderThin(cellStyle);
        //自动换行
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    /**
     * 边框细线
     * @param cellStyle
     * @return
     */
    public CellStyle borderThin(CellStyle cellStyle){
        //设置边框样式---细线
        cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
        cellStyle.setBorderTop(BorderStyle.THIN);// 上边框
        cellStyle.setBorderRight(BorderStyle.THIN);// 右边框
        return cellStyle;
    }

    /**
     * 该列对应的内容单元格设置下拉列表
     * @param workbook
     * @param sheet
     * @param contentList 下拉列表内容
     * @param endRow 下拉列表结束行
     */
    public void dropDownList(Workbook workbook, Sheet sheet, List<String> contentList, int endRow) {
        //必须以字母开头，最长为31位
        String hiddenSheetName = "hiddenSheet_"+getCoordinate();
        //excel中的"名称"，用于标记隐藏sheet中的用作菜单下拉项的所有单元格
        String formulaId = "form_"+getCoordinate();
        Sheet hiddenSheet = workbook.createSheet(hiddenSheetName);//用于存储 下拉菜单数据
        //存储下拉菜单项的sheet页不显示
        workbook.setSheetHidden(workbook.getSheetIndex(hiddenSheet), true);

        IntStream.range(0,contentList.size()).forEach(index ->{
            Row contentRow=hiddenSheet.createRow(index);
            CellUtil.createCell(contentRow, getCoordinateColumn(),contentList.get(index));
        });

        Name namedCell = workbook.createName();//创建"名称"标签，用于链接
        namedCell.setNameName(formulaId);
        namedCell.setRefersToFormula(hiddenSheetName + "!A$1:A$" + contentList.size());
        DataValidationHelper dvHelper = sheet.getDataValidationHelper();
        DataValidationConstraint dvConstraint = dvHelper.createFormulaListConstraint(formulaId);

        CellRangeAddressList addressList = new CellRangeAddressList(getCoordinateRow()+1, endRow, getCoordinateColumn(), getCoordinateColumn());
        DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);//添加菜单(将单元格与"名称"建立关联)
        sheet.addValidationData(validation);
    }



    public static void main(String[] args) {
        ExportColumnHeader columnHeader = ExportColumnHeader.exportColumnHeaderBuilder().columnName("test").coordinate("A1").build();
        System.out.println(columnHeader.toString());
        System.out.println(columnHeader.setCoordinate("B2").toString());
    }
}
