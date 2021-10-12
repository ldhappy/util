package com.ld.util.excel.writer.output;

import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.message.ExcelMessageSource;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @ClassName ByteArrayOutputStreamROP
 * @Description 返回结果为字节输出流
 * @Author 梁聃
 * @Date 2021/9/22 16:54
 */
public class ByteArrayOutputStreamROP implements IResultOutPut<ByteArrayOutputStream>  {
    /**
     * 输出导出结果
     *
     * @param fileNamePre
     * @param workbook
     * @return
     */
    @Override
    public ByteArrayOutputStream outPut(String fileNamePre, Workbook workbook) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os);
        } catch (IOException e) {
            ExcelException.messageException(ExcelMessageSource.WRITE_ERROR,e.getMessage());
        }
        return os;
    }
}
