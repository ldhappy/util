package com.ld.util.excel.message;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @ClassName ExcelMessageSource
 * @Description excel转换中的提示语
 * @Author 梁聃
 * @Date 2021/9/18 10:42
 */
public class ExcelMessageSource extends ResourceBundleMessageSource {
    public static final String OVERFLOW_MAX_ROWS = "overflowMaxRows";
    public static final String ANALYSIS_ERROR_XLS_NO_SST_RECORD = "analysisErrorXlsNoSstRecord";

    private ExcelMessageSource() {
        setBasename("com.ld.util.excel.messages");
        setDefaultEncoding(System.getProperty("file.encoding"));
    }

    private static class ExcelMessageSourceAccessorInstance{
        private static final MessageSourceAccessor INSTANCE = new MessageSourceAccessor(new ExcelMessageSource());
    }

    public static MessageSourceAccessor getAccessor(){
        return ExcelMessageSourceAccessorInstance.INSTANCE;
    }
}
