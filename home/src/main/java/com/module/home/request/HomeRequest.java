package com.module.home.request;

import com.module.arch.callback.ProtectedUnPeekLiveData;
import com.module.arch.callback.UnPeekLiveData;
import com.module.common.data.CaptchaBean;
import com.module.common.network.ResponseDataProcessListener;
import com.module.common.utils.EventProcess;
import com.module.home.repository.HomeRepository;

/**
 * @author BD
 */
public class HomeRequest extends EventProcess {

    private final UnPeekLiveData<Object> captchaLiveData = new UnPeekLiveData<>();

    public ProtectedUnPeekLiveData<Object> getCaptchaLiveData() {
        return captchaLiveData;
    }

    public void captcha(CaptchaBean bean) {
        HomeRepository.getInstance().captcha(bean, new ResponseDataProcessListener() {
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
}
