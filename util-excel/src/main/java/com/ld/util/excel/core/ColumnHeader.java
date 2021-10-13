package com.ld.util.excel.core;

import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.message.ExcelMessageSource;
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
public class ColumnHeader implements Comparable<ColumnHeader>{
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
            throw ExcelException.messageException(ExcelMessageSource.HEADER_COORDINATE_ERROR);
        }
    }

    public static void main(String[] args) {
        ColumnHeader columnHeader = ColumnHeader.columnHeaderBuilder().columnName("test").coordinate("A1").build();
        System.out.println(columnHeader.toString());
        System.out.println(columnHeader.setCoordinate("B2").toString());
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(ColumnHeader o) {
        int rowCompareResult = Integer.compare(getCoordinateRow(),o.getCoordinateRow());
        //行号相等时，对比列号，不等时直接返回行号对比结果
        return rowCompareResult == 0? Integer.compare(getCoordinateColumn(),o.getCoordinateColumn()):rowCompareResult;
    }
}
