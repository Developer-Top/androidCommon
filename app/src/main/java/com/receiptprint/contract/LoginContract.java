package com.receiptprint.contract;

import com.newing.core.base.BaseModelInterface;
import com.newing.core.base.BasePresenterInterface;
import com.newing.core.base.BaseView;
import com.receiptprint.bean.BaseInfo;
import com.receiptprint.bean.GlobalConfig;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface LoginContract {
    public interface Model extends BaseModelInterface {

        Single<BaseInfo> getData();
    }

    public interface Prestener extends BasePresenterInterface<View> {
        void getData();
    }

    public interface View extends BaseView {
        void onData(BaseInfo globalConfig);
    }
}