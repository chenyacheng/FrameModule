package com.module.common.navigationbar

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout

/**
 * 状态栏和导航栏的工具包
 *
 * @author bd
 * @date 2021/11/16
 */
object UltimateBarUtils {

    private const val TAG_STATUS_BAR = "status_bar"
    private const val TAG_NAVIGATION_BAR = "navigation_bar"

    /**
     * 给状态栏和导航栏设置背景
     * @param statusDark 状态栏灰色模式
     * @param statusDrawable 状态栏背景
     * @param statusDrawable2 Android 6.0 以下状态栏灰色模式时状态栏背景
     * @param applyNavigation 是否应用到导航栏
     * @param navigationDark 导航栏灰色模式
     * @param navigationDrawable 导航栏背景
     * @param navigationDrawable2 Android 8.0 以下导航栏灰色模式时导航栏背景
     */
    internal fun setBarDrawable(activity: Activity,
                                statusDark: Boolean = false,
                                statusDrawable: Int,
                                statusDrawable2: Int = statusDrawable,
                                applyNavigation: Boolean = false,
                                navigationDark: Boolean = false,
                                navigationDrawable: Int,
                                navigationDrawable2: Int = navigationDrawable) {
        setBar(activity, statusDark, statusDrawable, statusDrawable2, applyNavigation,
            navigationDark, navigationDrawable, navigationDrawable2, true, true)
    }

    /**
     * 状态栏和导航栏沉浸
     * @param statusDark 状态栏灰色模式
     * @param statusDrawable2 Android 6.0 以下状态栏灰色模式时状态栏颜色
     * @param applyNavigation 是否应用到导航栏
     * @param navigationDark 导航栏灰色模式
     * @param navigationDrawable2 Android 8.0 以下导航栏灰色模式时导航栏背景
     */
    internal fun setBarImmersion(activity: Activity,
                                 statusDark: Boolean = false,
                                 statusDrawable2: Int,
                                 applyNavigation: Boolean = false,
                                 navigationDark: Boolean = false,
                                 navigationDrawable2: Int) {
        setBarTransparent(activity, statusDark, statusDrawable2, statusDrawable2,
            applyNavigation, navigationDark, navigationDrawable2, navigationDrawable2)
    }

    /**
     * 半透明状态栏和导航栏
     * @param statusDark 状态栏灰色模式
     * @param statusDrawable 状态栏背景
     * @param statusDrawable2 Android 6.0 以下状态栏灰色模式时状态栏颜色
     * @param applyNavigation 是否应用到导航栏
     * @param navigationDark 导航栏灰色模式
     * @param navigationDrawable 导航栏背景
     * @param navigationDrawable2 Android 8.0 以下导航栏灰色模式时导航栏背景
     */
    internal fun setBarTransparent(activity: Activity,
                                   statusDark: Boolean = false,
                                   statusDrawable: Int,
                                   statusDrawable2: Int = statusDrawable,
                                   applyNavigation: Boolean = false,
                                   navigationDark: Boolean = false,
                                   navigationDrawable: Int,
                                   navigationDrawable2: Int = navigationDrawable) {
        setBar(activity, statusDark, statusDrawable, statusDrawable2, applyNavigation, navigationDark,
            navigationDrawable, navigationDrawable2, false, true)
    }

    /**
     * 隐藏状态栏和导航栏
     * @param applyNavigation 是否应用到导航栏
     */
    internal fun setBarHide(activity: Activity, applyNavigation: Boolean = false) {
        val decorView = activity.window.decorView
        var option = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        if (applyNavigation) {
            option = (option or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        }
        decorView.systemUiVisibility = option
    }

    /**
     * 针对 DrawerLayout 设置状态栏和导航栏背景
     * @param content DrawerLayout 中的主布局 View
     * @param drawer DrawerLayout 中的抽屉布局 View
     * @param statusDark 状态栏灰色模式
     * @param statusDrawable 状态栏背景
     * @param statusDrawable2 Android 6.0 以下状态栏灰色模式时状态栏颜色
     * @param applyNavigation 是否应用到导航栏
     * @param navigationDark 导航栏灰色模式
     * @param navigationDrawable 导航栏背景
     * @param navigationDrawable2 Android 8.0 以下导航栏灰色模式时导航栏背景
     */
    internal fun setBarDrawableDrawer(activity: Activity,
                                      drawerLayout: androidx.drawerlayout.widget.DrawerLayout,
                                      content: View,
                                      drawer: View,
                                      statusDark: Boolean = false,
                                      statusDrawable: Int,
                                      statusDrawable2: Int = statusDrawable,
                                      applyNavigation: Boolean = false,
                                      navigationDark: Boolean = false,
                                      navigationDrawable: Int,
                                      navigationDrawable2: Int = navigationDrawable) {
        setRootView(drawerLayout, false)
        setRootView(content, true)
        setRootView(drawer, false)
        setBar(activity, statusDark, statusDrawable, statusDrawable2, applyNavigation,
            navigationDark, navigationDrawable, navigationDrawable2, false)
    }

    /**
     * 核心方法
     * @param statusDark 状态栏灰色模式
     * @param statusDrawable 状态栏背景
     * @param statusDrawable2 Android 6.0 以下状态栏灰色模式时状态栏颜色
     * @param applyNavigation 是否应用到导航栏
     * @param navigationDark 导航栏灰色模式
     * @param navigationDrawable 导航栏背景
     * @param navigationDrawable2 Android 8.0 以下导航栏灰色模式时导航栏颜色
     * @param fitsSystemWindows 跟布局是否设置 fitsSystemWindows
     * @param lastIndex 状态栏和导航栏 view 是否加在 decorView 的最后位置
     */
    private fun setBar(activity: Activity,
                       statusDark: Boolean = false,
                       statusDrawable: Int,
                       statusDrawable2: Int = statusDrawable,
                       applyNavigation: Boolean = false,
                       navigationDark: Boolean = false,
                       navigationDrawable: Int,
                       navigationDrawable2: Int,
                       fitsSystemWindows: Boolean = false,
                       lastIndex: Boolean = false) {
        setRootView(activity, fitsSystemWindows)
        var finalStatusDrawable: Int = statusDrawable
        var finalNavigationDrawable: Int = navigationDrawable
        val window = activity.window
        val decorView = window.decorView as ViewGroup
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                var option = when {
                    statusDark -> {
                        when {
                            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ->
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                            else -> {
                                finalStatusDrawable = statusDrawable2
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            }
                        }
                    }
                    else -> View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                }
                window.statusBarColor = Color.TRANSPARENT
                setStatusBarView(activity, decorView, lastIndex, finalStatusDrawable)
                when {
                    applyNavigation && navigationBarExist(activity) -> {
                        option = option or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        when {
                            navigationDark -> {
                                when {
                                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ->
                                        option = option or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                                    else -> finalNavigationDrawable = statusDrawable2
                                }
                            }
                        }
                        window.navigationBarColor = Color.TRANSPARENT
                        setNavigationBarView(activity, decorView, lastIndex, finalNavigationDrawable)
                    }
                    else -> {
                        window.navigationBarColor = Color.BLACK
                        removeNavigationBarView(decorView)
                    }
                }
                decorView.systemUiVisibility = option
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                val winParams = window.attributes
                if (winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS == 0) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                }
                when {
                    statusDark -> finalStatusDrawable = statusDrawable2
                }
                setStatusBarView(activity, decorView, lastIndex, finalStatusDrawable)
                when {
                    applyNavigation && navigationBarExist(activity) -> {
                        if (winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION == 0) {
                            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                        }
                        when {
                            navigationDark -> finalNavigationDrawable = navigationDrawable2
                        }
                        setNavigationBarView(activity, decorView, lastIndex, finalNavigationDrawable)
                    }
                    else -> {
                        if (winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION != 0) {
                            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                        }
                        removeNavigationBarView(decorView)
                    }
                }
            }
        }
    }

    // 导航栏是否存在
    private fun navigationBarExist(activity: Activity): Boolean {
        val windowManager = activity.windowManager
        val d = windowManager.defaultDisplay

        val realDisplayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            d.getRealMetrics(realDisplayMetrics)
        }

        val realHeight = realDisplayMetrics.heightPixels
        val realWidth = realDisplayMetrics.widthPixels

        val displayMetrics = DisplayMetrics()
        d.getMetrics(displayMetrics)

        val displayHeight = displayMetrics.heightPixels
        val displayWidth = displayMetrics.widthPixels

        return realWidth - displayWidth > 0 || realHeight - displayHeight > 0
    }

    // 状态栏高度
    private fun getStatusBarHeight(context: Context): Int {
        return getBarHeight(context, "status_bar_height")
    }

    // 导航栏高度
    private fun getNavigationBarHeight(context: Context): Int {
        return getBarHeight(context, "navigation_bar_height")
    }

    private fun getBarHeight(context: Context, name: String): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier(name, "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    private fun setStatusBarView(context: Context, decorView: ViewGroup, lastIndex: Boolean, background: Int) {
        var statusBarView: View? = decorView.findViewWithTag(TAG_STATUS_BAR)
        val navigationBarView: View? = decorView.findViewWithTag(TAG_NAVIGATION_BAR)
        if (statusBarView == null) {
            statusBarView = createStatusBarView(context, background)
            statusBarView.tag = TAG_STATUS_BAR
            if (lastIndex) decorView.addView(statusBarView)
            else {
                val index = when (decorView.getChildAt(0)) {
                    navigationBarView -> 1
                    else -> 0
                }
                decorView.addView(statusBarView, index)
            }
        } else {
            val index = decorView.indexOfChild(statusBarView)
            val count = decorView.childCount
            if (lastIndex) {
                when (index) {
                    // 最后一个
                    count - 1 -> {
                        statusBarView.setBackgroundResource(background)
                    }
                    // 倒数第二个
                    count - 2 -> {
                        when (decorView.getChildAt(count - 1)) {
                            navigationBarView -> statusBarView.setBackgroundResource(background)
                            else -> {
                                decorView.removeView(statusBarView)
                                setStatusBarView(context, decorView, true, background)
                            }
                        }
                    }
                    else -> {
                        decorView.removeView(statusBarView)
                        setStatusBarView(context, decorView, true, background)
                    }
                }
            } else {
                when (index) {
                    // 第一个
                    0 -> {
                        statusBarView.setBackgroundResource(background)
                    }
                    // 第二个
                    1 -> {
                        when (decorView.getChildAt(0)) {
                            navigationBarView -> statusBarView.setBackgroundResource(background)
                            else -> {
                                decorView.removeView(statusBarView)
                                setStatusBarView(context, decorView, false, background)
                            }
                        }
                    }
                    else -> {
                        decorView.removeView(statusBarView)
                        setStatusBarView(context, decorView, false, background)
                    }
                }
            }
        }
    }

    private fun setNavigationBarView(context: Context, decorView: ViewGroup, lastIndex: Boolean, background: Int) {
        var navigationBarView: View? = decorView.findViewWithTag(TAG_NAVIGATION_BAR)
        val statusBarView: View? = decorView.findViewWithTag(TAG_STATUS_BAR)
        if (navigationBarView == null) {
            navigationBarView = createNavigationBarView(context, background)
            navigationBarView.tag = TAG_NAVIGATION_BAR
            if (lastIndex) decorView.addView(navigationBarView)
            else {
                val index = when (decorView.getChildAt(0)) {
                    statusBarView -> 1
                    else -> 0
                }
                decorView.addView(navigationBarView, index)
            }
        } else {
            val index = decorView.indexOfChild(navigationBarView)
            val count = decorView.childCount
            if (lastIndex) {
                when (index) {
                    // 最后一个
                    count - 1 -> {
                        navigationBarView.setBackgroundResource(background)
                    }
                    // 倒数第二个
                    count - 2 -> {
                        when (decorView.getChildAt(count - 1)) {
                            statusBarView -> navigationBarView.setBackgroundResource(background)
                            else -> {
                                decorView.removeView(navigationBarView)
                                setNavigationBarView(context, decorView, true, background)
                            }
                        }
                    }
                    else -> {
                        decorView.removeView(navigationBarView)
                        setNavigationBarView(context, decorView, true, background)
                    }
                }
            } else {
                when (index) {
                    // 第一个
                    0 -> {
                        navigationBarView.setBackgroundResource(background)
                    }
                    // 第二个
                    1 -> {
                        when (decorView.getChildAt(0)) {
                            statusBarView -> navigationBarView.setBackgroundResource(background)
                            else -> {
                                decorView.removeView(navigationBarView)
                                setNavigationBarView(context, decorView, false, background)
                            }
                        }
                    }
                    else -> {
                        decorView.removeView(navigationBarView)
                        setNavigationBarView(context, decorView, false, background)
                    }
                }
            }
        }
    }

    private fun removeNavigationBarView(decorView: ViewGroup) {
        val navigationBarView: View? = decorView.findViewWithTag(TAG_NAVIGATION_BAR)
        if (navigationBarView != null) decorView.removeView(navigationBarView)
    }

    private fun createStatusBarView(context: Context, background: Int): View {
        val statusBarView = View(context)
        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight(context))
        params.gravity = Gravity.TOP
        statusBarView.layoutParams = params
        statusBarView.setBackgroundResource(background)
        return statusBarView
    }

    private fun createNavigationBarView(context: Context, background: Int): View {
        val navBarView = View(context)
        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getNavigationBarHeight(context))
        params.gravity = Gravity.BOTTOM
        navBarView.layoutParams = params
        navBarView.setBackgroundResource(background)
        return navBarView
    }

    private fun setRootView(activity: Activity, fit: Boolean) {
        val content: FrameLayout = activity.findViewById(android.R.id.content)
        val root: View? = content.getChildAt(0)
        setRootView(root, fit)
    }

    private fun setRootView(view: View?, fit: Boolean) {
        if (view is ViewGroup) {
            view.fitsSystemWindows = fit
            view.clipToPadding = fit
        }
    }
}
