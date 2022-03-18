package com.module.app.repository;

import com.module.arch.base.BaseApi;
import com.module.arch.base.BaseRequest;
import com.module.common.AppConfig;
import com.module.common.api.CommonApi;
import com.module.common.network.ResponseDataProcess;
import com.module.common.network.ResponseDataProcessListener;

/**
 * @author BD
 */
public class AppRepository {

    public static AppRepository getInstance() {
        return AppRepository.SingletonEnum.INSTANCE.getInstance();
    }

    public void advertising(ResponseDataProcessListener responseDataProcessListener) {
        BaseRequest.getInstance().executeAsyncCallback(BaseApi.getInstance().getRetrofit(AppConfig.BASE_URL).create(CommonApi.class).advertisingPage(), new ResponseDataProcess(responseDataProcessListener));
    }

    private enum SingletonEnum {
        // 枚举对象
        INSTANCE;
        private final AppRepository singleton;

        SingletonEnum() {
            singleton = new AppRepository();
        }

        AppRepository getInstance() {
            return singleton;
        }
    }

}
