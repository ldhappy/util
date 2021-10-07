package com.ld.util.transition.annotation;

import java.lang.annotation.*;

/**
 * 转换注解
 * 梁聃 2018/3/13 21:21
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Conversion  {
    //转换规则类
    Class convertRuleClass();
}
