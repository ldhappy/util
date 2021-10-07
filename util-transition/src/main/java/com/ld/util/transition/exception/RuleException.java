package com.ld.util.transition.exception;

/**
 * 规则的异常
 * 梁聃 2018/3/13 11:34
 */
public class RuleException extends IllegalStateException {

    private static final long serialVersionUID = 543957166879017956L;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public RuleException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public RuleException(String message, Throwable cause) {
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