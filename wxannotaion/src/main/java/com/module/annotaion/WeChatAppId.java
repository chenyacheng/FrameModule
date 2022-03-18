package com.module.annotaion;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 微信支付
 * 编译时注解，项目的applicationId（包名）
 *
 * @author BD
 * @date 2021/12/17
 */
@Retention(RetentionPolicy.CLASS)
public @interface WeChatAppId {
    String value();
}