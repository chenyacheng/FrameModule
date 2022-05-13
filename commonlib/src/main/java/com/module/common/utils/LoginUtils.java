package com.module.common.utils;

import androidx.fragment.app.FragmentActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.common.interceptor.LoginNavigationCallbackImpl;
import com.module.common.store.UserInfo;
import com.module.common.ui.TipsDialog;

/**
 * 登录判断，登录弹框
 *
 * @author BD
 * @date 2022/5/10 9:56
 */
public class LoginUtils {

    private LoginUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * true -> 已登录
     * false -> 未登录
     *
     * @return true
     */
    public static boolean isLogin() {
        String a = "loginInfo";
        return  !((String) UserInfo.getInstance().getData(a, "")).isEmpty();
    }

    public static void loginDialog(FragmentActivity activity, String path) {
        TipsDialog tipsDialog = new TipsDialog();
        tipsDialog.setLoginListener(() -> ARouter.getInstance().build(path).navigation(activity, new LoginNavigationCallbackImpl()));
        tipsDialog.show(activity);
    }
}
