package com.module.common.message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * 用于跨页面进行数据传输
 *
 * @author BD
 * @date 2021/11/25
 */
public class SharedViewModel extends ViewModel {

    private final MutableLiveData<String> webViewContent = new MutableLiveData<>();

    public MutableLiveData<String> getWebViewContent() {
        return webViewContent;
    }

    public void setWebViewContent(String content) {
        webViewContent.setValue(content);
    }
}
