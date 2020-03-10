package com.receiptprint.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.newing.core.base.LoadingDialog;
import com.newing.core.base.LoadingDialog2;
import com.receiptprint.R;
import com.receiptprint.TestAlertDialog;
import com.receiptprint.bean.BaseInfo;
import com.receiptprint.contract.LoginContract;
import com.receiptprint.databinding.ActivityLoginBinding;
import com.receiptprint.presenter.LoginPresenter;
import com.receiptprint.bean.GlobalConfig;
import com.newing.core.base.BaseActivity;
import com.newing.core.helper.CoreConfigure;
import com.newing.core.rest.ApiManager;

import org.slf4j.LoggerFactory;

import java.util.Map;

public class LoginActivity extends BaseActivity<LoginPresenter, ActivityLoginBinding> implements LoginContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ApiManager.getInstance().initRest("http://192.168.1.120:9002/mapi/");
    }

    @Override
    protected void initData() {
        CoreConfigure.getInstance().setWaitDialog(LoadingDialog2.class);
        mPresenter.getData();

    }

    @Override
    protected void showData() {

    }

    @Override
    protected void releaseData() {

    }

    @Override
    public void onData(BaseInfo globalConfig) {
        Log.e("BaseInfo2", globalConfig.toString());
        LoggerFactory.getLogger(LoginActivity.class).debug(globalConfig.toString());
    }

}
