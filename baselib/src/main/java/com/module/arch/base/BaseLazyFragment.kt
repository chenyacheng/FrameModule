package com.module.arch.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * fragment基类
 *
 * @author bd
 * @date 2021/09/29
 */
abstract class BaseLazyFragment<VB : ViewBinding> : Fragment() {

    protected lateinit var context: AppCompatActivity
    private var viewBinding: VB? = null
    protected val binding get() = viewBinding!!
    private var mFragmentProvider: ViewModelProvider? = null
    private var mApplicationProvider: ViewModelProvider? = null
    private var firstVisible = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = getViewBinding(inflater, container)
        return viewBinding!!.root
    }

    override fun onResume() {
        super.onResume()
        // 可见又是第一次
        if (firstVisible) {
            lazyLoadInit()
            // 改变首次可见的状态
            firstVisible = false
        }
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    protected abstract fun initViewModel()

    protected abstract fun lazyLoadInit()

    protected open fun <T : ViewModel> getFragmentScopeViewModel(@NonNull modelClass: Class<T>): T {
        if (mFragmentProvider == null) {
            mFragmentProvider = ViewModelProvider(this)
        }
        return mFragmentProvider!![modelClass]
    }

    protected fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        if (mApplicationProvider == null) {
            mApplicationProvider = ViewModelProvider(context.applicationContext as BaseApplication)
        }
        return mApplicationProvider!![modelClass]
    }

    override fun onDestroyView() {
        viewBinding = null
        firstVisible = false
        super.onDestroyView()
    }
}