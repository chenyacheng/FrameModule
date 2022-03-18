package com.module.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView 内嵌套 RecyclerView 导致外层 item 点击不响应，去除子 RecyclerView 的 touch 监听
 *
 * @author BD
 * @date 2021/12/02
 */
public class NoTouchRecyclerView extends RecyclerView {

    public NoTouchRecyclerView(@NonNull Context context) {
        super(context);
    }

    public NoTouchRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NoTouchRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            // 如果点击
            performClick();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return true;
    }
    @Override
    public boolean performClick() {
        return super.performClick();
    }

}
