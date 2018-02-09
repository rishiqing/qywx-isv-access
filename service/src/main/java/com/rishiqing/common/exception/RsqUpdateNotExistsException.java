package com.rishiqing.common.exception;

public class RsqUpdateNotExistsException extends Exception {

    private long errcode;
    private String errmsg;

    public RsqUpdateNotExistsException() {
        super();
    }

    public RsqUpdateNotExistsException(String message) {
        super(message);
    }

    public RsqUpdateNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RsqUpdateNotExistsException(Throwable cause) {
        super(cause);
    }

    protected RsqUpdateNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
