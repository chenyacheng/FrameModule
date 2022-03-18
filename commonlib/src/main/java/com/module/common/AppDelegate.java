package com.module.common;

import com.module.arch.base.BaseApplication;
import com.module.arch.utils.LogUtils;
import com.module.common.store.UserInfo;
import com.module.common.utils.AppDebugUtils;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * App的delegate
 *
 * @author bd
 * @date 2021/11/23
 */
public class AppDelegate extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // LogUtils日志设置，false不打印日志，true打印日志
        LogUtils.setLogEnable(AppDebugUtils.isAppDebug());
        // 第三个参数为SDK调试模式开关，调试模式的行为特性如下：
        // 输出详细的Bugly SDK的Log；每一条Crash都会被立即上报；自定义日志将会在Logcat中输出。
        // 建议在测试阶段建议设置成true，发布时设置为false
        CrashReport.initCrashReport(getApplication(), BuildConfig.BUGLY, BuildConfig.BUGLY_LOG);
        // 初始化用户信息
        UserInfo.init(this);
    }
}
