package com.newing.core.base;

import androidx.annotation.NonNull;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<T> {

    protected BaseObserver() {

    }

    private BaseHttpListener baseHttpListener;

    /**
     * 默认 提示错误
     *
     * @param baseHttpListener
     */
    public BaseObserver(BaseHttpListener baseHttpListener) {
        this.baseHttpListener = baseHttpListener;
    }

    /**
     * 请求时弹出加载框
     *
     * @param baseHttpListener
     * @param bl
     */
    public BaseObserver(BaseHttpListener baseHttpListener, boolean bl) {
        this.baseHttpListener = baseHttpListener;
        if (this.baseHttpListener != null && bl) {
            this.baseHttpListener.showWaitDialog();
        }
    }

    @Override
    public final void onNext(@NonNull T result) {
        onSuccess(result);
        if (this.baseHttpListener != null) {
            this.baseHttpListener.hideWaitDialog();
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
    public void onComplete() {

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
    }

    public abstract void onSuccess(T result);

    public abstract void onFailure(Throwable e, String errorMsg);
}
