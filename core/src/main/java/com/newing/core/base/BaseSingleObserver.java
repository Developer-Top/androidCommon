package com.newing.core.base;

import com.newing.core.rest.ApiManager;

import org.slf4j.LoggerFactory;

import androidx.annotation.NonNull;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public abstract class BaseSingleObserver<T> implements SingleObserver<T> {

    protected BaseSingleObserver() {

    }

    private BaseHttpListener baseHttpListener;

    /**
     * 默认 提示错误
     *
     * @param baseHttpListener
     */
    public BaseSingleObserver(BaseHttpListener baseHttpListener) {
        this.baseHttpListener = baseHttpListener;
    }

    /**
     * 请求时弹出加载框
     *
     * @param baseHttpListener
     * @param bl
     */
    public BaseSingleObserver(BaseHttpListener baseHttpListener, boolean bl) {
        this.baseHttpListener = baseHttpListener;
        if (this.baseHttpListener != null && bl) {
            this.baseHttpListener.showWaitDialog();
        }
    }


    @Override
    public void onError(@NonNull Throwable e) {

        String error = HttpExceptionUtil.exceptionHandler(e);
        onFailure(e, error);
        if (this.baseHttpListener != null) {
            this.baseHttpListener.hideWaitDialog();
            this.baseHttpListener.showToast(error);
        }

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
    }

    @Override
    public void onSuccess(T result) {

        onSuccessful(result);
        if (this.baseHttpListener != null) {
            this.baseHttpListener.hideWaitDialog();
        }
    }

    public abstract void onSuccessful(T result);

    public abstract void onFailure(Throwable e, String errorMsg);
}
