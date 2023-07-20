package com.module.common.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * 支付宝支付
 *
 * @author BD
 * @date 2022/1/13 10:40
 */
public class AliPayUtils {

    // 支付宝支付回调
    private static final int SDK_PAY_FLAG = 1;



    public AliPayUtils() {
    }

    public void aliPay(Activity activity, String alipayBean) {
        Runnable payRunnable = () -> {
            // 构造PayTask 对象
            PayTask alipay = new PayTask(activity);
            Map<String, String> result = alipay.payV2("alipayBean.getZpayString()", true);
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressWarnings("unchecked")
    private final Handler mHandler = new Handler(Looper.myLooper(), msg -> {
        if (msg.what == SDK_PAY_FLAG) {
            aliPayRep((Map<String, String>) msg.obj);
        }
        return false;
    });


    private void aliPayRep(Map<String, String> result) {
//        PayResult payResult = new PayResult(result);
//        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//        String resultStatus = payResult.getResultStatus();// 判断resultStatus 为9000则代表支付成功
//        if (TextUtils.equals(resultStatus, "9000")) {
////          Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
//        } else if (TextUtils.equals(resultStatus, "8000")) {
//            Toast.makeText(context, "支付结果确认中", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
//        }
    }
}
