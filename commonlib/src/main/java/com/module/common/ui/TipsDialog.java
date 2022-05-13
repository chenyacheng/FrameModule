package com.module.common.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.module.common.databinding.DialogTipsBinding;

/**
 * 登录提示
 *
 * @author BD
 * @date 2022/2/28 17:05
 */
public class TipsDialog extends DialogFragment {

    private static final String TAG = "TipsDialog";
    private LoginListener loginListener;

    public TipsDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (null != dialog) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
        }
        DialogTipsBinding binding = DialogTipsBinding.inflate(getLayoutInflater());
        binding.cancel.setOnClickListener(v -> dismissAllowingStateLoss());
        binding.confirm.setOnClickListener(v -> {
            if (null != loginListener) {
                loginListener.onConfirm();
            }
            dismissAllowingStateLoss();
        });

        return binding.getRoot();
    }

    public void show(FragmentActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        super.show(fm, TAG);
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    @Override
    public void onDestroy() {
        loginListener = null;
        super.onDestroy();
    }

    public interface LoginListener {
        /**
         * 回调
         */
        void onConfirm();
    }
}
