package com.ld.util.excel.reader.input;

import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.message.ExcelMessageSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @ClassName IFileSourceInput
 * @Description 本地文件输入源
 * @Author 梁聃
 * @Date 2021/10/13 9:42
 */
public class FileSourceInput implements ISourceInput{

    /**
     * 将资源文件名转换为输入流
     *
     * @param sourceKey
     * @return
     */
    @Override
    public InputStream input(String sourceKey)  throws Exception{
        //读取到当前类路径下的样例文件
        File file = new File(sourceKey);
        if (!file.exists()) {
            throw ExcelException.messageException(ExcelMessageSource.READ_SOURCE_INPUT_FILE_NOT_EXIST,sourceKey);
        }
        return new FileInputStream(file);
    }
}
