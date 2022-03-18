package com.module.arch.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

import com.module.arch.base.BaseApplication;

/**
 * 网络连接工具类
 *
 * @author bd
 * @date 2021/10/08
 */
public class NetWorkUtils {

    private NetWorkUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 网络是否连接判断工具
     */
    public static boolean isNetConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) BaseApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络属性
        assert connectivity != null;
        NetworkCapabilities networkCapabilities = connectivity.getNetworkCapabilities(connectivity.getActiveNetwork());
        if (networkCapabilities != null) {
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        }
        return false;
    }
}