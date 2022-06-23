package com.module.common.constant;

/**
 * 接口后缀名
 *
 * @author BD
 * @date 2021/11/24
 */
public class UrlConstant {

    public static final String SEND_SMS = "/system/sendSms";
    public static final String REGISTER = "/user/register";
    public static final String LOGIN = "/user/login";
    public static final String UPDATE_USER_PASSWORD = "/system/updateUserPassword";
    public static final String UPLOAD_USER_PORTRAIT = "/user/uploadUserPortrait";
    public static final String QUERY_START_PAGE = "/content/queryStartPage";
    public static final String VERIFY_VERSION = "/system/verifyVersion";
    public static final String TEST = "/system/test";

    private UrlConstant() {
        throw new IllegalStateException("Utility class");
    }


}
