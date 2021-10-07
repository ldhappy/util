package com.ld.util.excel.writer.sheet;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * @ClassName ISheetWriter
 * @Description sheet输出接口
 * @Author 梁聃
 * @Date 2021/9/22 15:07
 */
public interface ISheetWriter {
    /**
     * 向workbook对象中写入导出内容
     * @param workbook
     */
    void write(Workbook workbook);
}
