package com.rishiqing.qywx.web.exception;

public class MessageSignException extends RuntimeException {
    public MessageSignException() {
    }

    public MessageSignException(String message) {
        super(message);
    }

    public MessageSignException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageSignException(Throwable cause) {
        super(cause);
    }

    public MessageSignException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
