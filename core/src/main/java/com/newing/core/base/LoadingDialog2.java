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
public class LoadingDialog2 implements BaseDialogInterface {

    LoadingDialog loadingDialog;
    @Override
    public void showWaitDialog(Context context) {
        loadingDialog=new LoadingDialog(context,"");
        loadingDialog.show();
    }

    @Override
    public void hideWaitDialog() {
        loadingDialog.dismiss();
    }
}
