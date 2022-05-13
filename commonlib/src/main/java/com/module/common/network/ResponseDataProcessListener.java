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
     * 响应成功，但没有返回 data 字段，用于 {"code":"200","msg":"暂无数据"} 这种情况下，提示给用户
     *
     * @param s 返回String的对象
     */
    void onSuccessNoData(String s);

    /**
     * 响应失败
     *
     * @param s 返回String的对象
     */
    void onFailure(String s);
}
