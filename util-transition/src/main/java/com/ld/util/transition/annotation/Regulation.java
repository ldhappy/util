package com.ld.util.transition.annotation;

import java.lang.annotation.*;

/**
 * 校验规则注解
 * 梁聃 2018/3/13 21:20
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Regulation {
    //校验正则
    String rule() default "";
    //校验类，该属性有值时正则不生效
    Class ruleClass() default Object.class;
    //错误信息
    String errorInfo() default "不满足规则表达式";
    //必填字段
    boolean required() default false;
}
