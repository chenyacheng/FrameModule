package com.module.common.widget.round;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.module.common.widget.alpha.AlphaButton;

/**
 * 设置圆角和背景色
 *
 * @author vya
 * @date 2023/4/28 上午9:50
 */
public class RoundButton extends AlphaButton {

    private RoundGradientDrawable bg;

    public RoundButton(Context context) {
        super(context);
        initRoundButton(context, null);
    }

    public RoundButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initRoundButton(context, attrs);
    }

    private void initRoundButton(Context context, AttributeSet attrs) {
        Drawable drawable = getBackground();
        int bgColor;
        if (drawable instanceof ColorDrawable) {
            bgColor = ((ColorDrawable) drawable).getColor();
        } else {
            bgColor = Color.WHITE;
        }
        bg = RoundGradientDrawable.fromAttributeSet(context, attrs, bgColor);
        setBackground(bg);
    }

    public void setGradientBgForRoundButton(GradientDrawable.Orientation orientation, @ColorInt int[] colors) {
        bg.setOrientation(orientation);
        bg.setColors(colors);
    }
}
