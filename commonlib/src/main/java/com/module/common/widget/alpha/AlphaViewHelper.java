package com.module.common.widget.alpha;

import android.view.View;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * @author vya
 * @date 2023/4/24 上午11:46
 */
public class AlphaViewHelper {

    private final WeakReference<View> mTarget;

    /**
     * 设置是否要在 press 时改变透明度
     */
    private boolean mChangeAlphaWhenPress = true;

    private final float mPressedAlpha;

    public AlphaViewHelper(@NonNull View target, float pressedAlpha) {
        mTarget = new WeakReference<>(target);
        mPressedAlpha = pressedAlpha;
    }

    /**
     * 在 {@link View#setPressed(boolean)} 中调用，通知 helper 更新
     *
     * @param pressed {@link View#setPressed(boolean)} 中接收到的参数
     */
    public void onPressedChanged(boolean pressed) {
        View target = mTarget.get();
        if (target != null) {
            if (target.isEnabled()) {
                float mNormalAlpha = 1f;
                target.setAlpha(mChangeAlphaWhenPress && pressed && target.isClickable() ? mPressedAlpha : mNormalAlpha);
            }
        }
    }

    /**
     * 设置是否要在 press 时改变透明度
     *
     * @param changeAlphaWhenPress 是否要在 press 时改变透明度
     */
    public void setChangeAlphaWhenPress(boolean changeAlphaWhenPress) {
        mChangeAlphaWhenPress = changeAlphaWhenPress;
    }

    /**
     * 设置是否要在 disabled 时改变透明度
     *
     * @param changeAlphaWhenDisable 是否要在 disabled 时改变透明度
     */
    public void setChangeAlphaWhenDisable(boolean changeAlphaWhenDisable) {
        View target = mTarget.get();
        if (target != null) {
            target.setEnabled(!changeAlphaWhenDisable);
        }
    }
}
