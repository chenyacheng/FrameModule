package com.module.arch.callback;

/**
 * 请求监听成功或者失败
 *
 * @author bd
 * @date 2021/10/09
 */
public interface ResponseListener {

    /**
     * 响应成功
     *
     * @param o 返回Object的对象
     */
    void onSuccess(Object o);

    /**
     * 响应失败
     *
     * @param t 返回Throwable的对象
     */
    void onFailure(Throwable t);
}
