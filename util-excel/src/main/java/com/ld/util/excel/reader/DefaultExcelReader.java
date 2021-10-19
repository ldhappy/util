package com.ld.util.excel.reader;

import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.message.ExcelMessageSource;
import com.ld.util.excel.reader.input.ISourceInput;
import com.ld.util.excel.reader.rule.ReadRule;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @ClassName ExcelReader
 * @Description 解析excel工具，仅支持首个sheet解析
 * @Author 梁聃
 * @Date 2021/10/9 10:16
 */
@Slf4j
public class DefaultExcelReader<T> extends AbstractExcelReader<T>{

    /**
     * 待解析资源文件输入处理接口
     */
    private ISourceInput iSourceInput;

    /**
     * excel的解析规则
     */
    private ReadRule<T> readRule;

    /**
     * 通过编码创建readRule信息
     * @param iSourceInput
     * @param readRule
     */
    public DefaultExcelReader(ISourceInput iSourceInput, ReadRule<T> readRule) {
        super(iSourceInput);
        if (Objects.isNull(readRule)) {
            throw ExcelException.messageException(ExcelMessageSource.READ_READ_RULE_EMPTY);
        }
        this.readRule = readRule;
    }

    public ReadRule<T> getReadRule() {
        return readRule;
    }

}
