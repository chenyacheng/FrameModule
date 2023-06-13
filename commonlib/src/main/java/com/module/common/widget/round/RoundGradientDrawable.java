package com.module.common.widget.round;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import com.module.common.R;

/**
 * @author vya
 * @date 2023/4/28 上午10:05
 */
public class RoundGradientDrawable extends GradientDrawable {

    public static RoundGradientDrawable fromAttributeSet(Context context, AttributeSet attrs, int argb) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundButton);
        int radius = typedArray.getDimensionPixelSize(R.styleable.RoundButton_radius, 0);
        typedArray.recycle();
        RoundGradientDrawable roundGradientDrawable = new RoundGradientDrawable();
        roundGradientDrawable.setCornerRadius(radius);
        roundGradientDrawable.setColor(argb);
        return roundGradientDrawable;
    }
}
