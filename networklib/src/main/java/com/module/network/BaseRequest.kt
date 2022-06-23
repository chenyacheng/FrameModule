package com.module.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

/**
 * 请求网络封装类
 *
 * @author bd
 * @date 2021/10/09
 */
class BaseRequest {

    private var baseResponseCall: Call<Any>? = null
    @Volatile
    private var disposed = false

    fun executeAsyncCallback(call: Call<Any>?, responseListener: ResponseListener) {
        baseResponseCall = call
        disposed = false
        baseResponseCall!!.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                if (response.isSuccessful) {
                    if (null != response.body()) {
                        if (disposed) {
                            return
                        }
                        responseListener.onSuccess(response.body()!!)
                        baseResponseCall = null
                    }
                } else {
                    val t: Throwable = HttpException(response)
                    responseListener.onFailure(t)
                    baseResponseCall = null
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                responseListener.onFailure(t)
                baseResponseCall = null
            }
        })
    }

    fun cancel() {
        if (null != baseResponseCall && !baseResponseCall!!.isCanceled) {
            baseResponseCall!!.cancel()
            baseResponseCall = null
            disposed = true
        }
    }

    private enum class SingletonEnum {
        // 枚举对象
        INSTANCE;

        val instance: BaseRequest = BaseRequest()
    }

    companion object {
        @JvmStatic
        val instance: BaseRequest get() = SingletonEnum.INSTANCE.instance
    }
}