package com.module.common.constant;

/**
 * 字符串常量
 *
 * @author BD
 * @date 2021/12/17
 */
public class StringConstant {

    /**
     * 支付成功
     */
    public static final String BROADCAST_PAY_SUCCESS = "broadcast_pay_success";
    /**
     * 支付失败
     */
    public static final String BROADCAST_PAY_ERROR = "broadcast_pay_error";
    /**
     * 用户取消支付
     */
    public static final String BROADCAST_PAY_CANCEL = "broadcast_pay_cancel";

    private StringConstant() {
        throw new IllegalStateException("Utility class");
    }
}
