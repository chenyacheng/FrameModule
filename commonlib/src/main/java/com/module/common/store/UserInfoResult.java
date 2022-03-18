package com.module.common.store;

import com.module.arch.utils.GsonUtils;
import com.module.common.data.LoginResult;

/**
 * @author BD
 */
public class UserInfoResult {

    private UserInfoResult() {
    }

    public static LoginResult getUserInfo() {
        return GsonUtils.fromJson((String) UserInfo.getInstance().getData("userInfo", ""), LoginResult.class);
    }

    public static void updateUserInfo(LoginResult loginResult) {
        UserInfo.getInstance().setData("userInfo", GsonUtils.objectToString(loginResult));
    }
}
