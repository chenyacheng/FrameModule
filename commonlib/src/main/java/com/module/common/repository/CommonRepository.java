package com.module.common.repository;

import com.module.arch.base.BaseApi;
import com.module.arch.base.BaseRequest;
import com.module.common.AppConfig;
import com.module.common.api.CommonApi;
import com.module.common.data.CaptchaBean;
import com.module.common.data.RegisterBean;
import com.module.common.data.LoginBean;
import com.module.common.data.UpdateUserPwdBean;
import com.module.common.network.ResponseDataProcess;
import com.module.common.network.ResponseDataProcessListener;

/**
 * @author BD
 */
public class CommonRepository {

    public static CommonRepository getInstance() {
        return SingletonEnum.INSTANCE.getInstance();
    }

    public void captcha(CaptchaBean captchaBean, ResponseDataProcessListener responseDataProcessListener) {
        BaseRequest.getInstance().executeAsyncCallback(BaseApi.getInstance().getRetrofit(AppConfig.BASE_URL).create(CommonApi.class).captcha(captchaBean), new ResponseDataProcess(responseDataProcessListener));
    }

    public void register(RegisterBean registerBean, ResponseDataProcessListener responseDataProcessListener) {
        BaseRequest.getInstance().executeAsyncCallback(BaseApi.getInstance().getRetrofit(AppConfig.BASE_URL).create(CommonApi.class).register(registerBean), new ResponseDataProcess(responseDataProcessListener));
    }

    public void login(LoginBean loginBean, ResponseDataProcessListener responseDataProcessListener) {
        BaseRequest.getInstance().executeAsyncCallback(BaseApi.getInstance().getRetrofit(AppConfig.BASE_URL).create(CommonApi.class).login(loginBean), new ResponseDataProcess(responseDataProcessListener));
    }

    public void updateUserPassword(UpdateUserPwdBean updateUserPwdBean, ResponseDataProcessListener responseDataProcessListener) {
        BaseRequest.getInstance().executeAsyncCallback(BaseApi.getInstance().getRetrofit(AppConfig.BASE_URL).create(CommonApi.class).updateUserPassword(updateUserPwdBean), new ResponseDataProcess(responseDataProcessListener));
    }

    private enum SingletonEnum {
        // 枚举对象
        INSTANCE;
        private final CommonRepository singleton;

        SingletonEnum() {
            singleton = new CommonRepository();
        }

        CommonRepository getInstance() {
            return singleton;
        }
    }

}
