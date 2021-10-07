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
    public static final String PARSE_FAIL = "parseFail";
    public static final String SOURCE_NULL="sourceNull";
    public static final String RESULT_NULL="resultNull";
    public static final String CREATE_RULE_EXCEPTION = "createRuleException";
    public static final String RULE_EXCEPTION = "ruleException";
    public static final String CREATE_RESULT_EXCEPTION = "createResultException";
    public static final String MUST_VALIDATE="mustValidate";
    public static final String MUST_CONVERT="mustConvert";

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
