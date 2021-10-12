package com.ld.util.transition.convert;

import com.ld.util.transition.annotation.Conversion;
import com.ld.util.transition.exception.ConvertException;
import com.ld.util.transition.exception.ParseException;
import com.ld.util.transition.message.TransitionMessageSource;
import com.ld.util.transition.parse.IParseData;
import com.ld.util.transition.rule.IConvertRule;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AnnotationConvert<S,R>  implements IConvert{
    private IParseData<S,R> parseData;
    private static ConcurrentMap<String,AnnotationConvertClassReflect> classReflectMap = new ConcurrentHashMap<String,AnnotationConvertClassReflect>();
    public AnnotationConvert(IParseData<S, R> parseData) {
        this.parseData = parseData;
    }

    public void convert(){
        S source = parseData.getSource();
        R result = parseData.getResult();

        AnnotationConvertClassReflect classReflect = getClassReflect(result);
        for(FieldConvert fieldConvert:classReflect.getFieldConvertList()){
            String name = fieldConvert.getSourceName();
            String errorTipName = fieldConvert.getErrorTipName();
            Conversion conversion = fieldConvert.getConversion();
            Field field = fieldConvert.getField();
            Object value = parseData.getSourceField(name);
            if (value != null){
                if (conversion != null){
                    Object object = null;
                    try {
                        object = conversion.convertRuleClass().newInstance();
                    } catch (IllegalAccessException e) {
                        throw ParseException.messageException(TransitionMessageSource.CONVERT_EXCEPTION_CREATE_RULE_CLASS, errorTipName, e.getMessage());
                    } catch (InstantiationException e) {
                        throw ParseException.messageException(TransitionMessageSource.CONVERT_EXCEPTION_CREATE_RULE_CLASS, errorTipName, e.getMessage());
                    }
                    if (object instanceof IConvertRule){
                        IConvertRule rule = (IConvertRule) object;
                        try {
                            rule.init(conversion.convertRuleClassInitJson());
                            value = rule.convert(value);
                        } catch (ConvertException e) {
                            throw ParseException.messageException(TransitionMessageSource.CONVERT_EXCEPTION_UNABLE_TO_CONVERT, errorTipName, e.getMessage());
                        }
                    } else {
                        throw ParseException.messageException(TransitionMessageSource.CONVERT_EXCEPTION_RULE_CLASS_IMPL, errorTipName, conversion.convertRuleClass().getCanonicalName(),IConvertRule.class.getCanonicalName());
                    }

                }
                try {
                    field.set(result,value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private AnnotationConvertClassReflect getClassReflect(R result) {
        AnnotationConvertClassReflect classReflect = classReflectMap.get(result.getClass().getName());
        if(classReflect == null) {
            classReflectMap.putIfAbsent(result.getClass().getName(),new AnnotationConvertClassReflect(result.getClass()));
            classReflect=classReflectMap.get(result.getClass().getName());
        }
        return classReflect;
    }
}
