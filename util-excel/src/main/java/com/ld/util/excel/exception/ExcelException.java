package com.ld.util.excel.exception;

import com.ld.util.excel.message.ExcelMessageSource;

/**
 * excel解析过程中的异常
 * 梁聃 2019/1/6 9:49
 */
public class ExcelException extends IllegalStateException  {

    private static final long serialVersionUID = -3685921267739668378L;

    private ExcelException(String s) {
        super(s);
    }

    /**
     * 根据模板创建异常
     * @param message 国际化模板名称
     * @param args
     * @return
     */
    public static ExcelException messageException(String message, Object... args){
        return new ExcelException(String.format(ExcelMessageSource.getAccessor().getMessage(message),args));
    }

    /**
     * 根据错误信息创建异常
     * @param errorInfo
     * @return
     */
    public static ExcelException infoException(String errorInfo){
        return new ExcelException(errorInfo);
    }
}
