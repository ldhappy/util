package com.ld.util.transition.example;

import com.ld.util.transition.exception.RuleException;
import com.ld.util.transition.rule.IRule;
import org.apache.commons.lang3.StringUtils;

public class NoRule implements IRule {
    public void match(Object source) {
        String s = source.toString();
        String No1="No.1";
        String No2="No.2";
        if(StringUtils.isNotBlank(s)){
            if (s.equals(No1) || s.equals(No2)){
                return ;
            }else{
                throw new RuleException("学号不符合要求");
            }
        }else {
            throw new RuleException("输入信息不能为空");
        }
    }
}
