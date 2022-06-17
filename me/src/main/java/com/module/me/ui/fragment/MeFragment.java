package com.module.me.ui.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.module.arch.base.BaseLazyFragment;
import com.module.arch.utils.LogUtils;
import com.module.me.databinding.MeFragmentMeMainBinding;
import com.module.me.request.MeViewModel;

/**
 * @author BD
 */
public class MeFragment extends BaseLazyFragment<MeFragmentMeMainBinding> {

    private MeViewModel viewModel;


    @NonNull
    @Override
    protected MeFragmentMeMainBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return MeFragmentMeMainBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViewModel() {
        viewModel = getFragmentScopeViewModel(MeViewModel.class);
    }

    @Override
    protected void init() {
        LogUtils.info("Me", "Me启动");
        toolBar();

    }

    private void toolBar() {

    }

    @Override
    public void onDestroy() {
        viewModel = null;
        super.onDestroy();
    }
}
