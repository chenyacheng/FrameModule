package com.module.common.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

import com.module.common.utils.ProgressDialogUtils;
import com.module.network.BaseRequest;

/**
 * 事件处理：
 * 1：异常消息显示
 * 2：网络取消
 *
 * @author bd
 * @date 2021/11/15
 */
public class BaseViewModel extends ViewModel implements DefaultLifecycleObserver {

    protected final SingleLiveData<String> messageLiveData = new SingleLiveData<>();

    public SingleLiveData<String> getMessageLiveData() {
        return messageLiveData;
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        BaseRequest.getInstance().cancel();
        ProgressDialogUtils.getInstance().hideProgress();
    }
}
