package com.module.network

import java.lang.IllegalStateException

/**
 * @author vya
 * @date 2023/7/12 下午1:06
 */
class NetworkConfig private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        /**
         * 是否将请求数据加密和响应数据解密
         */
        const val ENCRYPT = false

        /**
         * 是否可以查看原始请求数据和响应数据
         */
        const val ORIGINAL_DATA = true

        /**
         * 是否展示日志
         */
        const val SHOW_LOG = false
    }
}