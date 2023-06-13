package com.module.me.ui.activity.edittext

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.chenyacheng.snackbar.SnackBarBuilder
import com.module.arch.base.BaseActivity
import com.module.common.R
import com.module.common.constant.RouterConstant
import com.module.common.utils.BigDecimalUtils
import com.module.common.utils.DecimalFormatUtils
import com.module.common.widget.HeadToolBar
import com.module.me.databinding.MeActivityAmountEditTextBinding
import java.util.*

/**
 * @author BD
 * @date 2022/6/23 10:51
 */
@Route(path = RouterConstant.PATH_ME_AMOUNT_EDIT_TEXT_ACTIVITY)
class AmountEditTextActivity : BaseActivity<MeActivityAmountEditTextBinding>() {

    private var donationAmount = ""

    override fun getViewBinding(): MeActivityAmountEditTextBinding {
        return MeActivityAmountEditTextBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
    }

    override fun init() {
        toolBar()
        val symbol = Currency.getInstance(Locale.CHINA).symbol
        binding.symbol.text = symbol

        binding.etAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                var a = charSequence.toString()
                if (TextUtils.isEmpty(a)) {
                    noClickable()
                } else {
                    val dot = "."
                    // 当前字符串是 . 则自动加上 0.
                    if (dot == a) {
                        a = "0."
                        binding.etAmount.setText(a)
                        binding.etAmount.setSelection(a.length)
                    }
                    // 判断小数点后只能输入两位
                    if (a.contains(dot)) {
                        val two = 2
                        if (a.length - 1 - a.indexOf(dot) > two) {
                            a = a.subSequence(0, a.indexOf(dot) + 3).toString()
                            binding.etAmount.setText(a)
                            binding.etAmount.setSelection(a.length)
                        }
                    }
                    // 字符串都是 0.
                    val regex = Regex("^[0.]+$")
                    if (regex.matches(a)) {
                        noClickable()
                    } else {
                        clickable()
                        // 先乘以 100
                        val b = BigDecimalUtils.multiply(a.toDouble(), 100.0)
                        // 不保留小数点
                        donationAmount = DecimalFormatUtils.numberFormat1(b)
                    }
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        binding.btn1.setOnClickListener {
            SnackBarBuilder.getInstance().builderLong(it, "已充值")
        }
        noClickable()
    }

    private fun noClickable() {
        binding.btn1.isClickable = false
        binding.btn1.alpha = 0.5f
    }

    private fun clickable() {
        binding.btn1.isClickable = true
        binding.btn1.alpha = 1.0f
    }

    private fun toolBar() {
        val headToolBar: HeadToolBar = binding.toolbarHead
        headToolBar.setHeadToolBarBackground(R.color.common_ff009944)
        headToolBar.setMiddleTitle("EditText 金额输入控制位数")
        headToolBar.setLeftDrawable(R.drawable.toolbar_back)
        headToolBar.setLeftDrawableClickListener { finish() }
    }
}