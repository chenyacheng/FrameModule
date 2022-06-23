package com.module.home.repository;

import com.module.common.AppConfig;
import com.module.common.api.CommonApi;
import com.module.common.network.ResponseDataProcess;
import com.module.common.network.ResponseDataProcessListener;
import com.module.network.BaseApi;
import com.module.network.BaseRequest;

/**
 * @author BD
 */
public class HomeRepository {

    public static HomeRepository getInstance() {
        return HomeRepository.SingletonEnum.INSTANCE.getInstance();
    }

    public void test(ResponseDataProcessListener responseDataProcessListener) {
        BaseRequest.getInstance().executeAsyncCallback(BaseApi.getInstance().getRetrofit(AppConfig.BASE_URL).create(CommonApi.class).test(), new ResponseDataProcess(responseDataProcessListener));
    }

    private enum SingletonEnum {
        // 枚举对象
        INSTANCE;
        private final HomeRepository singleton;

        SingletonEnum() {
            singleton = new HomeRepository();
        }

        HomeRepository getInstance() {
            return singleton;
        }
    }
}
