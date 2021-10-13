package com.ld.util.excel.writer.output;

import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.message.ExcelMessageSource;
import com.ld.util.excel.writer.AbstractExcelWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName FileOutputStreamROP
 * @Description excel内容保存至本地文件，返回文件地址
 * @Author 梁聃
 * @Date 2021/9/25 9:49
 */
@Slf4j
public class FileOutputStreamROP implements IResultOutPut<String>  {
    //文件夹地址
    private String pathName;

    public FileOutputStreamROP(String pathName) {
        this.pathName = pathName;
    }

    /**
     * 输出导出结果
     *
     * @param fileNamePre
     * @param workbook
     * @return
     */
    @Override
    public String outPut(String fileNamePre, Workbook workbook) throws Exception {
        File file = new File(pathName + fileNamePre + "_" + System.currentTimeMillis() + AbstractExcelWriter.XLSX_EXT);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        workbook.write(fileOutputStream);
        return file.getAbsolutePath();
    }
}
