package com.module.common.data;

public class BaseDataBean {

    private String userDetailsKey;
    private String sessionId;

    public BaseDataBean(String userDetailsKey, String sessionId) {
        this.userDetailsKey = userDetailsKey;
        this.sessionId = sessionId;
    }
}
