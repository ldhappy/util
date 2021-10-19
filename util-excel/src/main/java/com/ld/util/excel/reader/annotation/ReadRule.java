package com.ld.util.excel.reader.annotation;

import com.ld.util.excel.reader.rule.CompleteMatchingRule;
import com.ld.util.excel.reader.rule.MatchingRule;

import java.lang.annotation.*;

/**
 * @ClassName ReadRule
 * @Description 读取规则
 * @Author 梁聃
 * @Date 2021/10/18 14:37
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadRule {
    //表头匹配规则
    Class<? extends MatchingRule> matchingRuleClass() default CompleteMatchingRule.class;
    /**
     * 列头开始行号,-1会根据规则列名出现的首行号为默认值
     */
    int columnHeaderBeginRowIndex() default -1;
    /**
     * 列头结束行号，-1会根据规则列名出现的（最大行号+占行数）为默认值
     */
    int columnContentBeginRowIndex() default -1;
    /**
     * 可解析的最大行数,-1为不限制
     */
    int maxRows() default -1;
}
