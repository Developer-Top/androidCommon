package com.newing.utils.codec;

/**
 * Created by Administrator on 2017/7/3.
 */

public class RequestParametersHolder {
    private EpayppHashMap protocalMustParams;
    private EpayppHashMap protocalOptParams;
    private EpayppHashMap applicationParams;

    public RequestParametersHolder() {
    }

    public EpayppHashMap getProtocalMustParams() {
        return this.protocalMustParams;
    }

    public void setProtocalMustParams(EpayppHashMap protocalMustParams) {
        this.protocalMustParams = protocalMustParams;
    }

    public EpayppHashMap getProtocalOptParams() {
        return this.protocalOptParams;
    }

    public void setProtocalOptParams(EpayppHashMap protocalOptParams) {
        this.protocalOptParams = protocalOptParams;
    }

    public EpayppHashMap getApplicationParams() {
        return this.applicationParams;
    }

    public void setApplicationParams(EpayppHashMap applicationParams) {
        this.applicationParams = applicationParams;
    }
}
