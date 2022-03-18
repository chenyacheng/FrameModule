package com.module.arch.base;

import android.text.TextUtils;

import com.module.arch.BuildConfig;
import com.module.arch.utils.NetWorkUtils;
import com.module.arch.utils.RequestEncryptInterceptor;
import com.module.arch.utils.LogUtils;
import com.module.arch.utils.ResponseDecryptInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2L;
    /**
     * 云端响应头拦截器，用来配置缓存策略
     */
    private final Interceptor mRewriteCacheControlInterceptor = chain -> {
        // 拦截器获取请求
        Request request = chain.request();
        // 服务器的缓存策略
        String cacheControl = request.cacheControl().toString();
        // 断网时配置缓存策略
        if (!NetWorkUtils.isNetConnected()) {
            request = request.newBuilder()
                    .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        // 在线缓存
        if (NetWorkUtils.isNetConnected()) {
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    // 应用服务端配置的缓存策略
                    .header("Cache-Control", cacheControl)
                    .build();
        } else {
            // 离线缓存
            /*
             * only-if-cached:(仅为请求标头)
             *　 请求:告知缓存者,我希望内容来自缓存，我并不关心被缓存响应,是否是新鲜的.
             * max-stale:
             *　 请求:意思是,我允许缓存者，发送一个,过期不超过指定秒数的,陈旧的缓存.
             *　 响应:同上.
             * max-age:
             *   请求:强制响应缓存者，根据该值,校验新鲜性.即与自身的Age值,与请求时间做比较.如果超出max-age值,
             *   则强制去服务器端验证.以确保返回一个新鲜的响应.
             *   响应:同上.
             */
            // 需要服务端配合处理缓存请求头，不然会抛出： HTTP 504 Unsatisfiable Request (only-if-cached)
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                    .build();
        }
    };

    public static BaseApi getInstance() {
        return SingletonEnum.INSTANCE.getInstance();
    }

    /**
     * 无超时及缓存策略的Retrofit
     *
     * @param baseUrl 基本url
     * @return Retrofit的实例
     */
    public Retrofit getSimpleRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                // 请求结果转换为基本类型，一般为String
                .addConverterFactory(ScalarsConverterFactory.create())
                // 请求的结果转为实体类
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        // 定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        // OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(getLevel());
        httpClientBuilder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS);
        return httpClientBuilder.build();
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
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> LogUtils.verbose("OkHttp====Message", message));
        return loggingInterceptor.setLevel(level);
    }

    /**
     * 使用OkHttp配置了超时及缓存策略的Retrofit
     *
     * @param baseUrl 基本url
     * @return retrofit
     */
    public Retrofit getRetrofit(String baseUrl) {
        // 缓存
        File cacheFile = new File(BaseApplication.getApplication().getCacheDir(), "cache");
        // 100Mb
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100L);
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
        // 没网的情况下
        builder.addInterceptor(mRewriteCacheControlInterceptor);
        // 有网的情况下
        builder.addNetworkInterceptor(mRewriteCacheControlInterceptor);
        builder.addInterceptor(headerInterceptor);
        if (BuildConfig.ORIGINAL_DATA) {
            builder.addInterceptor(getLevel());
        }
        // 需要参数加密
        if (BuildConfig.ENCRYPT) {
            builder.addInterceptor(new RequestEncryptInterceptor());
            builder.addInterceptor(new ResponseDecryptInterceptor());
        }
        builder.cache(cache);
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
