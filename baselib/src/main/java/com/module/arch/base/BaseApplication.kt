package com.module.arch.base

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**
 * 每个组件实现自己的Application，并且继承BaseApplication.
 * 组件模块中实现的Application必须在debug包中的AndroidManifest.xml中注册，否则无法使用.
 * 组件模块的Application需置于java/debug文件夹中，不得放于主代码.
 *
 * @author bd
 * @date 2021/09/29
 */
open class BaseApplication : Application(), ViewModelStoreOwner {

    private lateinit var mAppViewModelStore: ViewModelStore

    override fun onCreate() {
        super.onCreate()
        setApplication(this)
        mAppViewModelStore = ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    companion object {

        private lateinit var application: BaseApplication

        private fun setApplication(application: BaseApplication) {
            Companion.application = application
        }

        @JvmStatic
        fun getApplication(): BaseApplication {
            return application
        }
    }
}