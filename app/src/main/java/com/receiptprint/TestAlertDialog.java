package com.receiptprint;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.newing.core.base.BaseDialogInterface;

/**
 * @author ：LiMing
 * @date ：2019-10-24
 * @desc ：
 */
public class TestAlertDialog implements BaseDialogInterface {

    private AlertDialog alertDialog;


    @Override
    public void showWaitDialog(Context context) {
         AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("请回答");
        builder.setMessage("你觉得我好看吗？？");
        builder.setPositiveButton("当然是好看了！！", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("我觉得一般", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("我觉得不好看", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void hideWaitDialog() {

        alertDialog.dismiss();
    }
}
