package com.ld.util.excel.example.read.builder;

import com.ld.util.transition.exception.ConvertException;
import com.ld.util.transition.rule.IConvertRule;
import org.apache.commons.lang3.StringUtils;

public class SexConvert implements IConvertRule {
    public Object convert(Object source) {
        String s = source.toString();
        if(StringUtils.isNotBlank(s)){
            if (s.equals("男")){
                return 1;
            }else if(s.equals("女")){
                return 2;
            }
        }
        throw new ConvertException("信息不能被转换");
    }
}
