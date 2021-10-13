package com.ld.util.excel.reader.content;

import com.ld.util.excel.reader.ReadResult;
import com.ld.util.excel.reader.rule.ReadRule;
import org.apache.poi.xssf.usermodel.XSSFComment;

/**
 * 统一xls，xlsx文件内容读取的接口
 * 梁聃 2019/1/7 17:18
 */
public interface RowContentReader<T> {
    /**
     * 行解析开始时调用
     * @param index 当前索引号
     */
    void startRow(int index);
    /**
     * 行解析结束时调用
     * @param index
     */
    void endRow(int index);

    /**
     * 每个单元格内容解析时调用
     * @param s 单元格坐标
     * @param s1 单元格内容
     * @param xssfComment
     */
    void cell(String s, String s1, XSSFComment xssfComment);

    void headerFooter(String s, boolean b, String s1);

    void setReadResult(ReadResult<T> readResult);

    ReadResult<T> getReadResult();

    void setReadRule(ReadRule<T> readRule);
}
