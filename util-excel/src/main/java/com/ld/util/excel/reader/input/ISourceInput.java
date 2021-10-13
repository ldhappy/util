package com.ld.util.excel.reader.input;

import java.io.InputStream;

/**
 * @ClassName ISourceInput
 * @Description 待解析资源文件输入处理接口
 * @Author 梁聃
 * @Date 2021/10/9 9:57
 */
public interface ISourceInput {
    /**
     * 将资源文件名转换为输入流
     * @param sourceKey
     * @return
     */
    InputStream input(String sourceKey) throws Exception;
}
