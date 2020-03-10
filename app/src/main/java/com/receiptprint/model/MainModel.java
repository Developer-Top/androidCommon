package com.receiptprint.model;

import com.receiptprint.bean.GlobalConfig;
import com.receiptprint.contract.MainContract;
import com.receiptprint.test.RestApi;
import com.newing.core.base.BaseModel;
import com.newing.core.rest.ApiFactory;
import io.reactivex.Single;

/**
 * @author ：LiMing
 * @date ：2019-10-25
 * @desc ：
 */
public class MainModel extends BaseModel implements MainContract.Model {

    @Override
    public Single<GlobalConfig> getData() {
        return ApiFactory.getInstance().getApiService(RestApi.class).getGlobalConfig("handhold_terminal");
    }
}
