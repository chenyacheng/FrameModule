package com.module.me.ui.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.arch.base.BaseLazyFragment;
import com.module.arch.utils.LogUtils;
import com.module.common.constant.RouterConstant;
import com.module.common.widget.HeadToolBar;
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
    protected void lazyLoadInit() {
        LogUtils.info("Me", "Me启动");
        toolBar();
        getBinding().btn1.setOnClickListener(view -> ARouter.getInstance().build(RouterConstant.PATH_ME_AMOUNT_EDIT_TEXT_ACTIVITY).navigation());
        getBinding().btn2.setOnClickListener(view -> ARouter.getInstance().build(RouterConstant.PATH_ME_PHOTOS_ACTIVITY).navigation());
    }

    private void toolBar() {
        HeadToolBar headToolBar = getBinding().toolbarHead;
        headToolBar.setHeadToolBarBackground(com.module.common.R.color.common_ff009944);
        headToolBar.setMiddleTitle("我的");
    }

    @Override
    public void onDestroy() {
        viewModel = null;
        super.onDestroy();
    }
}
