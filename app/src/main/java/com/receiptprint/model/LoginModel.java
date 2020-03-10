package com.receiptprint.model;

import com.newing.core.base.BaseModel;
import com.receiptprint.bean.BaseInfo;
import com.receiptprint.contract.LoginContract;

import com.receiptprint.bean.GlobalConfig;
import com.receiptprint.test.RestApi;
import com.newing.core.rest.ApiFactory;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;

public class LoginModel extends BaseModel implements LoginContract.Model {

    @Override
    public Single<BaseInfo> getData() {
        return ApiFactory.getInstance().getApiService(RestApi.class).getTermBaseInfoQuery("fca015ae63");
    }
}