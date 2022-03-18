package com.module.common.widget;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * RecyclerView滑动监听
 *
 * @author bd
 * @date 2021/11/17
 */
public abstract class AbstractStaggeredGridOnScrollListener extends RecyclerView.OnScrollListener {

    /**
     * 用来标记是否正在向上滑动
     */
    private boolean isSlidingUpward = false;

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
        // 当不滑动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE && null != manager) {
            // 获取最后一个完全显示的itemPosition
            int[] lastItemPosition = manager.findLastCompletelyVisibleItemPositions(new int[2]);
            int itemCount = manager.getItemCount();
            // 判断是否滑动到了最后一个item，并且是向上滑动
            if (lastItemPosition[1] == (itemCount - 1) && isSlidingUpward) {
                // 加载更多
                onLoadMore();
            }
        }
    }

    /**
     * 加载更多回调
     */
    public abstract void onLoadMore();

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
        isSlidingUpward = dy > 0;
    }
}
