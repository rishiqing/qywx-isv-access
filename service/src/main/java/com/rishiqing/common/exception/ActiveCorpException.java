package com.rishiqing.common.exception;

public class ActiveCorpException extends Exception {
    public ActiveCorpException() {
        super();
    }

    public ActiveCorpException(String message) {
        super(message);
    }

    public ActiveCorpException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActiveCorpException(Throwable cause) {
        super(cause);
    }

    protected ActiveCorpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
