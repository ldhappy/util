package com.ld.util.transition.annotation;

import java.lang.annotation.*;

/**
 * 属性名称注解
 * 梁聃 2018/3/23 10:13
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {
    //入参属性名称
    String sourceName() default "";
    //错误提示属性名称
    String errorTipName() default "";
}
