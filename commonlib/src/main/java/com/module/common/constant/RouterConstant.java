package com.module.common.constant;

/**
 * 路由常量类
 * 路由跳转命名统一用：path+模块名+Activity名
 *
 * @author bd
 * @date 2021/11/16
 */
public class RouterConstant {

    public static final String PATH_COMMON_WEB_VIEW_ACTIVITY = "/common/WebViewActivity";
    public static final String PATH_COMMON_LOGIN_ACTIVITY = "/common/LoginActivity";
    public static final String PATH_COMMON_REGISTER_ACTIVITY = "/common/RegisterActivity";
    public static final String PATH_COMMON_MODIFY_PASSWORD_ACTIVITY = "/common/ModifyPasswordActivity";

    public static final String PATH_APP_MAIN_FRAGMENT_ACTIVITY = "/app/MainFragmentActivity";
    public static final String PATH_APP_ADVERTISING_ACTIVITY = "/app/AdvertisingActivity";

    private RouterConstant() {
        throw new IllegalStateException("Utility class");
    }
}
