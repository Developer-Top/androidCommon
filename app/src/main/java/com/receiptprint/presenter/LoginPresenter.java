package com.receiptprint.presenter;


import com.receiptprint.bean.BaseInfo;
import com.receiptprint.contract.LoginContract;
import com.receiptprint.model.LoginModel;

import com.newing.core.base.BasePresenter;
import com.newing.core.base.BaseSingleObserver;
import com.receiptprint.bean.GlobalConfig;

import org.slf4j.LoggerFactory;

import java.util.Map;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Prestener {


    LoginContract.Model mModel;

    @Override
    protected void initModel() {
        mModel = new LoginModel();
    }

    @Override
    public void getData() {
        mModel.getData()
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseSingleObserver<BaseInfo>(LoginPresenter.this,true) {
                    @Override
                    public void onSuccessful(BaseInfo result) {
                        LoggerFactory.getLogger(LoginPresenter.class).debug("11=="+result);
                        mView.onData(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        LoggerFactory.getLogger(LoginPresenter.class).debug("22=="+errorMsg);
                    }
                })
        ;
    }


}