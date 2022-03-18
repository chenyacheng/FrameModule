package com.module.common.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.viewbinding.ViewBinding;

import java.util.List;

/**
 * ListView、GridView等适配器
 *
 * @author bd
 * @date 2021/10/12
 */
public abstract class AbstractListViewAdapter<T> extends BaseAdapter {

    /**
     * 数据源
     */
    private List<T> data;
    private int size;

    protected AbstractListViewAdapter(List<T> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        if (size != 0) {
            return Math.min(data.size(), size);
        } else {
            return data == null ? 0 : data.size();
        }
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListViewHolder viewHolder = ListViewHolder.get(convertView, getViewBinding(LayoutInflater.from(parent.getContext()), parent));
        convert(viewHolder.v, getItem(position), position);
        return viewHolder.getConvertView();
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
     * 转换
     *
     * @param viewBinding viewBinding
     * @param item        item
     * @param position    position
     */
    protected abstract void convert(ViewBinding viewBinding, T item, int position);

    public void setSize(int size) {
        this.size = size;
    }

    public void setListData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
