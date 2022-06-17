package com.module.common.request;

import com.module.common.data.CaptchaBean;
import com.module.common.data.LoginBean;
import com.module.common.data.RegisterBean;
import com.module.common.data.UpdateUserPwdBean;
import com.module.common.network.BaseViewModel;
import com.module.common.network.ResponseDataProcessListener;
import com.module.common.network.SingleLiveData;
import com.module.common.repository.CommonRepository;

/**
 * 共用页面的请求
 *
 * @author BD
 * @date 2022/01/05
 */
public class CommonRequest extends BaseViewModel {

    private final SingleLiveData<Object> captchaLiveData = new SingleLiveData<>();

    private final SingleLiveData<Object> registerLiveData = new SingleLiveData<>();

    private final SingleLiveData<Object> loginLiveData = new SingleLiveData<>();

    private final SingleLiveData<Object> updateUserPasswordLiveData = new SingleLiveData<>();

    public SingleLiveData<Object> getCaptchaLiveData() {
        return captchaLiveData;
    }

    public SingleLiveData<Object> getRegisterLiveData() {
        return registerLiveData;
    }

    public SingleLiveData<Object> getLoginLiveData() {
        return loginLiveData;
    }

    public SingleLiveData<Object> getUpdateUserPasswordLiveData() {
        return updateUserPasswordLiveData;
    }

    public void captcha(CaptchaBean captchaBean) {
        CommonRepository.getInstance().captcha(captchaBean, new ResponseDataProcessListener() {
            @Override
            public void onSuccess(Object o) {
                captchaLiveData.postValue(o);
            }

            @Override
            public void onSuccessNoData(String s) {
                messageLiveData.postValue(s);
            }

            @Override
            public void onFailure(String s) {
                messageLiveData.postValue(s);
            }
        });
    }

    public void register(RegisterBean registerBean) {
        CommonRepository.getInstance().register(registerBean, new ResponseDataProcessListener() {
            @Override
            public void onSuccess(Object o) {
                registerLiveData.postValue(o);
            }

            @Override
            public void onSuccessNoData(String s) {
                messageLiveData.postValue(s);
            }

            @Override
            public void onFailure(String s) {
                messageLiveData.postValue(s);
            }
        });
    }

    public void login(LoginBean loginBean) {
        CommonRepository.getInstance().login(loginBean, new ResponseDataProcessListener() {
            @Override
            public void onSuccess(Object o) {
                loginLiveData.postValue(o);
            }

            @Override
            public void onSuccessNoData(String s) {
                messageLiveData.postValue(s);
            }

            @Override
            public void onFailure(String s) {
                messageLiveData.postValue(s);
            }
        });
    }

    public void updateUserPassword(UpdateUserPwdBean updateUserPwdBean) {
        CommonRepository.getInstance().updateUserPassword(updateUserPwdBean, new ResponseDataProcessListener() {
            @Override
            public void onSuccess(Object o) {
                updateUserPasswordLiveData.postValue(o);
            }

            @Override
            public void onSuccessNoData(String s) {
                messageLiveData.postValue(s);
            }

            @Override
            public void onFailure(String s) {
                messageLiveData.postValue(s);
            }
        });
    }
}
