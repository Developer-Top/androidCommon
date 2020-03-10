package com.newing.core.rest;

import java.util.LinkedHashMap;

/**
 * @author ：LiMing
 * @date ：2019-10-25
 * @desc ：管理API单例
 */
public class ApiFactory {
    public static ApiFactory getInstance() {
        return ApiFactory.InstanceHolder.INSTANCE;
    }

    private LinkedHashMap<Class<?>, Object> mApiServices = new LinkedHashMap<>();


    public <T> T getApiService(Class<T> tClass) {
        if (checkApi(tClass)) {
            return (T) mApiServices.get(tClass);
        } else {
            return addApi(tClass);
        }
    }

    private <T> T addApi(Class<T> tClass) {
        T apiService = ApiManager.getInstance().getApiService(tClass);
        mApiServices.put(tClass, apiService);
        return apiService;
    }

    private <T> boolean checkApi(Class<T> tClass) {
        return mApiServices.get(tClass) != null;
    }


    private static class InstanceHolder {
        private static final ApiFactory INSTANCE = new ApiFactory();
    }

}
