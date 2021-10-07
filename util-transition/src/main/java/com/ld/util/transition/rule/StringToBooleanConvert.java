package com.ld.util.transition.rule;


import com.ld.util.transition.exception.ConvertException;

/**
 * 字符串转换为布尔
 */
public class StringToBooleanConvert implements IConvertRule {
    @Override
    public Object convert(Object source) {
        String s = source.toString().trim();
        if("是".equals(s) || "true".equals(s)){
            return true;
        }else if("否".equals(s) || "false".equals(s)){
            return false;
        }else {
            throw new ConvertException("请填写内容：“是”或“否”");
        }
    }

}
