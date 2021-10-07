package com.ld.util.excel.writer.sheet.annotation;

import java.lang.annotation.*;

/**
 * @ClassName SheetRule
 * @Description 打印工作表的规则
 * @Author 梁聃
 * @Date 2021/9/26 9:25
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SheetRule {
    //工作表名称
    String name() default "";
}
