package com.module.app;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.common.AppDelegate;
import com.module.common.utils.AppDebugUtils;

/**
 * @author BD
 */
public class MainApplication extends AppDelegate {

    @Override
    public void onCreate() {
        super.onCreate();
        initRouter();
    }

    private void initRouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (AppDebugUtils.isAppDebug()) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);
    }
}
