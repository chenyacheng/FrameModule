package com.module.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/**
 * 自定义NestedScrollView
 *
 * @author bd
 * @date 2021/10/12
 */
public class CustomScrollView extends NestedScrollView {

    private TranslucentListener listener;

    public CustomScrollView(@NonNull Context context) {
        super(context);
    }

    public CustomScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTranslucentListener(TranslucentListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
            float a = 3f;
            int scrollY = getScrollY();
            int screenHeight = getContext().getResources().getDisplayMetrics().heightPixels;
            if (scrollY <= screenHeight / a) {
                listener.onTranslucent(scrollY / (screenHeight / 3f));
            }
        }
    }

    public interface TranslucentListener {
        /**
         * 透明度的回调
         *
         * @param alpha alpha
         */
        void onTranslucent(float alpha);
    }
}
