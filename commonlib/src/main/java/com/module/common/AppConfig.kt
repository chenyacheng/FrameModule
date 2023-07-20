package com.module.common

import java.lang.IllegalStateException

/**
 * App配置参数
 *
 * @author bd
 * @date 2021/10/09
 */
class AppConfig private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        const val WX_APP_ID = "11111111111111"
        const val BASE_URL = "http://192.168.0.1:8000"
        const val IMAGE_URL = "http://static.xxx.cn"

        /**
         * 是否开启登录拦截
         */
        const val LOGIN_INTERCEPTOR = false
        const val BUGLY = "11111"

        /**
         * 日志将会在 Logcat 中输出，false 时不输出
         */
        const val BUGLY_LOG = false
    }
}