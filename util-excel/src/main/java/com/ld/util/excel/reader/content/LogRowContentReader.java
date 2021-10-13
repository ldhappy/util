package com.ld.util.excel.reader.content;

import com.ld.util.excel.reader.ReadResult;
import com.ld.util.excel.reader.rule.ReadRule;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFComment;

/**
 * 简单打印日志，用于查看SheetContentsXlsReader内容解析是否正确
 * 梁聃 2019/1/9 10:52
 * @param <T>
 */
@Slf4j
public class LogRowContentReader<T> implements RowContentReader<T> {
    /**
     * 行解析开始时调用
     *
     * @param index 当前索引号
     */
    @Override
    public void startRow(int index) {
        log.info("startRow-begin>>>param:"+"index = [" + index + "]");
        log.info("startRow-end<<<return:");
    }

    /**
     * 行解析结束时调用
     *
     * @param index
     */
    @Override
    public void endRow(int index) {
        log.info("endRow-begin>>>param:"+"index = [" + index + "]");
        log.info("endRow-end<<<return:");
    }

    /**
     * 每个单元格内容解析时调用
     *
     * @param s           单元格坐标
     * @param s1          单元格内容
     * @param xssfComment
     */
    @Override
    public void cell(String s, String s1, XSSFComment xssfComment) {
        log.info("cell-begin>>>param:"+"s = [" + s + "], s1 = [" + s1 + "], xssfComment = [" + xssfComment + "]");
        log.info("cell-end<<<return:");
    }

    @Override
    public void headerFooter(String s, boolean b, String s1) {
        log.info("headerFooter-begin>>>param:"+"s = [" + s + "], b = [" + b + "], s1 = [" + s1 + "]");
        log.info("headerFooter-end<<<return:");
    }

    @Override
    public void setReadResult(ReadResult<T> readResult) {
        
    }

    @Override
    public ReadResult<T> getReadResult() {
        return null;
    }

    @Override
    public void setReadRule(ReadRule<T> readRule) {

    }
}
