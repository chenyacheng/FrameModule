package com.module.home.request;

import com.module.common.data.CaptchaBean;
import com.module.common.network.EventProcess;
import com.module.common.network.ResponseDataProcessListener;
import com.module.common.network.SingleLiveData;
import com.module.home.repository.HomeRepository;

/**
 * @author BD
 */
public class HomeRequest extends EventProcess {

    private final SingleLiveData<Object> captchaLiveData = new SingleLiveData<>();

    public SingleLiveData<Object> getCaptchaLiveData() {
        return captchaLiveData;
    }

    public void captcha(CaptchaBean bean) {
        HomeRepository.getInstance().captcha(bean, new ResponseDataProcessListener() {
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
}
