package com.module.app.request;

import com.module.app.repository.AppRepository;
import com.module.common.network.BaseViewModel;
import com.module.common.network.ResponseDataProcessListener;
import com.module.common.network.SingleLiveData;

/**
 * @author BD
 */
public class AppViewModel extends BaseViewModel {

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
