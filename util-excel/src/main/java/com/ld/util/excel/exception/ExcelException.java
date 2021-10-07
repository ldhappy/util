package com.ld.util.excel.exception;

/**
 * excel解析过程中的异常
 * 梁聃 2019/1/6 9:49
 */
public class ExcelException extends IllegalStateException  {

    private static final long serialVersionUID = -3685921267739668378L;

    public ExcelException(String s) {
        super(s);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 无需进行堆栈填充，提高异常抛出执行效率
     * @return
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
