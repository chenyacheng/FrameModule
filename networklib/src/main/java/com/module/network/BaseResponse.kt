package com.module.network

/**
 * 服务端返回的数据格式
 *
 * @author bd
 * @date 2021/10/08
 */
class BaseResponse {
    val code: String? = null
    val message: String? = null
    var data: Any? = null
}