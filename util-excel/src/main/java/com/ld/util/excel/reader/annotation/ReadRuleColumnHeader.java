package com.ld.util.excel.reader.annotation;

import java.lang.annotation.*;

/**
 * @ClassName ReadRuleColumnHeader
 * @Description 导入列头规则
 * @Author 梁聃
 * @Date 2021/10/15 10:02
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ReadRuleColumnHeaders.class)
public @interface ReadRuleColumnHeader {
    //列头名称属性名称
    String columnName();
    /**
     * 坐标,格式必须为“A1”“AA2”,对应于excel的单元格坐标
     */
    String coordinate();
    /**
     * 占列数
     */
    int coverageColumn() default 1;
    /**
     * 占行数
     */
    int coverageRow() default 1;
}
