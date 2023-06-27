package com.module.common.widget.alpha;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.module.common.R;

/**
 * 在 pressed 和 disabled 时改变 View 的透明度
 *
 * @author vya
 * @date 2023/4/24 上午10:37
 */
public class AlphaButton extends AppCompatButton {

    private AlphaViewHelper alphaViewHelper;
    private float pressedAlpha;

    public AlphaButton(@NonNull Context context) {
        super(context);
        initAlphaButton(context, null);
    }

    public AlphaButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAlphaButton(context, attrs);
    }

    private void initAlphaButton(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AlphaButton);
        pressedAlpha = typedArray.getFloat(R.styleable.AlphaButton_pressedAlpha, 0.5f);
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

    public void setGradientBgForAlphaButton(GradientDrawable.Orientation orientation, @ColorInt int[] colors) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setOrientation(orientation);
        drawable.setColors(colors);
        setBackground(drawable);
    }
}
