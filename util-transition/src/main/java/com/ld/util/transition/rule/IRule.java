package com.ld.util.transition.rule;

import com.ld.util.transition.exception.RuleException;

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
    void match(Object source) throws RuleException;

    /**
     * 帮助解决创建规则对象需要入参的问题
     * @param initJson 创建规则需要的入参，入参需要遵守json的规范
     */
    default void init(String initJson){}
}
