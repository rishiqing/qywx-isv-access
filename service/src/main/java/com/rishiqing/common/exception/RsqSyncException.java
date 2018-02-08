package com.rishiqing.common.exception;

public class RsqSyncException extends Exception {

    private long errcode;
    private String errmsg;

    public RsqSyncException() {
        super();
    }

    public RsqSyncException(String message) {
        super(message);
    }

    public RsqSyncException(String message, Throwable cause) {
        super(message, cause);
    }

    public RsqSyncException(Throwable cause) {
        super(cause);
    }

    protected RsqSyncException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RsqSyncException(long errcode, String errmsg){
        super(buildMessage(errcode, errmsg));
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    private static String buildMessage(long errcode, String errmsg) {
        return "http error, errcode is " + errcode + ", errmsg is " + errmsg;
    }
}
