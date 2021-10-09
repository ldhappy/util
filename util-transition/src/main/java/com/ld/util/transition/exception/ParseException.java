package com.ld.util.transition.exception;

import com.ld.util.transition.message.TransitionMessageSource;

/**
 * 导入文件过程中的异常
 * 梁聃 2018/3/13 11:34
 */
public class ParseException  extends IllegalStateException {

    private static final long serialVersionUID = 543957166879017956L;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    private ParseException(String message) {
        super(message);
    }

    /**
     * 无需进行堆栈填充，提高异常抛出执行效率
     * @return
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    /**
     * 根据模板创建异常
     * @param message 国际化模板名称
     * @param args
     * @return
     */
    public static ParseException messageException(String message, Object... args){
        throw new ParseException(String.format(TransitionMessageSource.getAccessor().getMessage(message),args));
    }

    /**
     * 根据错误信息创建异常
     * @param errorInfo
     * @return
     */
    public static ParseException messageException(String errorInfo){
        throw new ParseException(errorInfo);
    }
}