package com.ld.util.excel.writer.sheet.annotation;

import java.lang.annotation.*;
import java.util.function.Function;

/**
 * @ClassName ColumnHeaderRule
 * @Description 打印列头规则
 * @Author 梁聃
 * @Date 2021/9/26 9:25
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnHeaderRule {
    //列头名称属性名称
    String name() default "";
    //列头排序，建议同一个对象中不要出现相同排序字段
    int order() default 0;
}
