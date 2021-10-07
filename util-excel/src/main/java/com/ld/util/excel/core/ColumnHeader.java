package com.ld.util.excel.core;

import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.util.ExcelUtil;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @ClassName ColumnHeader
 * @Description 列参数对象
 * @Author 梁聃
 * @Date 2021/9/22 10:16
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ColumnHeader {
    /**
     * 列名称
     */
    private String columnName;
    /**
     * 坐标,格式必须为“A1”“AA2”,对应于excel的单元格坐标
     */
    private String coordinate;
    /**
     * 占列数
     */
    private Integer coverageColumn;
    /**
     * 占行数
     */
    private Integer coverageRow;
    /**
     * 坐标列号，无需设置，getCoordinateColumn()方法中自动解析
     */
    private Integer coordinateColumn;
    /**
     * 坐标行号，无需设置，getCoordinateColumn()方法中自动解析
     */
    private Integer coordinateRow;

    @Builder(builderMethodName = "columnHeaderBuilder")
    public ColumnHeader(String columnName, String coordinate, Integer coverageColumn, Integer coverageRow) {
        this.columnName = columnName;
        this.coordinate = coordinate;
        this.coverageColumn = Objects.isNull(coverageColumn)?1:coverageColumn;
        this.coverageRow = Objects.isNull(coverageRow)?1:coverageRow;
    }

    public boolean equalsValue(ColumnHeader columnHeader) {
        if(columnHeader == null || columnHeader.getColumnName() == null || columnHeader.getCoordinate() == null){
            return false;
        }
        if(columnName == null || coordinate == null){
            return false;
        }
        if(columnName.trim().equals(columnHeader.getColumnName().trim()) && coordinate.equals(columnHeader.getCoordinate())){
            return true;
        } else {
            return false;
        }
    }

    /**
     * 允许修改坐标
     * @param coordinate
     */
    public ColumnHeader setCoordinate(String coordinate) {
        this.coordinate = coordinate;
        //修改坐标后，需要重新计算坐标列号，行号
        coordinateColumn = null;
        coordinateRow = null;
        return this;
    }

    public Integer getCoordinateColumn() {
        if(coordinateColumn == null){
            calculateCoordinate();
        }
        return coordinateColumn;
    }

    public Integer getCoordinateRow() {
        if(coordinateRow == null){
            calculateCoordinate();
        }
        return coordinateRow;
    }

    /**
     * 计算坐标
     */
    private void calculateCoordinate() {
        if(StringUtils.isNotBlank(coordinate) && coordinate.matches("^[A-Z]+[0-9]+$")){
            StringBuilder sb = new StringBuilder();
            for(int i = 0;i<coordinate.length();i++){
                char c = coordinate.charAt(i);
                if('A' <= c && c <= 'Z'){
                    sb.append(c);
                }else{
                    coordinateRow = Integer.valueOf(coordinate.substring(i,coordinate.length())) - 1;
                    break;
                }
            }
            coordinateColumn = ExcelUtil.excelColStrToNum(sb.toString());
        }else{
            throw new ExcelException("当前坐标异常，请检查设置。坐标需满足^[A-Z]+[0-9]+$");
        }
    }

    public static void main(String[] args) {
        ColumnHeader columnHeader = ColumnHeader.columnHeaderBuilder().columnName("test").coordinate("A1").build();
        System.out.println(columnHeader.toString());
        System.out.println(columnHeader.setCoordinate("B2").toString());
    }
}
