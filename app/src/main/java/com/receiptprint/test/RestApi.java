package com.receiptprint.test;

import com.receiptprint.bean.BaseInfo;
import com.receiptprint.bean.GlobalConfig;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestApi {
    /**
     * 获取全局变量
     *
     * @return 全局变量
     */
    @Headers({"Accept: application/json"})
    @GET("config/init")
    Single<GlobalConfig> getGlobalConfig(@Query("equipmentType") String equipmentType);


    /**
     *   查询设备信息
     * @param term_id  设备序列号
     * @return
     */
    @POST("gateway.htm?method=yumei.term.base.info.query")
    Single<BaseInfo> getTermBaseInfoQuery(@Query("term_id") String term_id);

}