package com.newing.core.base;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.newing.core.R;


/**
 * Created by sqq on 2020/1/7
 */
public class LoadingDialog extends Dialog  {

    private TextView mTvLoadingmsg;
    public LoadingDialog(Context context, String msg) {
        super(context, R.style.DialogBaseStyle_Loading);
        this.setContentView(R.layout.request_dialog);
        mTvLoadingmsg = (TextView) findViewById(R.id.id_tv_loadingmsg);
        if (!TextUtils.isEmpty(msg)) {
            mTvLoadingmsg.setVisibility(View.VISIBLE);
            mTvLoadingmsg.setText(msg);
        } else {
            setMsg(mMsg);
            mTvLoadingmsg.setVisibility(View.VISIBLE);
        }
        this.setCancelable(true);
    }
    private String mMsg="正在加载中请稍后";

    public void setMsg(String msg) {
      //  this.mMsg = msg;
        mTvLoadingmsg.setText(msg);
    }

}
