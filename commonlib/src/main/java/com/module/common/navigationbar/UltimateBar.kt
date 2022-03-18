package com.module.common.navigationbar

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.View

/**
 * 状态栏和导航栏
 *
 * @author bd
 * @date 2021/11/16
 */
class UltimateBar private constructor(private val activity: Activity) {

    // 状态栏灰色模式
    private var statusDark: Boolean = false
    // 状态栏背景
    private var statusDrawable: Drawable? = null
    // Android 6.0 以下状态栏灰色模式时，状态栏背景
    private var statusDrawable2: Drawable? = statusDrawable
    // 是否应用到导航栏
    private var applyNavigation: Boolean = false
    // 导航栏灰色模式
    private var navigationDark: Boolean = false
    // 导航栏背景
    private var navigationDrawable: Drawable? = null
    // Android 8.0 以下导航栏灰色模式时，导航栏背景
    private var navigationDrawable2: Drawable? = null

    companion object {
        fun with(activity: Activity): Builder {
            return Builder(activity)
        }
    }

    // 设置背景
    fun drawableBar() {
        UltimateBarUtils.setBarDrawable(
            activity, statusDark, statusDrawable, statusDrawable2,
            applyNavigation, navigationDark, navigationDrawable, navigationDrawable2
        )
    }

    // 半透明
    fun transparentBar() {
        UltimateBarUtils.setBarTransparent(
            activity, statusDark, statusDrawable, statusDrawable2,
            applyNavigation, navigationDark, navigationDrawable, navigationDrawable2
        )
    }

    // 沉浸
    fun immersionBar() {
        UltimateBarUtils.setBarImmersion(
            activity,
            statusDark,
            statusDrawable2,
            applyNavigation,
            navigationDark,
            navigationDrawable2
        )
    }

    // 隐藏
    fun hideBar() {
        UltimateBarUtils.setBarHide(activity, applyNavigation)
    }

    /**
     * 针对 DrawerLayout 设置背景，content：DrawerLayout的主布局View，drawer：DrawerLayout的抽屉布局View
     */
    fun drawableBarDrawer(drawerLayout: androidx.drawerlayout.widget.DrawerLayout, content: View, drawer: View) {
        UltimateBarUtils.setBarDrawableDrawer(
            activity,
            drawerLayout,
            content,
            drawer,
            statusDark,
            statusDrawable,
            statusDrawable2,
            applyNavigation,
            navigationDark,
            navigationDrawable,
            navigationDrawable2
        )
    }

    class Builder internal constructor(private val activity: Activity) {

        private var statusDark: Boolean = false
        private var statusDrawable: Drawable? = null
        private var statusDrawable2: Drawable? = statusDrawable
        private var statusDrawable2HasSet: Boolean = false
        private var applyNavigation: Boolean = false
        private var navigationDark: Boolean = false
        private var navigationDrawable: Drawable? = null
        private var navigationDrawable2: Drawable? = navigationDrawable
        private var navigationDrawable2HasSet: Boolean = false

        /**
         * 状态栏灰色模式(Android 6.0+)，默认 false
         */
        fun statusDark(statusDark: Boolean): Builder {
            this.statusDark = statusDark
            return this
        }

        /**
         * 状态栏背景，默认 null
         */
        fun statusDrawable(statusDrawable: Drawable?): Builder {
            this.statusDrawable = statusDrawable
            if (!statusDrawable2HasSet) this.statusDrawable2 = statusDrawable
            return this
        }

        /**
         * Android 6.0 以下状态栏灰色模式时状态栏颜色
         */
        fun statusDrawable2(statusDrawable2: Drawable?): Builder {
            this.statusDrawable2 = statusDrawable2
            statusDrawable2HasSet = true
            return this
        }

        /**
         * 应用到导航栏，默认 flase
         */
        fun applyNavigation(applyNavigation: Boolean): Builder {
            this.applyNavigation = applyNavigation
            return this
        }

        /**
         * 导航栏灰色模式(Android 8.0+)，默认 false
         */
        fun navigationDark(navigationDark: Boolean): Builder {
            this.navigationDark = navigationDark
            return this
        }

        /**
         * 导航栏背景，默认 null
         */
        fun navigationDrawable(navigationDrawable: Drawable?): Builder {
            this.navigationDrawable = navigationDrawable
            if (!navigationDrawable2HasSet) this.navigationDrawable2 = navigationDrawable
            return this
        }

        /**
         * Android 8.0 以下导航栏灰色模式时导航栏颜色
         */
        fun navigationDrawable2(navigationDrawable2: Drawable?): Builder {
            this.navigationDrawable2 = navigationDrawable2
            navigationDrawable2HasSet = true
            return this
        }

        fun create(): UltimateBar {
            return UltimateBar(activity).apply {
                statusDark = this@Builder.statusDark
                statusDrawable = this@Builder.statusDrawable
                statusDrawable2 = this@Builder.statusDrawable2
                applyNavigation = this@Builder.applyNavigation
                navigationDark = this@Builder.navigationDark
                navigationDrawable = this@Builder.navigationDrawable
                navigationDrawable2 = this@Builder.navigationDrawable2
            }
        }
    }
}
