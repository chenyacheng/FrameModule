package com.module.app.ui;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;

import com.module.app.R;
import com.module.arch.utils.LogUtils;

/**
 * 启动页
 *
 * SplashScreen 功能在 Android 12 上是强制启动的，在 Android 12 中就会出现双重 SplashScreen 的现象
 * https://developer.android.google.cn/guide/topics/ui/splash-screen?hl=en
 * https://developer.android.google.cn/reference/kotlin/android/window/SplashScreen?hl=en
 *
 * @author BD
 * @date 2021/11/10
 */
public class StartupActivity extends AppCompatActivity implements Handler.Callback {

    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.myLooper(), this);
        LogUtils.info(getClass().getSimpleName(), "启动页");
    }

    /**
     * 延长启动画面显示时间
     */
    private void extendDisplayTime() {
        MyViewModel myViewModel = new MyViewModel(getApplication());
        // Set up an OnPreDrawListener to the root view.
        final View content = findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        // 检查初始数据是否准备好。
                        if (myViewModel.isReady()) {
                            // 取消挂起，内容准备好了。
                            content.getViewTreeObserver().removeOnPreDrawListener(this);
                            return true;
                        } else {
                            // 挂起，内容未准备好。
                            return false;
                        }
                        //如果仅return false,则会产生一个永久显示SplashScreen的效果。
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 休眠1000毫秒后进入主界面
        mHandler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    protected void onPause() {
        // 防止启动界面退出后过一会又进入登录页面
        mHandler.removeMessages(0);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        super.onDestroy();
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        startActivity(new Intent(this, MainFragmentActivity.class));
        finish();
        overridePendingTransition(R.anim.activity_fade_enter, R.anim.activity_fade_exit);
        return true;
    }

    private static class MyViewModel extends AndroidViewModel {

        private final long startUptimeMillis = SystemClock.uptimeMillis();

        public MyViewModel(Application application) {
            super(application);
        }

        public boolean isReady() {
            return SystemClock.uptimeMillis() - startUptimeMillis > 3000;
        }
    }
}
