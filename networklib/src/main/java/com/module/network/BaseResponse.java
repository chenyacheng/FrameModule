package com.module.network;

/**
 * 服务端返回的数据格式
 *
 * @author bd
 * @date 2021/10/08
 */
public class BaseResponse {

    private String code;
    private String msg;
    private Object data;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
