package com.ld.util.transition.rule;

import com.ld.util.transition.exception.ConvertException;

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
    Object convert(Object source) throws ConvertException;

    /**
     * 帮助解决创建转换对象需要入参的问题
     * @param initJson 创建转换规则需要的入参，入参需要遵守json的规范
     */
    default void init(String initJson){}
}
