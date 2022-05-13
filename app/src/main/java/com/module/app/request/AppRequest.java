package com.module.app.request;

import com.module.app.repository.AppRepository;
import com.module.common.network.EventProcess;
import com.module.common.network.ResponseDataProcessListener;
import com.module.common.network.SingleLiveData;

/**
 * App页面的请求
 *
 * @author BD
 * @date 2021/11/22
 */
public class AppRequest extends EventProcess {

    private final SingleLiveData<Object> advertisingLiveData = new SingleLiveData<>();

    public SingleLiveData<Object> getAdvertisingLiveData() {
        return advertisingLiveData;
    }

    public void advertising() {
        AppRepository.getInstance().advertising(new ResponseDataProcessListener() {
            @Override
            public void onSuccess(Object o) {
                advertisingLiveData.postValue(o);
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
