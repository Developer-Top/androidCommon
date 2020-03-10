package com.newing.utils.encrypt;

/**
 * Created by Administrator on 2017/6/30.
 */

public class EpayppApiException extends Exception {
    private static final long serialVersionUID = 1469808082556735750L;
    private String errCode;
    private String errMsg;

    public EpayppApiException() {
    }

    public EpayppApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public EpayppApiException(String message) {
        super(message);
    }

    public EpayppApiException(Throwable cause) {
        super(cause);
    }

    public EpayppApiException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}
