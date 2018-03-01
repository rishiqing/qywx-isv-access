package com.rishiqing.common.exception;

public class HttpException extends RuntimeException {

    private long errcode;
    private String errmsg;

    public HttpException() {
        super();
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpException(Throwable cause) {
        super(cause);
    }

    protected HttpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public HttpException(long errcode, String errmsg){
        super(buildMessage(errcode, errmsg));
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    private static String buildMessage(long errcode, String errmsg) {
        return "http error, errcode is " + errcode + ", errmsg is " + errmsg;
    }
}
