package com.newing.core.base;




public interface BasePresenterInterface<T extends BaseView> {

    void subscribe();

    void unsubscribe();

    void setView(T view);

}
