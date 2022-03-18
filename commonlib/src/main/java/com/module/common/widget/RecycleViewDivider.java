package com.module.common.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义RecycleView分割线
 *
 * @author bd
 * @date 2021/11/23
 */
public class RecycleViewDivider extends RecyclerView.ItemDecoration {

    private final Rect mBounds = new Rect();
    private Drawable mDivider;
    /**
     * 设置左边间距
     */
    private int leftEdge = 0;
    /**
     * 设置右边间距
     */
    private int rightEdge = 0;

    public void setLeftEdge(int leftEdge) {
        this.leftEdge = leftEdge;
    }

    public void setRightEdge(int rightEdge) {
        this.rightEdge = rightEdge;
    }

    /**
     * Sets the {@link Drawable} for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    public void setDrawable(@NonNull Drawable drawable) {
        this.mDivider = drawable;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getLayoutManager() == null || mDivider == null) {
            return;
        }
        drawVertical(c, parent);
    }

    private void drawVertical(@NonNull Canvas canvas, @NonNull RecyclerView parent) {
        canvas.save();
        final int left;
        final int right;
        // noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right, parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }
        final int childCount = parent.getChildCount();
        // 最后一个item不画分割线，所以childCount要减去一
        for (int i = 0; i < childCount - 1; ++i) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, this.mBounds);
            final int bottom = this.mBounds.bottom + Math.round(child.getTranslationY());
            final int top = bottom - this.mDivider.getIntrinsicHeight();
            this.mDivider.setBounds(left + leftEdge, top, right - rightEdge, bottom);
            this.mDivider.draw(canvas);
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (null != mDivider) {
            int childAdapterPosition = parent.getChildAdapterPosition(view);
            int itemCount = state.getItemCount();
            if (childAdapterPosition == itemCount - 1) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            }
        }
    }
}
