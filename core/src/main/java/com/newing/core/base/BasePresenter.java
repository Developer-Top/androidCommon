package com.newing.core.base;

import androidx.annotation.NonNull;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.SingleTransformer;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author ：LiMing
 * @date ：2019-10-22
 * @desc ：
 */
public abstract class BasePresenter<V extends BaseView> implements BasePresenterInterface<V>, BaseHttpListener {
    protected Logger mLogger = (Logger) LoggerFactory.getLogger(getClass());
    protected V mView;
    @NonNull
    protected CompositeDisposable compositeDisposable;

    @Override
    public final void setView(V view) {
        mView = view;
        initModel();
    }

    protected abstract void initModel();

    @Override
    public void subscribe() {
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.dispose();
        compositeDisposable = null;
    }


    protected <T> SingleTransformer<T, T> callbackOnIOToMainThread() {
        return com.newing.core.helper.SchedulerProviderHelper.callbackOnIOToMainThreadBySingle(compositeDisposable);
    }

    @Override
    public void showToast(String str) {
        mView.onError(str);
    }

    @Override
    public void hideWaitDialog() {
        mView.hideWaitDialog();
    }

    @Override
    public void showWaitDialog() {
        mView.showWaitDialog();
    }
}
