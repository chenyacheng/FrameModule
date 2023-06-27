package com.module.home.ui.activity

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import com.alibaba.android.arouter.facade.annotation.Route
import com.chenyacheng.snackbar.SnackBarBuilder
import com.module.arch.base.BaseActivity
import com.module.common.R
import com.module.common.constant.RouterConstant
import com.module.common.widget.HeadToolBar
import com.module.home.databinding.HomeActivityButtonAndLayoutBinding

/**
 * @author vya
 * @date 2023/4/29 上午8:42
 */
@Route(path = RouterConstant.PATH_HOME_BUTTON_AND_LAYOUT_ACTIVITY)
class ButtonAndLayoutActivity : BaseActivity<HomeActivityButtonAndLayoutBinding>() {

    override fun getViewBinding(): HomeActivityButtonAndLayoutBinding {
        return HomeActivityButtonAndLayoutBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
    }

    override fun init() {
        toolBar()
        binding.btn1.setChangeAlphaWhenPress(false)
        binding.btn1.setOnClickListener {
            SnackBarBuilder.getInstance().builderShort(it, "Button 按钮，没有圆角，没有alpha")
        }

        binding.btn2.setGradientBgForRoundButton(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(Color.parseColor("#990033"), Color.parseColor("#009966")))
        binding.btn2.setOnClickListener {
            SnackBarBuilder.getInstance().builderShort(it, "Button 按钮，有圆角，有alpha：默认0.5")
        }

        binding.btn22.setOnClickListener {
            SnackBarBuilder.getInstance().builderShort(it, "Button 按钮，有圆角，有alpha：0.7")
        }

        binding.btn3.setGradientBgForAlphaButton(GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(Color.parseColor("#00ff00"), Color.parseColor("#ff0000")))
        binding.btn3.setOnClickListener {
            SnackBarBuilder.getInstance().builderShort(it, "Button 按钮，没有圆角，有alpha")
        }

        binding.btn4.setChangeAlphaWhenDisable(true)

    }

    private fun toolBar() {
        val headToolBar: HeadToolBar = binding.toolbarHead
        headToolBar.setHeadToolBarBackground(R.color.common_ff009944)
        headToolBar.setMiddleTitle("Button，布局")
        headToolBar.setLeftDrawable(R.drawable.toolbar_back)
        headToolBar.setLeftDrawableClickListener { finish() }
    }
}