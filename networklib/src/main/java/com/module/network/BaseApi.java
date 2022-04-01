package com.module.network;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 网络对象层
 *
 * @author bd
 * @date 2021/10/08
 */
public class BaseApi {
    /**
     * 连接时长，单位：秒
     */
    private static final int CONNECT_TIME_OUT = 12;
    /**
     * 读超时长，单位：秒
     */
    private static final int READ_TIME_OUT = 60;
    /**
     * 写超时长，单位：秒
     */
    private static final int WRITE_TIME_OUT = 60;

    public static BaseApi getInstance() {
        return SingletonEnum.INSTANCE.getInstance();
    }

    /**
     * 使用OkHttp配置的Retrofit
     *
     * @param baseUrl 基本url
     * @return retrofit
     */
    public Retrofit getRetrofit(String baseUrl) {
        // 增加头部信息
        Interceptor headerInterceptor = chain -> {
            Request build = chain.request().newBuilder()
                    // 设置允许请求json数据
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Connection", "close")
                    .build();
            return chain.proceed(build);
        };
        // 创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS);
        builder.addInterceptor(headerInterceptor);
        if (BuildConfig.ORIGINAL_DATA) {
            builder.addInterceptor(getLevel());
        }
        // 需要参数加密
        if (BuildConfig.ENCRYPT) {
            builder.addInterceptor(new RequestEncryptInterceptor());
            builder.addInterceptor(new ResponseDecryptInterceptor());
        }
        OkHttpClient client = builder.build();

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                // 请求结果转换为基本类型，一般为String
                .addConverterFactory(ScalarsConverterFactory.create())
                // 请求的结果转为实体类
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private HttpLoggingInterceptor getLevel() {
        // 日志级别分为4类：NONE、BASIC、HEADERS、BODY；NONE无；BASIC请求/响应行；HEADERS请求/响应行+头；BODY请求/响应行+头+体
        // 日志显示级别
        HttpLoggingInterceptor.Level level;
        if (BuildConfig.SHOW_LOG) {
            level = HttpLoggingInterceptor.Level.BODY;
        } else {
            level = HttpLoggingInterceptor.Level.NONE;
        }
        // 新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.v("LoggingMessage", message));
        return loggingInterceptor.setLevel(level);
    }

    private enum SingletonEnum {
        // 枚举对象
        INSTANCE;
        private final BaseApi singleton;

        SingletonEnum() {
            singleton = new BaseApi();
        }

        BaseApi getInstance() {
            return singleton;
        }
    }
}
