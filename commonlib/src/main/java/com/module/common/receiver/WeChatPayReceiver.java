package com.module.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.module.common.constant.StringConstant;

/**
 * 微信支付回调
 *
 * @author BD
 * @date 2021/12/27 13:37
 */
public class WeChatPayReceiver extends BroadcastReceiver {

    private WeCatPayListener weCatPayListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null != weCatPayListener) {
            if (StringConstant.BROADCAST_PAY_SUCCESS.equals(intent.getAction())) {
                weCatPayListener.paySuccess();
            } else if (StringConstant.BROADCAST_PAY_ERROR.equals(intent.getAction())) {
                weCatPayListener.payError();
            } else if (StringConstant.BROADCAST_PAY_CANCEL.equals(intent.getAction())) {
                weCatPayListener.payCancel();
            }
        }
        // 注销广播
        context.unregisterReceiver(this);
    }

    public void setWeCatPayListener(WeCatPayListener weCatPayListener) {
        this.weCatPayListener = weCatPayListener;
    }

    public interface WeCatPayListener {
        /**
         * 支付成功
         */
        void paySuccess();

        /**
         * 支付取消
         */
        void payCancel();

        /**
         * 支付错误
         */
        void payError();
    }
}
