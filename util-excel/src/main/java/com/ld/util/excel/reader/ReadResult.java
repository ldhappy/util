package com.ld.util.excel.reader;

import com.ld.util.excel.core.ColumnHeader;
import com.ld.util.excel.reader.rule.ReadRule;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName ReadResult
 * @Description excel读取结果
 * @Author 梁聃
 * @Date 2021/10/8 15:45
 */
@ToString
public class ReadResult<T> {
    /**
     * 根据导入文件解析的列头
     */
    protected List<ColumnHeader> columnHeaderList;
    /**
     * excel解析后错误字段列名
     */
    private String errorInfoField = "ErrorInfo";
    /**
     * 成功解析行
     */
    private Map<T, Integer> successRows = new HashMap<>();
    /**
     * 成功解析行原始信息
     * TreeMap:key：输入文件行号，value：row的info
     * Map：key：excel的列号，如A,B,C，value：单元格的内容，列号互转可以使用ExcelUtil的转换工具
     */
    private TreeMap<Integer, Map<String, String>> successRowsOriginal = new TreeMap<Integer, Map<String, String>>();
    /**
     * 失败解析行
     * TreeMap:key：输入文件行号，value：row的info
     * Map：key：excel的列号，如A,B,C，value：单元格的内容，列号互转可以使用ExcelUtil的转换工具
     */
    private TreeMap<Integer, Map<String, String>> faultRows = new TreeMap<Integer, Map<String, String>>();

    private Integer totalRowsSize = 0;

    public String getErrorInfoField() {
        return errorInfoField;
    }

    public Map<T, Integer> getSuccessRows() {
        return successRows;
    }

    public TreeMap<Integer, Map<String, String>> getSuccessRowsOriginal() {
        return successRowsOriginal;
    }

    public TreeMap<Integer, Map<String, String>> getFaultRows() {
        return faultRows;
    }

    /**
     * 此处考虑分页处理时，有可能successRows已经处理完成移出successRows，所以采用总数-失败数获得成功数
     * @return
     */
    public int getSuccessRowsSize() {
        return totalRowsSize - faultRows.size();
    }

    public int getFaultRowsSize() {
        return faultRows.size();
    }

    public void setTotalRowsSize(Integer totalRowsSize) {
        this.totalRowsSize = totalRowsSize;
    }

    public int getTotalRowsSize() {
        return totalRowsSize;
    }

    public List<ColumnHeader> getColumnHeaderList() {
        return columnHeaderList;
    }

    public void setColumnHeaderList(List<ColumnHeader> columnHeaderList) {
        this.columnHeaderList = columnHeaderList;
    }

    /**
     * 设置错误信息
     *
     * @param index
     * @param errorMessage
     * @param currentRow
     */
    public void putFaultRow(int index, String errorMessage, Map<String, String> currentRow) {
        currentRow.put(errorInfoField, errorMessage);
        faultRows.put(index, currentRow);
    }

    /**
     * 将出错的成功解析行转入失败解析行，并记录错误信息
     *
     * @param row
     * @param errorMessage
     */
    public void removeSuccessRows(T row, String errorMessage) {
        Integer rowNo = successRows.get(row);
        successRows.remove(row);
        putFaultRow(rowNo, errorMessage, successRowsOriginal.get(rowNo));
        successRowsOriginal.remove(rowNo);
    }
}
