package com.ld.util.excel.exception.info;

/**
 * 默认导入异常错误信息提示类
 */
public class DefaultExceptionInfo {
    public static DefaultExceptionInfo defaultExceptionInfo = new DefaultExceptionInfo();
    public String OVERFLOW_MAX_ROWS = "当前excel已超过可解析的最大行数";
    public String  ANALYSIS_ERROR_XLS_NO_SST_RECORD = "excel的解析异常，没有找到SSTRecord";
    /**
     * 可由继承类创建
     */
    protected DefaultExceptionInfo(){

    }
}
