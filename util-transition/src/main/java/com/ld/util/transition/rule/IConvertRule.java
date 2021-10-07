package com.ld.util.transition.rule;

/**
 * 转换规则接口
 * 梁聃 2019/1/5 23:27
 */
public interface IConvertRule {
    /**
     * 转换规则接口
     * @param source
     * @return
     */
    Object convert(Object source);
}
