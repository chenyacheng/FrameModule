package com.module.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.module.common.R;

/**
 * 子布局根据父布局背景的圆角大小进行调整，把 TextView 直角突出的部分给裁剪掉
 *
 * @author BD
 * @date 2022/6/27 16:05
 */
public class AutoFixLinearLayout extends LinearLayout {

    private int mRadius = 0;
    private Path path;
    private RectF rectF;

    public AutoFixLinearLayout(Context context) {
        super(context);
        init(context, null);
    }

    public AutoFixLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        path = new Path();
        rectF = new RectF();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoFixLinearLayout);
        mRadius = typedArray.getInt(R.styleable.AutoFixLinearLayout_roundCorner, 0);
        typedArray.recycle();
        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(context, R.color.common_00000000));
        ColorDrawable normalDrawable = (ColorDrawable) getBackground();
        if (null == normalDrawable) {
            normalDrawable = colorDrawable;
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(dp2px(mRadius));
        gradientDrawable.setColor(normalDrawable.getColor());
        setBackground(gradientDrawable);
    }

    private int dp2px(int dp) {
        return Math.round(getResources().getDisplayMetrics().density * dp);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        rectF.set(0, 0, getWidth(), getHeight());
        path.addRoundRect(rectF, dp2px(mRadius), dp2px(mRadius), Path.Direction.CW);
        canvas.clipPath(path);
        super.dispatchDraw(canvas);
    }
}
