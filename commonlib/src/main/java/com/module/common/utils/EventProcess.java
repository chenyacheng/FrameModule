package com.module.common.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.module.arch.base.BaseEventProcess;
import com.module.arch.base.BaseRequest;

/**
 * 事件处理：网络取消
 *
 * @author bd
 * @date 2021/11/15
 */
public class EventProcess extends BaseEventProcess implements DefaultLifecycleObserver {

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        BaseRequest.getInstance().cancel();
        ProgressDialogUtils.getInstance().hideProgress();
    }
}
