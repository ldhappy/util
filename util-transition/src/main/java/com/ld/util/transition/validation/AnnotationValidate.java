package com.ld.util.transition.validation;

import com.ld.util.transition.annotation.Regulation;
import com.ld.util.transition.exception.ParseException;
import com.ld.util.transition.exception.RuleException;
import com.ld.util.transition.message.TransitionMessageSource;
import com.ld.util.transition.parse.IParseDataGet;
import com.ld.util.transition.rule.IRule;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 根据注解校验转换数据
 * @param <S>
 * @param <R>
 */
public class AnnotationValidate<S,R>  implements IValidate{
    private IParseDataGet<S,R> parseData;
    private static ConcurrentMap<String,AnnotationValidateClassReflect> classReflectMap = new ConcurrentHashMap<String,AnnotationValidateClassReflect>();

    public AnnotationValidate(IParseDataGet<S, R> parseData) {
        this.parseData = parseData;
    }

    public void validate(){
        S source = parseData.getSource();
        R result = parseData.getResult();
        AnnotationValidateClassReflect classReflect = getClassReflect(result);
        for (Map.Entry<String, Regulation> entry:classReflect.getRegulationMap().entrySet()){
            String name = entry.getKey();
            String errorTipName;
            if(classReflect.getErrorTipNameMap().containsKey(name)){
                errorTipName = classReflect.getErrorTipNameMap().get(name);
            }else{
                errorTipName = name;
            }
            //规则注解
            Regulation regulation = entry.getValue();
            if (regulation != null) {
                Object value = parseData.getSourceField(name);
                if (value != null) {
                    if (!regulation.ruleClass().equals(Object.class)) {
                        Object object = null;
                        try {
                            object = regulation.ruleClass().newInstance();
                        } catch (IllegalAccessException e) {
                            ParseException.messageException(TransitionMessageSource.VALIDATE_EXCEPTION_CREATE_RULE_CLASS, errorTipName, e.getMessage());
                        } catch (InstantiationException e) {
                            ParseException.messageException(TransitionMessageSource.VALIDATE_EXCEPTION_CREATE_RULE_CLASS, errorTipName, e.getMessage());
                        }
                        if (object instanceof IRule) {
                            IRule rule = (IRule) object;
                            try {
                                rule.init(regulation.ruleClassInitJson());
                                rule.match(value);
                            } catch (Exception e) {
                                ParseException.messageException(TransitionMessageSource.VALIDATE_EXCEPTION_UNMATCHED,errorTipName,e.getMessage());
                            }
                        } else {
                            ParseException.messageException(TransitionMessageSource.VALIDATE_EXCEPTION_RULE_CLASS_IMPL,errorTipName,regulation.ruleClass().getCanonicalName(),IRule.class.getCanonicalName());
                        }
                    } else if (StringUtils.isNotBlank(regulation.rule())) {
                        if (value.toString().matches(regulation.rule())) {
                            continue;
                        } else {
                            ParseException.messageException(TransitionMessageSource.VALIDATE_EXCEPTION_UNMATCHED,errorTipName,regulation.errorInfo());
                        }
                    }
                } else if (regulation.required()) {
                    ParseException.messageException(TransitionMessageSource.VALIDATE_EXCEPTION_NULL,errorTipName);
                }
            }
        }
    }

    private AnnotationValidateClassReflect getClassReflect(R result) {
        AnnotationValidateClassReflect classReflect = classReflectMap.get(result.getClass().getName());
        if(classReflect == null) {
            classReflectMap.putIfAbsent(result.getClass().getName(),new AnnotationValidateClassReflect(result.getClass()));
            classReflect=classReflectMap.get(result.getClass().getName());
        }
        return classReflect;
    }



}
