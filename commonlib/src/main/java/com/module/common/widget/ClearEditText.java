package com.module.common.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

/**
 * 自定义一个带清除(Clear)按钮的EditText
 *
 * @author BD
 * @date 2022/4/20 14:24
 */
public class ClearEditText extends AppCompatEditText implements TextWatcher, View.OnFocusChangeListener {

    /**
     * 清除按钮图片
     */
    private Drawable mClearDrawable;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 默认设置隐藏图标
        setClearIconVisible(false);
        // 设置焦点改变的监听
        setOnFocusChangeListener(this);
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible true显示，false不显示
     */
    private void setClearIconVisible(boolean visible) {
        if (mClearDrawable == null) {
            return;
        }
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(null, null, right, null);
    }

    public void setClearIcon(int resId) {
        mClearDrawable = ResourcesCompat.getDrawable(getResources(), resId, null);
        if (mClearDrawable != null) {
            // 设置图标的位置以及大小，getIntrinsicWidth()获取显示出来的大小而不是原图片的大小
            mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        }
    }

    /**
     * 当ClearEditText焦点发生变化的时候：
     * 有焦点并且输入的文本内容不为空时则显示清除按钮
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        setClearIconVisible(hasFocus && !TextUtils.isEmpty(getText()));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    /**
     * 当输入框里面内容发生变化的时候：
     * 有焦点并且输入的文本内容不为空时则显示清除按钮
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        setClearIconVisible(s.length() > 0);
    }

    /**
     * 用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置在 EditText 的宽度 - 文本右边到图标左边缘的距离 - 图标左边缘至控件右边缘的距离
     * 到 EditText的右边缘 之间就算点击了图标，竖直方向就以 EditText 高度为边界
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && mClearDrawable != null) {
            performClick();
            // getTotalPaddingRight()图标左边缘至控件右边缘的距离
            // getCompoundDrawablePadding()表示从文本右边到图标左边缘的距离
            int left = getWidth() - getTotalPaddingRight() - getCompoundDrawablePadding();
            boolean touchable = event.getX() > left && event.getX() < getWidth();
            if (touchable) {
                this.setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}