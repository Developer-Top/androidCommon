package com.newing.core.helper;

import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ：LiMing
 * @date ：2019-10-22
 * @desc ：
 */
public class SchedulerProviderHelper {

    public static <T> ObservableTransformer<T, T> callbackOnIOToMainThread(final CompositeDisposable compositeDisposable) {
        return upstream -> upstream
                .compose(callbackOnIOToMainThread())
                .doOnLifecycle((Consumer<Disposable>) compositeDisposable::add, (Action) () -> {
                });

    }

    public static <T> ObservableTransformer<T, T> callbackOnIOToMainThread() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public static <T> SingleTransformer<T, T> callbackOnIOToMainThreadBySingle(final CompositeDisposable compositeDisposable) {
        return upstream -> upstream.compose(SchedulerProviderHelper
                .callbackOnIOToMainThreadBySingle())
                .doOnSubscribe((Consumer<Disposable>) compositeDisposable::add);
    }


    public static <T> SingleTransformer<T, T> callbackOnIOToMainThreadBySingle() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


}
