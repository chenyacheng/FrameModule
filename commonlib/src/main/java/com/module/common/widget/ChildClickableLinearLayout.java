package com.module.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * 可直接控制所有子控件是否可点击的 LinearLayout
 *
 * @author BD
 * @date 2022/5/24 10:18
 */
public class ChildClickableLinearLayout extends LinearLayout {

    /**
     * 子控件是否可以接受点击事件
     */
    private boolean childClickable = true;

    public ChildClickableLinearLayout(Context context) {
        super(context);
    }

    public ChildClickableLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 返回 true 则拦截子控件所有点击事件，如果 childClickable 为 true，则需返回 false
        return !childClickable;
    }

    /**
     * 然后就像正常 LinearLayout 一样使用这个控件就可以了。在需要的时候调用一下 setChildClickable，
     * 参数为 true 则所有子控件可以点击，false 则不可点击。
     *
     * @param clickable true or false
     */
    public void setChildClickable(boolean clickable) {
        childClickable = clickable;
    }
}
