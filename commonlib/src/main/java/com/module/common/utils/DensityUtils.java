package com.module.common.utils;

import android.content.Context;

/**
 * @author BD
 * @date 2022/5/13 16:30
 */
public class DensityUtils {

    private static volatile DensityUtils instance = null;
    private final Context mContext;

    private DensityUtils(Context context) {
        // 这里变化了，把当前Context指向个应用程序的Context
        this.mContext = context.getApplicationContext();
    }

    public static DensityUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (DensityUtils.class) {
                if (instance == null) {
                    instance = new DensityUtils(context);
                }
            }
        }
        return instance;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dps dp值
     * @return px值
     */
    public int dpToPx(int dps) {
        return Math.round(mContext.getResources().getDisplayMetrics().density * dps);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxs px值
     * @return dp值
     */
    public int pxToDp(int pxs) {
        return Math.round(pxs / mContext.getResources().getDisplayMetrics().density);
    }
}
