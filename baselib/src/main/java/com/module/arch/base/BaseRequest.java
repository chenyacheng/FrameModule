package com.module.arch.base;

import com.module.arch.callback.ResponseListener;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * 请求网络封装类
 *
 * @author bd
 * @date 2021/10/09
 */
public class BaseRequest {

    private Call<Object> baseResponseCall;
    private volatile boolean disposed;

    public static BaseRequest getInstance() {
        return BaseRequest.SingletonEnum.INSTANCE.getInstance();
    }

    public void executeAsyncCallback(Call<Object> call, ResponseListener responseListener) {
        baseResponseCall = call;
        disposed = false;
        baseResponseCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                if (response.isSuccessful()) {
                    if (null != response.body()) {
                        if (disposed) {
                            baseResponseCall = null;
                            return;
                        }
                        responseListener.onSuccess(response.body());
                    }
                } else {
                    Throwable t = new HttpException(response);
                    responseListener.onFailure(t);
                }
                baseResponseCall = null;
            }

            @Override
            public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {
                responseListener.onFailure(t);
                baseResponseCall = null;
            }
        });
    }

    public void cancel() {
        if (null != baseResponseCall && !baseResponseCall.isCanceled()) {
            baseResponseCall.cancel();
            baseResponseCall = null;
            disposed = true;
        }
    }

    private enum SingletonEnum {
        // 枚举对象
        INSTANCE;
        private final BaseRequest singleton;

        SingletonEnum() {
            singleton = new BaseRequest();
        }

        BaseRequest getInstance() {
            return singleton;
        }
    }
}
