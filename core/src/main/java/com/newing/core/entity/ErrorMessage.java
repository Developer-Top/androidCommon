package com.newing.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * 错误信息
 * Created by linlingrong on 2016-01-25.
 */
public class ErrorMessage extends RuntimeException{

    private static final String TAG = "ResultException";
    private int errCode = -1;
    private String response_key;
    public ErrorMessage(int errCode, String msg,String response_key)
    {
        super(msg);
        this.errCode = errCode;
        this.response_key=response_key;
    }

    public int getErrCode()
    {
        return errCode;
    }

    public void setErrCode(int errCode)
    {
        this.errCode = errCode;
    }

    public String getResponse_key() {
        return response_key;
    }

    public void setResponse_key(String response_key) {
        this.response_key = response_key;
    }
}
