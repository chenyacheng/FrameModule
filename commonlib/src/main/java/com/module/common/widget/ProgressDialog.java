package com.module.common.widget;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.module.common.R;
import com.module.common.databinding.NetworkRequestProgressDialogBinding;

/**
 * 自定义progressDialog对话框，用于网络请求时提示用户当前操作正在运行，让用户等待.
 *
 * @author bd
 * @date 2021/10/09
 */
public class ProgressDialog extends Dialog {

    NetworkRequestProgressDialogBinding binding;

    public ProgressDialog(@NonNull Context context) {
        super(context, R.style.network_request_progress_dialog);
    }

    public void showProgress(String message) {
        binding = NetworkRequestProgressDialogBinding.inflate(getLayoutInflater());
        // 自定义的layout样式
        setContentView(binding.getRoot());
        // 设置不可通过点击外面区域取消
        setCanceledOnTouchOutside(false);
        binding.progressTextView.setText(message);
    }

    @Override
    public void onDetachedFromWindow() {
        binding = null;
        super.onDetachedFromWindow();
    }
}
