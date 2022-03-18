package com.module.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.module.common.databinding.ToolbarHeadBinding

/**
 * 顶部状态栏
 *
 * @author bd
 * @date 2021/10/14
 */
class HeadToolBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr) {

    private var binding: ToolbarHeadBinding? = null

    init {
        init(context)
    }

    private fun init(context: Context) {
        binding = ToolbarHeadBinding.inflate(LayoutInflater.from(context), this, true)
    }

    /**
     * 获取toolBar根布局
     */
    fun getToolBarLayout(): FrameLayout {
        return binding!!.frameLayout
    }

    /**
     * 设置导航栏背景颜色
     *
     * @param resId 资源Id
     */
    fun setHeadToolBarBackground(resId: Int) {
        binding!!.frameLayout.setBackgroundResource(resId)
    }

    /**
     * 设置左侧图标
     *
     * @param resId 图标Id
     */
    fun setLeftDrawable(resId: Int) {
        binding!!.flLeft.visibility = View.VISIBLE
        binding!!.ivLeftIcon.setImageResource(resId)
    }

    /**
     * 设置左侧图标点击事件
     *
     * @param onClickListener 点击事件
     */
    fun setLeftDrawableClickListener(onClickListener: OnClickListener?) {
        binding!!.flLeft.setOnClickListener(onClickListener)
    }

    /**
     * 设置中间的title
     *
     * @param title 标题
     */
    fun setMiddleTitle(title: String?) {
        binding!!.tvMiddleTitle.visibility = View.VISIBLE
        binding!!.tvMiddleTitle.text = title
    }

    /**
     * 设置中间title是否显示
     *
     * @param visible 隐藏与显示
     */
    fun setMiddleTitleVisible(visible: Int) {
        binding!!.tvMiddleTitle.visibility = visible
    }

    /**
     * 设置左侧图标
     *
     * @param resId 图标Id
     */
    fun setRightDrawable(resId: Int) {
        binding!!.flRight.visibility = View.VISIBLE
        binding!!.ivRightIcon.setImageResource(resId)
    }

    /**
     * 设置右侧点击事件
     *
     * @param onClickListener 点击事件
     */
    fun setRightClickListener(onClickListener: OnClickListener?) {
        binding!!.flRight.setOnClickListener(onClickListener)
    }
}