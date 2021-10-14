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

    //列头相关错误---开始
    public static final String HEADER_COORDINATE_ERROR = "header.coordinateError";
    //列头相关错误---结束


    //excel导出相关错误---开始
    public static final String WRITE_FILE_NAME_PRE_EMPTY = "write.fileNamePreEmpty";
    public static final String WRITE_RESULT_OUTPUT_EMPTY = "write.resultOutPutEmpty";
    public static final String WRITE_SHEET_NAME_EMPTY = "write.sheetNameEmpty";
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

    //excel导入相关错误---开始
    public static final String READ_SOURCE_KEY_EMPTY = "read.sourceKeyEmpty";
    public static final String READ_SOURCE_INPUT_EMPTY = "read.sourceInputEmpty";
    public static final String READ_READ_RULE_EMPTY = "read.readRuleEmpty";
    public static final String READ_READ_RULE_TARGET_CLASS_EMPTY = "read.readRule.targetClassEmpty";
    public static final String READ_ROW_CONTENT_READER_EMPTY = "read.rowContentReaderEmpty";
    //文档超过最大可解析的行数，第一个入参：支持的最大可解析行数
    public static final String READ_OVERFLOW_MAX_ROWS = "read.overflowMaxRows";

    public static final String READ_ANALYSIS_ERROR_XLS_NO_SST_RECORD = "read.analysisErrorXlsNoSstRecord";
    //导入指定的文件地址不存在，第一个入参：指定的文件地址
    public static final String READ_SOURCE_INPUT_FILE_NOT_EXIST = "read.sourceInput.fileNotExist";
    //导入的文件类型不支持，第一个入参：当前导入文件类型
    public static final String READ_FILE_POSTFIX_NONSUPPORT = "read.filePostfixNonsupport";
    //列头检查相关错误---开始
    public static final String READ_MATCHING_RULE_RULE_EMPTY = "read.matchingRule.ruleEmpty";
    public static final String READ_MATCHING_RULE_HEADER_EMPTY = "read.matchingRule.headerEmpty";
    //导入文件的列头数量和模板列头数量不一致，第一个入参：导入文件列头数量，第二个入参：规则列头数量
    public static final String READ_MATCHING_RULE_HEADER_SIZE_ERROR = "read.matchingRule.headerSizeError";
    //导入文件的列头数量和模板列头数量不一致(模糊匹配版)，第一个入参：规则列头数量，第二个入参：导入文件列头数量
    public static final String READ_MATCHING_RULE_HEADER_SIZE_ERROR_FOR_FUZZY = "read.matchingRule.headerSizeErrorForFuzzy";
    //模板要求的指定列导入文件中不存在，第一个入参：规则列索引，第二个入参：模板列名
    public static final String READ_MATCHING_RULE_UNMATCH_COORDINATE = "read.matchingRule.unMatchCoordinate";
    //模板要求的指定列导入文件中对应位置的列名不一致，第一个入参：规则列索引，第二个入参：模板列名，第三个入参：导入文件列名
    public static final String READ_MATCHING_RULE_UNMATCH_NAME = "read.matchingRule.unMatchName";
    //列头检查相关错误---结束


    public static final String READ_RESULT_FAULT_ROW_EMPTY = "read.result.faultRowEmpty";

    //导入失败统一使用的异常，第一个入参：错误详情
    public static final String READ_ERROR = "read.error";

    //excel导入相关错误---结束

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
