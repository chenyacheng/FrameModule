package com.module.common.store;

import android.content.Context;

/**
 * 用户信息存储
 *
 * @author bd
 * @date 2021/11/23
 */
public class UserInfo {

    private static SharedPreferencesUtils sharedPreferencesUtils;

    private UserInfo() {
    }

    public static UserInfo getInstance() {
        return UserInfo.UserInfoEnum.INSTANCE.getInstance();
    }

    public static void init(Context context) {
        sharedPreferencesUtils = new SharedPreferencesUtils(context, "userInfo");
    }

    public Object getData(String key, Object defaultObject) {
        return sharedPreferencesUtils.getSharedPreference(key, defaultObject);
    }

    public void setData(String key, Object object) {
        sharedPreferencesUtils.putSharedPreference(key, object);
    }

    public void clearAllData() {
        sharedPreferencesUtils.clear();
    }

    private enum UserInfoEnum {
        // 枚举对象
        INSTANCE;
        private final UserInfo singleton;

        UserInfoEnum() {
            singleton = new UserInfo();
        }

        UserInfo getInstance() {
            return singleton;
        }
    }
}
