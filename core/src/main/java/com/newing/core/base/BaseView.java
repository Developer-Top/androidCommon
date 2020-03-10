package com.newing.core.base;

public interface BaseView {
    void onError(String errorMessage);

    void showWaitDialog();

    void hideWaitDialog();
}
