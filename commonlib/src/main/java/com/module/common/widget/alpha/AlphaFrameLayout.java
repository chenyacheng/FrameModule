package com.module.common.widget.alpha;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.module.common.R;

/**
 * @author vya
 * @date 2023/4/29 下午3:25
 */
public class AlphaFrameLayout extends FrameLayout {

    private AlphaViewHelper alphaViewHelper;
    private float pressedAlpha;

    public AlphaFrameLayout(@NonNull Context context) {
        super(context);
        initAlphaFrameLayout(context, null);
    }

    public AlphaFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAlphaFrameLayout(context, attrs);
    }

    private void initAlphaFrameLayout(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AlphaFrameLayout);
        pressedAlpha = typedArray.getFloat(R.styleable.AlphaFrameLayout_pressedAlpha, 0.5f);
        typedArray.recycle();
    }

    private AlphaViewHelper getAlphaViewHelper() {
        if (alphaViewHelper == null) {
            alphaViewHelper = new AlphaViewHelper(this, pressedAlpha);
        }
        return alphaViewHelper;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        getAlphaViewHelper().onPressedChanged(pressed);
    }

    /**
     * 设置是否要在 press 时改变透明度
     *
     * @param changeAlphaWhenPress 是否要在 press 时改变透明度
     */
    public void setChangeAlphaWhenPress(boolean changeAlphaWhenPress) {
        getAlphaViewHelper().setChangeAlphaWhenPress(changeAlphaWhenPress);
    }

    /**
     * 设置是否要在 disabled 时改变透明度
     *
     * @param changeAlphaWhenDisable 是否要在 disabled 时改变透明度
     */
    public void setChangeAlphaWhenDisable(boolean changeAlphaWhenDisable) {
        getAlphaViewHelper().setChangeAlphaWhenDisable(changeAlphaWhenDisable);
    }
}
