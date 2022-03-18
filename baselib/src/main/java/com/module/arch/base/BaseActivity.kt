package com.module.arch.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

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