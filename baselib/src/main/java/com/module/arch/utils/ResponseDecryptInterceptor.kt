package com.module.arch.utils

import android.text.TextUtils
import com.google.gson.reflect.TypeToken
import com.module.arch.base.BaseResponse
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * 对响应数据进行解密
 *
 * @author BD
 * @date 2021/12/28 17:23
 */
class ResponseDecryptInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        if (response.isSuccessful) {
            val code = 200
            if (code == response.code) {
                val responseBody = response.body
                if (null != responseBody) {
                    val oldResponseBodyStr = responseBody.string()
                    if (!TextUtils.isEmpty(oldResponseBodyStr)) {
                        val bean = GsonUtils.fromJson(oldResponseBodyStr, BaseResponse::class.java)
                        val contentType = responseBody.contentType()
                        if (null != bean.data) {
                            //加密的响应数据用AES秘钥解密
                            val newResponseBodyStr: String
                            if (bean.data is List<*>) {
                                if ((bean.data as List<*>).size != 0) {
                                    newResponseBodyStr = AES.decryptString(bean.data.toString())
                                    val type = object : TypeToken<Any?>() {}.type
                                    val obj =
                                        GsonUtils.stringToObject<Any>(newResponseBodyStr, type)
                                    bean.data = obj
                                }
                            } else {
                                newResponseBodyStr = AES.decryptString(bean.data.toString())
                                val type = object : TypeToken<Any?>() {}.type
                                val obj = GsonUtils.stringToObject<Any>(newResponseBodyStr, type)
                                bean.data = obj
                            }
                        }
                        responseBody.close()
                        // 构造新的response
                        val dataString = GsonUtils.objectToString(bean)
                        val newResponseBody = dataString.toResponseBody(contentType)
                        response = response.newBuilder().body(newResponseBody).build()
                    }
                }
                return response
            }
            return response
        }
        return response
    }
}