package com.module.arch.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.module.arch.utils.LogUtils

/**
 * activity基类
 *
 * @author bd
 * @date 2021/09/29
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var viewBinding: VB? = null
    protected val binding get() = viewBinding!!
    private var mActivityProvider: ViewModelProvider? = null
    private var mApplicationProvider: ViewModelProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = getViewBinding()
        setContentView(viewBinding!!.root)
        initViewModel()
        init()
        LogUtils.info("类名", javaClass.simpleName)
    }

    override fun attachBaseContext(newBase: Context) {
        // 设置字体为默认大小，不随系统字体大小改而改变
        val config = Configuration()
        config.setToDefaults()
        config.fontScale = 1.0f
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }

    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }

    protected abstract fun getViewBinding(): VB

    protected abstract fun initViewModel()

    protected fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(this)
        }
        return mActivityProvider!![modelClass]
    }

    protected fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        if (mApplicationProvider == null) {
            mApplicationProvider = ViewModelProvider(this.applicationContext as BaseApplication)
        }
        return mApplicationProvider!![modelClass]
    }

    /**
     * 初始化
     */
    protected abstract fun init()
}