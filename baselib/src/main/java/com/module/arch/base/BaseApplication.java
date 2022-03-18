package com.module.arch.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * 每个组件实现自己的Application，并且继承BaseApplication.
 * 组件模块中实现的Application必须在debug包中的AndroidManifest.xml中注册，否则无法使用.
 * 组件模块的Application需置于java/debug文件夹中，不得放于主代码.
 *
 * @author bd
 * @date 2021/09/29
 */
public class BaseApplication extends Application implements ViewModelStoreOwner {

    private static BaseApplication application;
    private ViewModelStore mAppViewModelStore;

    @Override
    public void onCreate() {
        super.onCreate();
        setApplication(this);
        mAppViewModelStore = new ViewModelStore();
    }

    private static void setApplication(BaseApplication application) {
        BaseApplication.application = application;
    }

    public static BaseApplication getApplication() {
        return application;
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }
}
