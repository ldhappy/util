package com.ld.util.transition.message;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @ClassName TransitionMessageSource
 * @Description 转换过程中的提示语
 * @Author 梁聃
 * @Date 2021/9/18 10:42
 */
public class TransitionMessageSource extends ResourceBundleMessageSource {
    public static final String SOURCE_NULL="sourceNull";
    public static final String RESULT_NULL="resultNull";
    public static final String MUST_VALIDATE="mustValidate";
    //校验过程发生的错误异常---开始
    public static final String VALIDATE_EXCEPTION_UNMATCHED = "validateException.unmatched";
    public static final String VALIDATE_EXCEPTION_CREATE_RULE_CLASS = "validateException.createRuleClass";
    public static final String VALIDATE_EXCEPTION_RULE_CLASS_IMPL = "validateException.ruleClassImpl";
    public static final String VALIDATE_EXCEPTION_NULL = "validateException.null";
    //校验过程发生的错误异常---结束
    public static final String MUST_CONVERT="mustConvert";
    //转换过程发生的错误异常---开始
    public static final String CONVERT_EXCEPTION_UNABLE_TO_CONVERT = "convertException.unableToConvert";
    public static final String CONVERT_EXCEPTION_CREATE_RULE_CLASS = "convertException.createRuleClass";
    public static final String CONVERT_EXCEPTION_RULE_CLASS_IMPL = "convertException.ruleClassImpl";
    //转换过程发生的错误异常---结束

    private TransitionMessageSource() {
        setBasename("com.ld.util.transition.messages");
        setDefaultEncoding(System.getProperty("file.encoding"));
    }

    private static class TransitionMessageSourceAccessorInstance{
        private static final MessageSourceAccessor INSTANCE = new MessageSourceAccessor(new TransitionMessageSource());
    }

    public static MessageSourceAccessor getAccessor(){
        return TransitionMessageSourceAccessorInstance.INSTANCE;
    }
}
