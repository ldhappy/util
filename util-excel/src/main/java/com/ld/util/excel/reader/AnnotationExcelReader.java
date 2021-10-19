package com.ld.util.excel.reader;

import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.message.ExcelMessageSource;
import com.ld.util.excel.reader.annotation.AnnotationReadRuleClassReflect;
import com.ld.util.excel.reader.content.DefaultRowContentReader;
import com.ld.util.excel.reader.input.ISourceInput;
import com.ld.util.excel.reader.rule.ReadRule;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName ExcelReader
 * @Description 解析excel工具，仅支持首个sheet解析
 * @Author 梁聃
 * @Date 2021/10/9 10:16
 */
@Slf4j
public class AnnotationExcelReader<T> extends AbstractExcelReader<T>{

    private static ConcurrentMap<String, AnnotationReadRuleClassReflect<?>> classReflectMap = new ConcurrentHashMap<>();

    /**
     * excel的解析规则
     */
    private ReadRule<T> readRule;

    /**
     * 目标类实现了标准注解（@ReadRuleColumnHeader，@ReadRule），可以解析出readRule信息
     * @param iSourceInput
     * @param targetClass
     */
    public AnnotationExcelReader(ISourceInput iSourceInput,Class<T> targetClass) {
        super(iSourceInput);
        if(Objects.isNull(targetClass)){
            throw ExcelException.messageException(ExcelMessageSource.READ_READ_RULE_TARGET_CLASS_EMPTY);
        }
        this.readRule = getClassReflect(targetClass).getReadRule();
    }

    public ReadRule<T> getReadRule() {
        return readRule;
    }

    private AnnotationReadRuleClassReflect getClassReflect(Class<T> targetClass) {
        AnnotationReadRuleClassReflect classReflect = classReflectMap.get(targetClass.getName());
        if(classReflect == null) {
            classReflectMap.putIfAbsent(targetClass.getName(),new AnnotationReadRuleClassReflect(targetClass));
            classReflect=classReflectMap.get(targetClass.getName());
        }
        return classReflect;
    }

    /**
     * 使用默认的行信息读取器读取待解析资源文件
     *
     * @param sourceKey 待解析资源文件名
     * @return
     */
    public ReadResult<T> read(String sourceKey) throws ExcelException {
        return read(sourceKey, new DefaultRowContentReader<>());
    }

}
