package com.receiptprint.presenter;

import com.newing.core.base.BasePresenter;
import com.newing.core.base.BaseSingleObserver;
import com.receiptprint.bean.GlobalConfig;
import com.receiptprint.contract.MainContract;
import com.receiptprint.model.MainModel;


/**
 * @author ：LiMing
 * @date ：2019-10-25
 * @desc ：
 */
public class MainPrestener extends BasePresenter<MainContract.View> implements MainContract.Prestener {

    MainContract.Model mMainModel;

    @Override
    protected void initModel() {
        mMainModel = new MainModel();
    }

    @Override
    public void getData() {
        mMainModel.getData()
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseSingleObserver<GlobalConfig>() {
                    @Override
                    public void onSuccessful(GlobalConfig result) {
                        mView.onData(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {

                    }
                })
        ;
    }




}
