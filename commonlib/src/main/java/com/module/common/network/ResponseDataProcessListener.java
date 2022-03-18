package com.module.common.network;

/**
 * 处理接口返回的数据监听
 *
 * @author BD
 * @date 2021/12/30 11:10
 */
public interface ResponseDataProcessListener {
    /**
     * 响应成功
     *
     * @param o 返回Object的对象
     */
    void onSuccess(Object o);

    /**
     * 响应失败
     *
     * @param s 返回String的对象
     */
    void onFailure(String s);
}
