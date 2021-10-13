package com.ld.util.excel.reader;

import com.ld.util.excel.reader.content.RowContentReader;
import com.ld.util.excel.reader.rule.ReadRule;

import java.io.InputStream;

/**
 * @ClassName IInputStreamReader
 * @Description 输入流抽象接口
 * @Author 梁聃
 * @Date 2021/10/12 16:41
 */
public interface IInputStreamReader {
    /**
     * 是否支持指定后缀文件
     * @param filePostfix 文件后缀
     * @return
     */
    boolean support(String filePostfix);

    /**
     * 读取内容
     * @param inputStream
     * @param rowContentReader
     * @param <T>
     */
    <T> void read(InputStream inputStream, RowContentReader<T> rowContentReader) throws Exception;
}
