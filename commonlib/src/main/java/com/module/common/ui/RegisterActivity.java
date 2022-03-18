package com.module.common.ui;

import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chenyacheng.snackbar.SnackBarBuilder;
import com.module.arch.base.BaseActivity;
import com.module.arch.utils.LogUtils;
import com.module.common.constant.RouterConstant;
import com.module.common.databinding.ActivityRegisterBinding;
import com.module.common.message.SharedViewModel;
import com.module.common.navigationbar.UltimateBar;
import com.module.common.utils.ProgressDialogUtils;

import java.lang.ref.WeakReference;

/**
 * 注册页
 *
 * @author BD
 * @date 2021/11/22
 */
@Route(path = RouterConstant.PATH_COMMON_REGISTER_ACTIVITY)
public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    private CommonViewModel viewModel;
    /**
     * 验证码倒计时，定义为全局变量
     */
    private CaptchaCountDownTimer captchaCountDownTimer = null;
    private SharedViewModel sharedViewModel;

    @NonNull
    @Override
    protected ActivityRegisterBinding getViewBinding() {
        return ActivityRegisterBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViewModel() {
        viewModel = getActivityScopeViewModel(CommonViewModel.class);
        sharedViewModel = getApplicationScopeViewModel(SharedViewModel.class);
        getLifecycle().addObserver(viewModel.commonRequest);
    }

    @Override
    protected void init() {
        LogUtils.info(getClass().getSimpleName(), "注册页");
        // 设置状态栏
        UltimateBar.Companion.with(this).create().immersionBar();

        viewModel.commonRequest.getMessageLiveData().observe(this, s -> {
            ProgressDialogUtils.getInstance().hideProgress();
            SnackBarBuilder.getInstance().builderLong(this, s);
        });


    }

    @Override
    protected void onDestroy() {
        viewModel = null;
        sharedViewModel = null;
        // 销毁，避免内存泄漏
        if (null != captchaCountDownTimer) {
            captchaCountDownTimer.cancel();
            captchaCountDownTimer = null;
        }
        super.onDestroy();
    }

    private static class CaptchaCountDownTimer extends CountDownTimer {

        private final WeakReference<TextView> weakTextView;

        /**
         * 构造方法
         *
         * @param textView          传递的button
         * @param millisInFuture    总毫秒数
         * @param countDownInterval 倒计时的毫秒数
         */
        private CaptchaCountDownTimer(TextView textView, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            weakTextView = new WeakReference<>(textView);
        }

        /**
         * 计时过程
         *
         * @param millisUntilFinished 总毫秒数倒计时
         */
        @Override
        public void onTick(long millisUntilFinished) {
            if (weakTextView.get() != null) {
                long countDown = millisUntilFinished / 1000;
                String countDownStr = countDown + "s";
                weakTextView.get().setEnabled(false);
                weakTextView.get().setText(countDownStr);
            }
        }

        /**
         * 计时完毕的方法
         */
        @Override
        public void onFinish() {
            if (weakTextView.get() != null) {
                weakTextView.get().setText("获取验证码");
                weakTextView.get().setEnabled(true);
            }
        }
    }
}
