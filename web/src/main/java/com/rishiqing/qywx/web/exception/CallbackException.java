package com.rishiqing.qywx.web.exception;

public class CallbackException extends RuntimeException {
    public CallbackException() {
        super();
    }

    public CallbackException(String message) {
        super(message);
    }

    public CallbackException(String message, Throwable cause) {
        super(message, cause);
    }

    public CallbackException(Throwable cause) {
        super(cause);
    }

    protected CallbackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
