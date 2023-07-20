package com.module.network

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 网络对象层
 *
 * @author bd
 * @date 2021/10/08
 */
class BaseApi {

    /**
     * 使用OkHttp配置的Retrofit
     *
     * @param baseUrl 基本url
     * @return retrofit
     */
    fun getRetrofit(baseUrl: String): Retrofit {
        // 增加头部信息
        val headerInterceptor = Interceptor { chain: Interceptor.Chain ->
            val build = chain.request().newBuilder() // 设置允许请求json数据
                .addHeader("Content-Type", "application/json")
                .addHeader("Connection", "close")
                .build()
            chain.proceed(build)
        }
        // 创建一个OkHttpClient并设置超时时间
        val builder = getOkHttpClient()
        builder.addInterceptor(headerInterceptor)
        // 需要参数加密
        if (NetworkConfig.ENCRYPT) {
            builder.addInterceptor(RequestEncryptInterceptor())
            builder.addInterceptor(ResponseDecryptInterceptor())
        }
        return Retrofit.Builder()
            .client(builder.build())
            .baseUrl(baseUrl)
            // 请求结果转换为基本类型，一般为String
            .addConverterFactory(ScalarsConverterFactory.create())
            // 请求的结果转为实体类，防止 int 类型自动转成 double 类型
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.LAZILY_PARSED_NUMBER
                    ).serializeNulls().create()
                )
            )
            .build()
    }

    /**
     * 无超时及缓存策略的Retrofit
     *
     * @param baseUrl 基本url
     * @return Retrofit的实例
     */
    fun getSimpleRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getOkHttpClient().build())
            // 请求结果转换为基本类型，一般为String
            .addConverterFactory(ScalarsConverterFactory.create())
            // 请求的结果转为实体类
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient.Builder {
        // 定制OkHttp
        val httpClientBuilder = OkHttpClient.Builder()
        // OkHttp进行添加拦截器loggingInterceptor
        if (NetworkConfig.ORIGINAL_DATA) {
            httpClientBuilder.addInterceptor(getLevel())
        }
        httpClientBuilder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
        return httpClientBuilder
    }

    // 日志级别分为4类：NONE、BASIC、HEADERS、BODY；NONE无；BASIC请求/响应行；HEADERS请求/响应行+头；BODY请求/响应行+头+体
    // 日志显示级别
    private fun getLevel(): HttpLoggingInterceptor {
        // 日志级别分为4类：NONE、BASIC、HEADERS、BODY；NONE无；BASIC请求/响应行；HEADERS请求/响应行+头；BODY请求/响应行+头+体
        // 日志显示级别
        val level: HttpLoggingInterceptor.Level = if (NetworkConfig.SHOW_LOG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        // 新建log拦截器
        val loggingInterceptor = HttpLoggingInterceptor { message: String ->
            showLogCompletion(
                "LoggingMessage",
                message,
                1000
            )
        }
        return loggingInterceptor.setLevel(level)
    }

    private fun showLogCompletion(tag: String, log: String, showCount: Int) {
        if (log.length > showCount) {
            val show = log.substring(0, showCount)
            Log.v(tag, show)
            // 剩下的文本还是大于规定长度
            if (log.length - showCount > showCount) {
                val partLog = log.substring(showCount)
                showLogCompletion(tag, partLog, showCount)
            } else {
                val surplusLog = log.substring(showCount)
                Log.v(tag, surplusLog)
            }
        } else {
            Log.v(tag, log)
        }
    }

    private enum class SingletonEnum {
        // 枚举对象
        INSTANCE;

        val instance: BaseApi = BaseApi()
    }

    companion object {
        /**
         * 连接时长，单位：秒
         */
        private const val CONNECT_TIME_OUT = 12L

        /**
         * 读超时长，单位：秒
         */
        private const val READ_TIME_OUT = 60L

        /**
         * 写超时长，单位：秒
         */
        private const val WRITE_TIME_OUT = 60L

        @JvmStatic
        val instance: BaseApi
            get() = SingletonEnum.INSTANCE.instance
    }
}