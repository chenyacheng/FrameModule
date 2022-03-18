package com.module.common.adapter;

import android.view.View;

import androidx.viewbinding.ViewBinding;

/**
 * ListView的Holder
 *
 * @author bd
 * @date 2021/10/12
 */
public class ListViewHolder {

    private final View mConvertView;

    protected ViewBinding v;

    ListViewHolder(ViewBinding viewBinding) {
        mConvertView = viewBinding.getRoot();
        mConvertView.setTag(this);
        v = viewBinding;
    }

    public static ListViewHolder get(View convertView, ViewBinding viewBinding) {
        if (convertView == null) {
            return new ListViewHolder(viewBinding);
        }
        return (ListViewHolder) convertView.getTag();
    }

    View getConvertView() {
        return mConvertView;
    }
}
