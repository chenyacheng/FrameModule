package com.module.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.module.common.R;

/**
 * @author BD
 * @date 2022/6/24 14:03
 */
public class TintTextView extends androidx.appcompat.widget.AppCompatTextView {

    private Drawable normalDrawable;
    private Drawable pressedDrawable;
    private float pressedAlpha;

    public TintTextView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public TintTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TintTextView);
        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(context, R.color.common_00000000));
        normalDrawable = getBackground();
        if (null == normalDrawable) {
            normalDrawable = colorDrawable;
        }
        pressedDrawable = typedArray.getDrawable(R.styleable.TintTextView_pressedBackground);
        if (null == pressedDrawable) {
            pressedDrawable = normalDrawable;
        }
        pressedAlpha = typedArray.getFloat(R.styleable.TintTextView_pressedAlpha, 1.0f);
        typedArray.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.setBackground(pressedDrawable);
                this.setAlpha(pressedAlpha);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.setBackground(normalDrawable);
                this.setAlpha(1.0f);
                break;
            default:
                break;
        }
        return true;
    }
}
