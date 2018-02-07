package com.rishiqing.qywx.service.exception;

public class ObjectNotExistException extends Exception {
    public ObjectNotExistException() {
        super();
    }

    public ObjectNotExistException(String message) {
        super(message);
    }

    public ObjectNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotExistException(Throwable cause) {
        super(cause);
    }

    protected ObjectNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
