package com.module.common.data;

public class CaptchaBean {

    private final String sendType;
    private final String userAccount;

    public CaptchaBean(String sendType, String userAccount) {
        this.sendType = sendType;
        this.userAccount = userAccount;
    }
}
