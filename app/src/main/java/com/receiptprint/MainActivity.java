package com.receiptprint;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.receiptprint.bean.GlobalConfig;
import com.receiptprint.contract.MainContract;
import com.receiptprint.databinding.ActivityMainBinding;
import com.receiptprint.presenter.MainPrestener;
import com.newing.core.base.BaseActivity;
import com.newing.core.helper.CoreConfigure;
import com.newing.core.rest.ApiManager;

import androidx.annotation.Nullable;


public class MainActivity extends BaseActivity<MainPrestener, ActivityMainBinding> implements MainContract.View {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ApiManager.getInstance().initRest("http://192.168.11.74:8089/jc/");
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void showData() {

    }

    @Override
    protected void releaseData() {

    }

    public void btnClick(View view) {

        CoreConfigure.getInstance().setWaitDialog(TestAlertDialog.class);
        mPresenter.getData();
        showWaitDialog();
    }



    @Override
    public void onData(GlobalConfig globalConfig) {
        Log.e("lm", globalConfig.toString());
    }
}
