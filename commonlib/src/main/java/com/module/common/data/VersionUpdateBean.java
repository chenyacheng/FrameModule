package com.module.common.data;

import com.module.common.store.UserInfoResult;

public class VersionUpdateBean extends BaseDataBean {

    private final String terminalType;
    private final String versionCode;

    public VersionUpdateBean(String terminalType, String versionCode) {
        super(UserInfoResult.getUserInfo().getUserDetailsKey(), UserInfoResult.getUserInfo().getSessionId());
        this.terminalType = terminalType;
        this.versionCode = versionCode;
    }
}
