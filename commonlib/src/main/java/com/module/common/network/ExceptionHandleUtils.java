package com.module.common.network;

import com.tencent.bugly.crashreport.CrashReport;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okio.BufferedSource;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * 失败处理类：1.异常2.错误
 * 1.异常：服务端异常，解析数据出错，网络异常等等
 * 2.错误：请求服务端成功但返回的状态码不是约定的（200）
 *
 * @author bd
 * @date 2021/10/08
 */
public class ExceptionHandleUtils {

    private final String message;

    private ExceptionHandleUtils(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static ExceptionHandleUtils handleException(Throwable e) {
        CrashReport.postCatchedException(e);
        if (e instanceof UnknownHostException) {
            return new ExceptionHandleUtils("当前连接的域名无效或过期");
        } else if (e instanceof ConnectException) {
            return new ExceptionHandleUtils("连接失败");
        } else if (e instanceof SocketTimeoutException) {
            return new ExceptionHandleUtils("请求超时");
        } else if (e instanceof HttpException) {
            Response<?> response = ((HttpException) e).response();
            if (null != response) {
                if (null == response.errorBody()) {
                    return new ExceptionHandleUtils("服务暂不可用");
                }
                BufferedSource bufferedSource = response.errorBody().source();
                return new ExceptionHandleUtils(bufferedSource.toString());
            }
            return new ExceptionHandleUtils("服务暂不可用");
        } else if (e instanceof SocketException) {
            return new ExceptionHandleUtils("取消网络请求");
        } else {
            return new ExceptionHandleUtils("服务器开小差了,稍后再试吧");
        }
    }
}
