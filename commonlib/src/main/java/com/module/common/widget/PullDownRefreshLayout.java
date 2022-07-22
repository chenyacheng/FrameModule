package com.module.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.jetbrains.annotations.NotNull;

/**
 * 下拉 Header 的同时下拉内容
 *
 * @author BD
 * @date 2022/6/30 9:10
 */
public class PullDownRefreshLayout extends SwipeRefreshLayout {

    private static final float DRAG_RATE = .5f;
    private static final int DURATION = 400;
    private int mTouchSlop;
    private float mInitialMotionY;
    private View target;
    private ImageView circleView;
    private int circleViewBottom;

    public PullDownRefreshLayout(@NonNull @NotNull Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public PullDownRefreshLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof ImageView) {
                circleView = (ImageView) child;
            } else {
                target = child;
            }
        }
        // circleViewBottom 默认值为 0。
        circleViewBottom = circleView.getBottom();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int pointerIndex;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pointerIndex = ev.findPointerIndex(ev.getPointerId(0));
                if (pointerIndex < 0) {
                    return false;
                }
                float mInitialDownY = ev.getY(pointerIndex);
                mInitialMotionY = mInitialDownY + mTouchSlop;
                break;
            case MotionEvent.ACTION_MOVE:
                pointerIndex = ev.findPointerIndex(ev.getPointerId(0));
                if (pointerIndex < 0) {
                    return false;
                }
                final float y = ev.getY(pointerIndex);
                // 记录手指移动的距离，mInitialMotionY是初始的位置，DRAG_RATE是拖拽因子。
                final float overscrollTop = (y - mInitialMotionY) * DRAG_RATE;
                // 赋值给 mTarget 的 top 使之产生拖动效果。
                if (overscrollTop >= 0) {
                    int zero = circleViewBottom + getProgressViewStartOffset();
                    if (zero > getProgressViewStartOffset()) {
                        if (zero > 0) {
                            target.setTranslationY(overscrollTop + zero);
                        } else {
                            target.setTranslationY(overscrollTop);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                // 手指松开时启动动画。
                // getProgressViewStartOffset()=-80 getProgressViewEndOffset()=128。
                if (circleViewBottom >= getProgressViewEndOffset()) {
                    float value = 2f * getProgressViewEndOffset() + getProgressViewStartOffset();
                    target.animate().translationY(value).setDuration(DURATION).start();
                } else {
                    target.animate().translationY(0).setDuration(DURATION).start();
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
        target.animate().translationY(0).setDuration(DURATION).start();
    }
}
