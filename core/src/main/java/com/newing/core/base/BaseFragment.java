package com.newing.core.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.newing.core.helper.CoreConfigure;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseFragment<T extends BasePresenterInterface, B extends ViewDataBinding> extends Fragment implements BaseView {
    protected Logger              logger      = LoggerFactory.getLogger(getClass());
    protected CompositeDisposable mDisposable = new CompositeDisposable();
    protected T                   mPresenter;
    protected B                   mBinding;
    protected Activity            aty;
    private   BaseDialogInterface mDialog;
    /**
     * Fragment根视图
     */
    protected View                mFragmentRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        aty = getActivity();
        mFragmentRootView = inflaterView(inflater, container, savedInstanceState);
        if (getPresenter() != null) {
            mPresenter = getPresenter();
            mPresenter.setView(this);
            mPresenter.subscribe();
        }
        initView(mFragmentRootView);
        initData();
        showData();
        return mFragmentRootView;
    }

    /**
     * 加载View
     *
     * @param inflater  LayoutInflater
     * @param container ViewGroup
     * @param bundle    Bundle
     * @return
     */
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return mBinding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible() && getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            getData();
        }
    }


    protected abstract int getLayoutId();

    @NotNull
    protected abstract T getPresenter();

    @Override
    public void onError(final String errorMessage) {
        logger.debug(errorMessage);
        if (aty != null) {
            aty.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(aty, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getData();
        }
    }

    /**
     * 初始化view 包含findViewById等
     *
     * @param view fragment
     */
    protected abstract void initView(View view);

    /**
     * 获取数据 调用presenter接口
     */
    protected abstract void initData();

    /**
     * 展示数据 显示到界面上
     */
    protected abstract void showData();

    /**
     * 释放数据 释放presenter
     */
    protected abstract void releaseData();

    protected abstract void getData();


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
