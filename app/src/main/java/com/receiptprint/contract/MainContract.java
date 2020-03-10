package com.receiptprint.contract;

import com.receiptprint.bean.GlobalConfig;
import com.newing.core.base.BaseModelInterface;
import com.newing.core.base.BasePresenterInterface;
import com.newing.core.base.BaseView;

import io.reactivex.Single;

/**
 * @author ：LiMing
 * @date ：2019-10-25
 * @desc ：
 */
public class MainContract {

    public  interface Model extends BaseModelInterface {

        Single<GlobalConfig> getData();
    }

    public  interface Prestener extends BasePresenterInterface<View> {
        void getData();
    }

    public  interface View extends BaseView {
        void onData(GlobalConfig globalConfig);
    }
}
