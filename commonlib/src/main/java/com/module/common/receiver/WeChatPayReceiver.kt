package com.module.common.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.module.common.constant.StringConstant

/**
 * 微信支付回调
 *
 * @author BD
 * @date 2021/12/27 13:37
 */
class WeChatPayReceiver : BroadcastReceiver() {

    private lateinit var weCatPayListener: WeCatPayListener
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            StringConstant.BROADCAST_PAY_SUCCESS -> weCatPayListener.paySuccess()
            StringConstant.BROADCAST_PAY_ERROR -> weCatPayListener.payError()
            StringConstant.BROADCAST_PAY_CANCEL -> weCatPayListener.payCancel()
        }
        // 注销广播
        context.unregisterReceiver(this)
    }

    fun setWeCatPayListener(weCatPayListener: WeCatPayListener) {
        this.weCatPayListener = weCatPayListener
    }

    interface WeCatPayListener {
        /**
         * 支付成功
         */
        fun paySuccess()

        /**
         * 支付取消
         */
        fun payCancel()

        /**
         * 支付错误
         */
        fun payError()
    }
}