package com.module.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 自定义控件转圈圈
 *
 * @author bd
 * @date 2022/09/09
 */
public class LoadView extends View {

    private final Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mCurrentIndex = 0;

    public LoadView(Context context) {
        this(context, null);
    }

    public LoadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = 10;
        if (mCurrentIndex >= count) {
            mCurrentIndex = 0;
        }
        int endAlpha = 255 / count;
        for (int i = 0; i < count; i++) {
            int alpha;
            if (mCurrentIndex - i > 0) {
                alpha = endAlpha * (mCurrentIndex - i);
            } else {
                alpha = 255 - 255 / count * (i - mCurrentIndex);
            }
            mPaint.setColor(Color.argb(alpha, 89, 53, 246));
            canvas.drawLine(mWidth / 2, 26, mWidth / 2, 46, mPaint);
            canvas.rotate(360 / count, mWidth / 2, mHeight / 2);
        }
        mCurrentIndex++;
        postInvalidateDelayed(200);
    }
}
