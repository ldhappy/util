package com.ld.util.excel.reader;

import com.google.common.collect.Lists;
import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.message.ExcelMessageSource;
import com.ld.util.excel.reader.annotation.AnnotationReadRuleClassReflect;
import com.ld.util.excel.reader.content.DefaultRowContentReader;
import com.ld.util.excel.reader.content.RowContentReader;
import com.ld.util.excel.reader.hssf.XlsReader;
import com.ld.util.excel.reader.input.ISourceInput;
import com.ld.util.excel.reader.rule.ReadRule;
import com.ld.util.excel.reader.xssf.XlsxReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName AbstractExcelReader
 * @Description 解析excel工具，抽象类
 * @Author 梁聃
 * @Date 2021/10/9 10:16
 */
@Slf4j
public abstract class AbstractExcelReader<T> {

    //处理的文件后缀名合集
    private static final List<IInputStreamReader> readerList = Lists.newArrayList(
            new XlsxReader(),
            new XlsReader()
    );

    /**
     * 待解析资源文件输入处理接口
     */
    protected ISourceInput iSourceInput;

    public AbstractExcelReader(ISourceInput iSourceInput) {
        if (Objects.isNull(iSourceInput)) {
            throw ExcelException.messageException(ExcelMessageSource.READ_SOURCE_INPUT_EMPTY);
        }
        this.iSourceInput = iSourceInput;
    }

    public abstract ReadRule<T> getReadRule();

    /**
     * 使用默认的行信息读取器读取待解析资源文件
     *
     * @param sourceKey 待解析资源文件名
     * @return
     */
    public ReadResult<T> read(String sourceKey) throws ExcelException {
        return read(sourceKey, new DefaultRowContentReader<>());
    }

    /**
     * 使用指定的行信息读取器读取待解析资源文件
     *
     * @param sourceKey        待解析资源文件名
     * @param rowContentReader 行内容读取器（如有特殊需求可以继承扩展，如解析时同步处理）
     * @return
     */
    public ReadResult<T> read(String sourceKey, RowContentReader<T> rowContentReader) throws ExcelException {
        //检查入参，并获取对应的流读取器
        IInputStreamReader reader = checkReadParam(sourceKey,rowContentReader);
        try {
            //获取文件流
            InputStream inputStream = iSourceInput.input(sourceKey);
            reader.read(inputStream,rowContentReader);
            return rowContentReader.getReadResult();
        } catch (Exception e) {
            log.error("导入文件异常，原因：",e);
            throw ExcelException.messageException(ExcelMessageSource.READ_ERROR,e.getMessage());
        }
    }

    /**
     * 检查入参，并获取对应的流读取器
     * @param sourceKey
     * @param rowContentReader
     * @return
     */
    private IInputStreamReader checkReadParam(String sourceKey, RowContentReader<T> rowContentReader) {
        //入参检查
        if (StringUtils.isBlank(sourceKey)) {
            throw ExcelException.messageException(ExcelMessageSource.READ_SOURCE_KEY_EMPTY);
        }
        if (Objects.isNull(rowContentReader)) {
            throw ExcelException.messageException(ExcelMessageSource.READ_ROW_CONTENT_READER_EMPTY);
        }
        if (Objects.isNull(rowContentReader.getReadResult())) {
            //分页读取时可能已经存在读取结果了，所以为空时才创建读取结果
            rowContentReader.setReadResult(new ReadResult<>());
        }
        rowContentReader.setReadRule(getReadRule());
        //检查文件后缀并获取指定的读取器
        //扩展名
        String extString = sourceKey.substring(sourceKey.lastIndexOf(".")).toLowerCase();
        for(IInputStreamReader reader:readerList){
            if(reader.support(extString)){
                return reader;
            }
        }
        throw ExcelException.messageException(ExcelMessageSource.READ_FILE_POSTFIX_NONSUPPORT,extString);
    }
}
