package com.ld.util.transition.exception;

/**
 * 规则的异常
 * 梁聃 2018/3/13 11:34
 */
public class ConvertException extends IllegalStateException {

    private static final long serialVersionUID = 543957166879017956L;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ConvertException(String message) {
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
}