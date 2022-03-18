package com.module.common.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

/**
 * RecyclerView的Holder
 *
 * @author bd
 * @date 2021/10/12
 */
public class RecyclerHolder extends RecyclerView.ViewHolder {

    protected ViewBinding v;

    RecyclerHolder(ViewBinding viewBinding) {
        super(viewBinding.getRoot());
        v = viewBinding;
    }

    RecyclerHolder(@NonNull View itemView) {
        super(itemView);
    }
}
