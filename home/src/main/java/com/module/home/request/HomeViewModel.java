package com.module.home.request;

import com.module.common.network.BaseViewModel;
import com.module.common.network.ResponseDataProcessListener;
import com.module.common.network.SingleLiveData;
import com.module.home.repository.HomeRepository;

/**
 * @author BD
 */
public class HomeViewModel extends BaseViewModel {

    private final SingleLiveData<Object> testLiveData = new SingleLiveData<>();

    public void test() {
        HomeRepository.getInstance().test(new ResponseDataProcessListener() {
            @Override
            public void onSuccess(Object o) {
                testLiveData.postValue(o);
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

    public SingleLiveData<Object> getTest() {
        return testLiveData;
    }
}
