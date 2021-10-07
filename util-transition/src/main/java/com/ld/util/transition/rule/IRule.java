package com.ld.util.transition.rule;

/**
 * 校验规则接口
 * 梁聃 2018/3/13 21:26
 */
public interface IRule {
    /**
     * 校验规则接口
     * @param source
     * @return
     */
    boolean match(Object source);

    /**
     * 校验后读取错误信息
     * @return
     */
    String errorInfo();
}
