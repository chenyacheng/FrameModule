package com.module.common;

/**
 * App配置参数
 *
 * @author bd
 * @date 2021/10/09
 */
public class AppConfig {

    public static final String WX_APP_ID = "11111111111111";
    public static final String BASE_URL = BuildConfig.BASE_URL;
    public static final String IMAGE_URL = BuildConfig.IMAGE_URL;

    private AppConfig() {
        throw new IllegalStateException("Utility class");
    }
}
