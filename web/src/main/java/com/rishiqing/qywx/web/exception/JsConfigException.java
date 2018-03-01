package com.rishiqing.qywx.web.exception;

public class JsConfigException extends RuntimeException {
    public JsConfigException() {
    }

    public JsConfigException(String message) {
        super(message);
    }

    public JsConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsConfigException(Throwable cause) {
        super(cause);
    }

    public JsConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
