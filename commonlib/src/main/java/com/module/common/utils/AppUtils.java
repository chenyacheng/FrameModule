package com.module.common.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;

import com.module.arch.base.BaseApplication;

/**
 * App相关工具类
 *
 * @author bd
 * @date 2021/11/23
 */
public class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取手机品牌
     *
     * @return 手机品牌
     */
    public static String getPhoneBrand() {
        return Build.BRAND == null ? "" : Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getPhoneModel() {
        return Build.MODEL == null ? "" : Build.MODEL;
    }

    /**
     * 获取手机Android版本
     *
     * @return Android版本
     */
    public static String getDeviceAndroidVersion() {
        return Build.VERSION.RELEASE == null ? "" : Build.VERSION.RELEASE;
    }

    /**
     * 获取App版本名称
     *
     * @param context 上下文
     * @return 版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            return packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 0代表相等，1代表currentVersion大于serverVersion，-1代表currentVersion小于serverVersion
     *
     * @param currentVersion App当前版本号
     * @param serverVersion  获取服务器版本号
     * @return 0或1或-1
     */
    public static int compareVersion(String currentVersion, String serverVersion) {
        if (currentVersion.equals(serverVersion)) {
            return 0;
        }
        String[] currentVersionArray = currentVersion.split("\\.");
        String[] serverVersionArray = serverVersion.split("\\.");
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(currentVersionArray.length, serverVersionArray.length);
        int diff = 0;
        // 循环判断每位的大小
        while (index < minLen && (diff = Integer.parseInt(currentVersionArray[index]) - Integer.parseInt(serverVersionArray[index])) == 0) {
            index++;
        }
        // 如果位数不一致，比较多余位数
        if (diff == 0) {
            for (int i = index; i < currentVersionArray.length; ++i) {
                if (Integer.parseInt(currentVersionArray[i]) > 0) {
                    return 1;
                }
            }
            for (int i = index; i < serverVersionArray.length; ++i) {
                if (Integer.parseInt(serverVersionArray[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

    /**
     * 根据包名判断某个app是否安装
     *
     * @param packageName 应用包名
     * @return 已安装：true，未安装：false
     */
    public static boolean isInstalledApp(String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = BaseApplication.getApplication().getPackageManager().getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    /**
     * 检测支付宝客户端是否安装
     *
     * @param context context
     * @return 已安装：true，未安装：false
     */
    public static boolean checkAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }
}
