package com.ld.util.excel.reader.xssf;

import com.ld.util.excel.reader.content.RowContentReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

/**
 * Sheet内容读取器
 * 适配器，实际调用RowContentReader<T>解析
 * 梁聃 2019/1/6 17:09
 */
public class SheetContentsXlsxReader implements XSSFSheetXMLHandler.SheetContentsHandler {
    private RowContentReader rowContentReader;

    public SheetContentsXlsxReader(RowContentReader rowContentReader) {
        this.rowContentReader = rowContentReader;
    }

    /**
     * 行解析开始时调用
     * @param index 当前索引号
     */
    @Override
    public void startRow(int index) {
        rowContentReader.startRow(index);
    }
    /**
     * 行解析结束时调用
     * @param index
     */
    @Override
    public void endRow(int index) {
        rowContentReader.endRow(index);
    }
    /**
     * 每个单元格内容解析时调用
     * @param s 单元格坐标
     * @param s1 单元格内容
     * @param xssfComment
     */
    @Override
    public void cell(String s, String s1, XSSFComment xssfComment) {
        rowContentReader.cell(s,s1,xssfComment);
    }

    @Override
    public void headerFooter(String s, boolean b, String s1) {
        rowContentReader.headerFooter(s,b,s1);
    }
}
