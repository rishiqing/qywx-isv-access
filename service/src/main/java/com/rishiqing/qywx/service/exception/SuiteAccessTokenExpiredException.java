package com.rishiqing.qywx.service.exception;

public class SuiteAccessTokenExpiredException extends Exception {
    public SuiteAccessTokenExpiredException() {
        super();
    }

    public SuiteAccessTokenExpiredException(String message) {
        super(message);
    }

    public SuiteAccessTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public SuiteAccessTokenExpiredException(Throwable cause) {
        super(cause);
    }

    protected SuiteAccessTokenExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
