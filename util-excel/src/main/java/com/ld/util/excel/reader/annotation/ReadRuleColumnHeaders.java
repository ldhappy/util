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
public @interface ReadRuleColumnHeaders {
    ReadRuleColumnHeader[] value();
}
