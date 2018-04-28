package com.rishiqing.common.exception;

public class ReauthCorpException extends Exception {
    public ReauthCorpException() {
        super();
    }

    public ReauthCorpException(String message) {
        super(message);
    }

    public ReauthCorpException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReauthCorpException(Throwable cause) {
        super(cause);
    }

    protected ReauthCorpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
