package com.module.common.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chenyacheng.snackbar.SnackBarBuilder;
import com.module.arch.base.BaseActivity;
import com.module.arch.base.BaseApplication;
import com.module.arch.utils.LogUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 继承了Activity，实现Android6.0的运行时权限检测
 * 需要进行运行时权限检测的Activity可以继承这个类
 *
 * @param <VB> ViewBinding
 * @author bd
 * @date 2021/12/19
 */

public abstract class AbstractCheckPermissionsActivity<VB extends ViewBinding> extends BaseActivity<VB> {

    /**
     * 如果设置了 target > 28，需要增加这个权限，否则不会弹出"始终允许"这个选择框
     */
    private static final String ACCESS_BACKGROUND_LOCATION = "android.permission.ACCESS_BACKGROUND_LOCATION";
    private static final int PERMISSION_REQUEST_CODE = 0;
    private static final int A = 23;
    /**
     * 是否需要检测后台定位权限，设置为 true 时，如果用户没有给予后台定位权限会弹窗提示
     */
    private static final boolean NEED_CHECK_BACK_LOCATION = false;
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private LocationCallBack mLocationCallBack;
    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private final AMapLocationListener locationListener = location -> {
        if (null != location) {
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.getErrorCode() == 0) {
                if (null != mLocationCallBack) {
                    mLocationCallBack.onLocationCallBack(location.getLongitude(), location.getLatitude());
                }
            } else {
                // 定位失败
                SnackBarBuilder.getInstance().builderLong(AbstractCheckPermissionsActivity.this, "请在设置中打开定位服务开关");
                String a = "错误码:" + location.getErrorCode() + "==错误信息:" + location.getErrorInfo() + "==错误描述:" + location.getLocationDetail();
                Exception exception = new Exception(a);
                CrashReport.postCatchedException(exception);
                LogUtils.verbose("高德定位", "错误码:" + location.getErrorCode());
                LogUtils.verbose("高德定位", "错误信息:" + location.getErrorInfo());
                LogUtils.verbose("高德定位", "错误描述:" + location.getLocationDetail());
            }
        } else {
            LogUtils.verbose("高德定位", "定位失败，loc is null");
        }
        stopLocation();
    };

    @Override
    public void onResume() {
        super.onResume();
        final int b = 28;
        if (Build.VERSION.SDK_INT > b && AbstractCheckPermissionsActivity.this.getApplicationInfo().targetSdkVersion > b) {
            needPermissions = new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    ACCESS_BACKGROUND_LOCATION
            };
        }
    }

    public void initLocation() {
        // 从定位5.6.0版本起对旧版本SDK不兼容，请务必确保调用SDK任何接口前先调用更新隐私合规updatePrivacyShow、updatePrivacyAgree两个接口，否则可能导致编译不通过等异常情况
        AMapLocationClient.updatePrivacyShow(this, true, true);
        AMapLocationClient.updatePrivacyAgree(this, true);
        //初始化client
        try {
            locationClient = new AMapLocationClient(BaseApplication.getApplication());
            locationOption = getDefaultOption();
            if (null != locationClient) {
                //设置定位参数
                locationClient.setLocationOption(locationOption);
                // 设置定位监听
                locationClient.setLocationListener(locationListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 默认的定位参数
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    public void setLocationCallBack(LocationCallBack locationCallBack) {
        this.mLocationCallBack = locationCallBack;
    }

    /**
     * 停止定位
     */
    private void stopLocation() {
        try {
            // 停止定位
            locationClient.stopLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNeedCheck() {
        if (AbstractCheckPermissionsActivity.this.getApplicationInfo().targetSdkVersion >= A && isNeedCheck) {
            checkPermissions(needPermissions);
        }
    }

    private void checkPermissions(String... permissions) {
        if (AbstractCheckPermissionsActivity.this.getApplicationInfo().targetSdkVersion >= A) {
            List<String> needRequestPermissionList = findDeniedPermissions(permissions);
            if (!needRequestPermissionList.isEmpty()) {
                String[] array = needRequestPermissionList.toArray(new String[0]);
                Method method;
                try {
                    method = getClass().getMethod("requestPermissions", String[].class, int.class);
                    method.invoke(this, array, PERMISSION_REQUEST_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        if (AbstractCheckPermissionsActivity.this.getApplicationInfo().targetSdkVersion >= A) {
            try {
                for (String perm : permissions) {
                    Method checkSelfMethod = getClass().getMethod("checkSelfPermission", String.class);
                    Method shouldShowRequestPermissionRationaleMethod = getClass().getMethod("shouldShowRequestPermissionRationale", String.class);
                    if ((Integer) checkSelfMethod.invoke(this, perm) != PackageManager.PERMISSION_GRANTED
                            || Boolean.TRUE.equals(shouldShowRequestPermissionRationaleMethod.invoke(this, perm))) {
                        if (!NEED_CHECK_BACK_LOCATION && ACCESS_BACKGROUND_LOCATION.equals(perm)) {
                            continue;
                        }
                        needRequestPermissionList.add(perm);
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return needRequestPermissionList;
    }

    @Override
    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] paramArrayOfInt) {
        super.onRequestPermissionsResult(requestCode, permissions, paramArrayOfInt);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                showMissingPermissionDialog();
                isNeedCheck = false;
            } else {
                startLocation();
            }
        }
    }

    /**
     * 检测是否所有的权限都已经授权
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示信息
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AbstractCheckPermissionsActivity.this);
        builder.setTitle(com.module.common.R.string.notifyTitle);
        builder.setMessage(com.module.common.R.string.notifyMsg);
        // 拒绝, 退出应用
        builder.setNegativeButton(com.module.common.R.string.cancel, (dialog, which) -> dialog.cancel());
        builder.setPositiveButton(com.module.common.R.string.setting, (dialog, which) -> startAppSettings());
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 开始定位
     */
    public void startLocation() {
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 启动应用的设置
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + AbstractCheckPermissionsActivity.this.getPackageName()));
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        destroyLocation();
        super.onDestroy();
    }

    /**
     * 销毁定位
     */
    private void destroyLocation() {
        if (null != locationClient) {
            // 如果 AMapLocationClient 是在当前 Activity 实例化的，在 Activity 的 onDestroy 中一定要执行 AMapLocationClient 的 onDestroy
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    public interface LocationCallBack {
        /**
         * 回调
         *
         * @param longitude 经度
         * @param latitude  维度
         */
        void onLocationCallBack(double longitude, double latitude);
    }
}