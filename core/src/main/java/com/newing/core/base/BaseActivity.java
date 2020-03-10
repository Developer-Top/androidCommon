package com.newing.core.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


import com.newing.core.helper.CoreConfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity<T extends BasePresenterInterface, B extends ViewDataBinding> extends AppCompatActivity implements BaseView {
    public    Logger              logger      = LoggerFactory.getLogger(getClass());
    protected CompositeDisposable mDisposable = new CompositeDisposable();
    protected T                   mPresenter;
    protected Activity            aty;
    protected B                   mBinding;
    private   BaseDialogInterface mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aty = this;
        T presenter = createPresenter();
        if (presenter != null) {
            mPresenter = presenter;
            mPresenter.setView(this);
            mPresenter.subscribe();
        }
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), null, false);
        setContentView(mBinding.getRoot());
        initView(savedInstanceState);
        initData();
        showData();
    }

    protected abstract int getLayoutId();

    /**
     * 直接在createPresenter中拿到当前类的泛型的class，利用反射制造一个对象并返回
     */
    protected T createPresenter() {
        //这里获得到的是类的泛型的类型
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        if (type != null) {
            Type[]   actualTypeArguments = type.getActualTypeArguments();
            Class<T> tClass              = (Class<T>) actualTypeArguments[0];
            try {
                return tClass.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 初始化view 包含findViewById等
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 获取数据 初始化presenter等
     */
    protected abstract void initData();

    /**
     * 展示数据 显示已知数据到界面上 显示fragment 调用presenter接口等
     */
    protected abstract void showData();

    /**
     * 释放数据 释放presenter等
     */
    protected abstract void releaseData();

    @Override
    public void onError(final String errorMessage) {
        logger.debug(errorMessage);
        if (aty != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(aty, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        releaseData();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void showWaitDialog() {
        if (mDialog == null) {
            mDialog = CoreConfigure.getInstance().createDialogInterface();
        }
        if (mDialog != null) {
            mDialog.showWaitDialog(aty);
        }
    }

    @Override
    public void hideWaitDialog() {
        if (mDialog != null) {
            mDialog.hideWaitDialog();
        }
    }
}

