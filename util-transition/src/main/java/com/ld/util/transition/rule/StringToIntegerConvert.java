package com.ld.util.transition.rule;

import com.ld.util.transition.exception.ConvertException;

/**
 * 字符串转换为数字
 */
public class StringToIntegerConvert implements IConvertRule {
    public Object convert(Object source) {
        String s = source.toString();
        try {
            Integer i = Integer.valueOf(s);
            return i;
        } catch (NumberFormatException e) {
            throw new ConvertException("信息不能被转换,原因："+e.getMessage());
        }
    }
}
