package com.module.arch.base;

import com.module.arch.callback.ProtectedUnPeekLiveData;
import com.module.arch.callback.UnPeekLiveData;

/**
 * 事件处理：异常消息显示
 *
 * @author bd
 * @date 2021/10/08
 */
public class BaseEventProcess {

    protected final UnPeekLiveData<String> messageLiveData = new UnPeekLiveData<>();

    public ProtectedUnPeekLiveData<String> getMessageLiveData() {
        return messageLiveData;
    }
}
