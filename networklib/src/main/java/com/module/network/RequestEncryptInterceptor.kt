package com.module.network

import android.util.Log
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.Buffer
import java.net.URLDecoder
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * 对请求数据进行加密
 *
 * @author BD
 * @date 2021/12/28 15:56
 */
class RequestEncryptInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val charset = Charset.forName(StandardCharsets.UTF_8.toString())
        val requestBody = request.body
        if (null != requestBody) {
            val contentType = requestBody.contentType()
            try {
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                val requestData = URLDecoder.decode(buffer.readString(charset).trim(), StandardCharsets.UTF_8.toString())
                buffer.close()
                // 调用加密方法
                val encryptData = AES.encryptString(requestData)
                val data: MutableMap<String, String> = HashMap(1)
                data["Data"] = encryptData
                val dataString = Gson().toJson(data)
                // 构建新的请求体
                val newRequestBody = dataString.toRequestBody(contentType)
                request = request.newBuilder().method(request.method, newRequestBody).build()
            } catch (e: Exception) {
                Log.e("加密异常", e.message!!)
                return chain.proceed(request)
            }
        }
        return chain.proceed(request)
    }
}