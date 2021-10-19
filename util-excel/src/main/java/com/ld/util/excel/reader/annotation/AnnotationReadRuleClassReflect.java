package com.ld.util.excel.reader.annotation;

import com.google.common.collect.Lists;
import com.ld.util.excel.core.ColumnHeader;
import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.message.ExcelMessageSource;
import com.ld.util.excel.reader.rule.MatchingRule;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName AnnotationReadRuleClassReflect
 * @Description 解析ReadRule的注解
 * @Author 梁聃
 * @Date 2021/10/18 16:10
 */
@Getter
@Slf4j
public class AnnotationReadRuleClassReflect<T> {
    com.ld.util.excel.reader.rule.ReadRule<T> readRule;
    /**
     *
     * @param clazz
     */
    public AnnotationReadRuleClassReflect(Class<T> clazz) {
        //表头匹配规则
        Class<? extends MatchingRule> matchingRuleClass = null;
        //列头开始行号,为空会根据规则列名出现的首行号为默认值
        Integer columnHeaderBeginRowIndex = null;
        //列头结束行号，为空会根据规则列名出现的（最大行号+占行数）为默认值
        Integer columnContentBeginRowIndex = null;
        //可解析的最大行数,为空为不限制
        Integer maxRows = null;
        //规则列名List
        List<ColumnHeader> ruleColumnHeaderList = Lists.newArrayList();
        ReadRule readRule = clazz.getAnnotation(ReadRule.class);
        if(Objects.nonNull(readRule)){
            matchingRuleClass = readRule.matchingRuleClass();
            if(readRule.columnHeaderBeginRowIndex() != -1){
                columnHeaderBeginRowIndex = readRule.columnHeaderBeginRowIndex();
            }
            if(readRule.columnContentBeginRowIndex() != -1){
                columnHeaderBeginRowIndex = readRule.columnContentBeginRowIndex();
            }
            if(readRule.maxRows() != -1){
                columnHeaderBeginRowIndex = readRule.maxRows();
            }
        }

        ReadRuleColumnHeaders readRuleColumnHeaders = clazz.getAnnotation(ReadRuleColumnHeaders.class);
        if(Objects.nonNull(readRuleColumnHeaders)){
            Arrays.stream(readRuleColumnHeaders.value()).forEach(
                    readRuleColumnHeader -> ruleColumnHeaderList.add(
                            ColumnHeader.columnHeaderBuilder()
                                    .columnName(readRuleColumnHeader.columnName())
                                    .coordinate(readRuleColumnHeader.coordinate())
                                    .coverageRow(readRuleColumnHeader.coverageRow())
                                    .coverageColumn(readRuleColumnHeader.coverageColumn())
                                    .build())
                    );
        }
        try {
            com.ld.util.excel.reader.rule.ReadRule.ReadRuleBuilder<T> builder = com.ld.util.excel.reader.rule.ReadRule.<T>builder()
                    .ruleColumnHeaderList(ruleColumnHeaderList)
                    .targetClass(clazz)
                    .columnHeaderBeginRowIndex(columnHeaderBeginRowIndex)
                    .columnContentBeginRowIndex(columnContentBeginRowIndex)
                    .maxRows(maxRows);
            if(Objects.nonNull(matchingRuleClass)){
                builder.matchingRule(matchingRuleClass.newInstance());
            }
            this.readRule = builder.build();
        } catch (Exception e) {
            log.error("创建excel读取规则异常，原因：",e);
            throw ExcelException.messageException(ExcelMessageSource.READ_ERROR,e.getMessage());
        }
    }
}
