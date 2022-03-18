package com.module.app.request;

import com.module.arch.callback.ProtectedUnPeekLiveData;
import com.module.arch.callback.UnPeekLiveData;
import com.module.app.repository.AppRepository;
import com.module.common.network.ResponseDataProcessListener;
import com.module.common.utils.EventProcess;

/**
 * App页面的请求
 *
 * @author BD
 * @date 2021/11/22
 */
public class AppRequest extends EventProcess {

    private final UnPeekLiveData<Object> advertisingLiveData = new UnPeekLiveData<>();

    public ProtectedUnPeekLiveData<Object> getAdvertisingLiveData() {
        return advertisingLiveData;
    }

    public void advertising() {
        AppRepository.getInstance().advertising(new ResponseDataProcessListener() {
            @Override
            public void onSuccess(Object o) {
                advertisingLiveData.postValue(o);
            }

            @Override
            public void onFailure(String s) {
                messageLiveData.postValue(s);
            }
        });
    }
}
