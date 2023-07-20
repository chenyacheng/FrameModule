package com.module.common.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.module.arch.utils.LogUtils;
import com.module.common.AppConfig;
import com.module.common.constant.RouterConstant;
import com.module.common.store.UserInfo;

/**
 * 登录拦截器
 *
 * @author BD
 * @date 2022/4/25 15:10
 */
@Interceptor(priority = 1, name = "login")
public class LoginInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (AppConfig.LOGIN_INTERCEPTOR) {
            String path = postcard.getPath();
            // 如果已经登录不拦截
            String a = "loginInfo";
            boolean isLogin = !((String) UserInfo.getInstance().getData(a, "")).isEmpty();
            if (isLogin) {
                callback.onContinue(postcard);
            } else {
                switch (path) {
                    // 不需要登录的直接进入这个页面
                    case RouterConstant.PATH_COMMON_LOGIN_ACTIVITY:
                    case RouterConstant.PATH_APP_ADVERTISING_ACTIVITY:
                    case RouterConstant.PATH_APP_MAIN_FRAGMENT_ACTIVITY:
                    case RouterConstant.PATH_ME_AMOUNT_EDIT_TEXT_ACTIVITY:
                    case RouterConstant.PATH_ME_PHOTOS_ACTIVITY:
                        callback.onContinue(postcard);
                        break;
                    // 需要登录的直接拦截下来
                    default:
                        callback.onInterrupt(null);
                        break;
                }
            }
        } else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
        // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
        LogUtils.info("LoginInterceptor", "路由登录拦截器初始化成功");
    }
}
