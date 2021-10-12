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

    //列头相关错误---开始
    public static final String HEADER_COORDINATE_ERROR = "header.coordinateError";
    //列头相关错误---结束


    //excel导出相关错误---开始
    public static final String WRITE_SHEET_EMPTY = "write.sheetEmpty";
    //sheet相关错误---开始
    public static final String WRITE_SHEET_HEADER_EMPTY = "write.sheet.headerEmpty";
    //导出对象未正确使用SheetRule注解，第一个入参：需要导出类名
    public static final String WRITE_SHEET_ANNOTATION_SHEET_RULE_NAME_EMPTY = "write.sheet.annotation.sheetRuleNameEmpty";
    //导出对象未正确使用ColumnHeaderRule注解，第一个入参：需要导出类名
    public static final String WRITE_SHEET_ANNOTATION_COLUMN_HEADER_RULE_EMPTY = "write.sheet.annotation.columnHeaderRuleEmpty";
    //导出失败统一使用的异常，第一个入参：错误详情
    public static final String WRITE_ERROR = "write.error";
    //sheet导出相关错误---结束
    //excel导出相关错误---结束

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
