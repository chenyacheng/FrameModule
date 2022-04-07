package com.module.common.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.module.common.databinding.RecyclerFootviewLayoutBinding;

import java.util.List;

/**
 * RecyclerAdapter适配器，支持上拉加载
 *
 * @author bd
 * @date 2021/10/12
 */
public abstract class AbstractRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerHolder> {

    /**
     * 正在加载
     */
    public static final int LOADING = 1;
    /**
     * 加载完成
     */
    public static final int LOADING_COMPLETE = 2;
    /**
     * 加载到底
     */
    public static final int LOADING_END = 3;
    private static final int NORMAL_TYPE = 1;
    private static final int FOOT_TYPE = 2;
    private static final int EMPTY_TYPE = 3;
    private final boolean mIsLoadMore;
    /**
     * 默认为空视图
     */
    private boolean hasEmptyView = true;
    private FrameLayout emptyView;
    /**
     * 数据源
     */
    private List<T> list;
    /**
     * 点击事件监听器
     */
    private OnItemClickListener listener;
    /**
     * 长按监听器
     */
    private OnItemLongClickListener longClickListener;
    private RecyclerView recyclerView;
    private int size;
    /**
     * 当前加载状态，默认为加载完成
     */
    private int loadState = 2;

    protected AbstractRecyclerAdapter(List<T> list, boolean showFootLayout) {
        this.list = list;
        mIsLoadMore = showFootLayout;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (hasEmptyView) {
            this.emptyView = new FrameLayout(parent.getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            this.emptyView.setLayoutParams(layoutParams);
        }
        if (viewType == EMPTY_TYPE) {
            return new RecyclerHolder(emptyView);
        } else if (viewType == NORMAL_TYPE) {
            return new RecyclerHolder(getViewBinding(LayoutInflater.from(parent.getContext()), parent));
        } else {
            return new RecyclerHolder(RecyclerFootviewLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null && v != null && recyclerView != null) {
                listener.onItemClick(recyclerView, v, (Integer) v.getTag());
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null && v != null && recyclerView != null) {
                longClickListener.onItemLongClick(recyclerView, v, (Integer) v.getTag());
                return true;
            }
            return false;
        });
        if (NORMAL_TYPE == holder.getItemViewType()) {
            convert(holder.v, list.get(position), position);
        } else {
            if (holder.v instanceof RecyclerFootviewLayoutBinding) {
                ((RecyclerFootviewLayoutBinding) holder.v).llFooter.setEnabled(false);
                switch (loadState) {
                    // 正在加载
                    case LOADING:
                        ((RecyclerFootviewLayoutBinding) holder.v).pbLoading.setVisibility(View.VISIBLE);
                        ((RecyclerFootviewLayoutBinding) holder.v).tvLoading.setVisibility(View.VISIBLE);
                        ((RecyclerFootviewLayoutBinding) holder.v).llEnd.setVisibility(View.GONE);
                        break;
                    // 加载完成
                    case LOADING_COMPLETE:
                        ((RecyclerFootviewLayoutBinding) holder.v).pbLoading.setVisibility(View.INVISIBLE);
                        ((RecyclerFootviewLayoutBinding) holder.v).tvLoading.setVisibility(View.INVISIBLE);
                        ((RecyclerFootviewLayoutBinding) holder.v).llEnd.setVisibility(View.GONE);
                        break;
                    // 加载到底
                    case LOADING_END:
                        ((RecyclerFootviewLayoutBinding) holder.v).pbLoading.setVisibility(View.GONE);
                        ((RecyclerFootviewLayoutBinding) holder.v).tvLoading.setVisibility(View.GONE);
                        ((RecyclerFootviewLayoutBinding) holder.v).llEnd.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mIsLoadMore) {
            if (list.isEmpty()) {
                return EMPTY_TYPE;
            } else if (position + 1 == getItemCount()) {
                return FOOT_TYPE;
            } else {
                return NORMAL_TYPE;
            }
        } else {
            if (list.isEmpty()) {
                return EMPTY_TYPE;
            } else {
                return NORMAL_TYPE;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mIsLoadMore) {
            if (list.isEmpty()) {
                return 1;
            } else {
                return list.size() + 1;
            }
        } else {
            if (size != 0) {
                return Math.min(list.size(), size);
            } else {
                if (list.isEmpty()) {
                    return 1;
                } else {
                    return list.size();
                }
            }
        }
    }

    /**
     * 在RecyclerView提供数据的时候调用
     *
     * @param recyclerView recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    /**
     * 获取ViewBinding对象
     *
     * @param from   from
     * @param parent parent
     * @return ViewBinding
     */
    protected abstract ViewBinding getViewBinding(LayoutInflater from, ViewGroup parent);

    /**
     * 填充RecyclerView适配器的方法，子类需要重写
     *
     * @param viewBinding viewBinding
     * @param item        子项
     * @param position    位置
     */
    protected abstract void convert(ViewBinding viewBinding, T item, int position);

    public void setEmptyView(View emptyView) {
        hasEmptyView = false;
        this.emptyView = new FrameLayout(emptyView.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(emptyView.getLayoutParams().width, emptyView.getLayoutParams().height);
        this.emptyView.setLayoutParams(layoutParams);
        this.emptyView.addView(emptyView);
    }

    public void setListData(List<T> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<T> getData() {
        return list;
    }

    public void addDataList(List<T> addData) {
        if (null != list) {
            list.addAll(addData);
        }
        notifyDataSetChanged();
    }

    /**
     * 设置上拉加载状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    /**
     * 定义一个点击事件接口回调
     */
    public interface OnItemClickListener {
        /**
         * 点击
         *
         * @param parent   parent
         * @param view     view
         * @param position position
         */
        void onItemClick(RecyclerView parent, View view, int position);
    }

    public interface OnItemLongClickListener {
        /**
         * 长按
         *
         * @param parent   parent
         * @param view     view
         * @param position position
         */
        void onItemLongClick(RecyclerView parent, View view, int position);
    }
}
