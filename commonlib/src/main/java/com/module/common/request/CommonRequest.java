package com.module.common.request;

import com.module.arch.callback.ProtectedUnPeekLiveData;
import com.module.arch.callback.UnPeekLiveData;
import com.module.common.data.CaptchaBean;
import com.module.common.data.RegisterBean;
import com.module.common.repository.CommonRepository;
import com.module.common.data.LoginBean;
import com.module.common.data.UpdateUserPwdBean;
import com.module.common.network.ResponseDataProcessListener;
import com.module.common.utils.EventProcess;

/**
 * 共用页面的请求
 *
 * @author BD
 * @date 2022/01/05
 */
public class CommonRequest extends EventProcess {

    private final UnPeekLiveData<Object> captchaLiveData = new UnPeekLiveData<>();

    private final UnPeekLiveData<Object> registerLiveData = new UnPeekLiveData<>();

    private final UnPeekLiveData<Object> loginLiveData = new UnPeekLiveData<>();

    private final UnPeekLiveData<Object> updateUserPasswordLiveData = new UnPeekLiveData<>();

    public ProtectedUnPeekLiveData<Object> getCaptchaLiveData() {
        return captchaLiveData;
    }

    public ProtectedUnPeekLiveData<Object> getRegisterLiveData() {
        return registerLiveData;
    }

    public ProtectedUnPeekLiveData<Object> getLoginLiveData() {
        return loginLiveData;
    }

    public ProtectedUnPeekLiveData<Object> getUpdateUserPasswordLiveData() {
        return updateUserPasswordLiveData;
    }

    public void captcha(CaptchaBean captchaBean) {
        CommonRepository.getInstance().captcha(captchaBean, new ResponseDataProcessListener() {
            @Override
            public void onSuccess(Object o) {
                captchaLiveData.postValue(o);
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
            public void onFailure(String s) {
                messageLiveData.postValue(s);
            }
        });
    }
}
