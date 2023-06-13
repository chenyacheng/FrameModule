package com.module.home.ui.fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chenyacheng.snackbar.SnackBarBuilder;
import com.module.arch.base.BaseLazyFragment;
import com.module.arch.utils.LogUtils;
import com.module.common.constant.RouterConstant;
import com.module.common.message.SharedViewModel;
import com.module.common.utils.ProgressDialogUtils;
import com.module.common.widget.HeadToolBar;
import com.module.home.databinding.HomeFragmentHomeMainBinding;
import com.module.home.request.HomeViewModel;

/**
 * @author BD
 */
public class HomeFragment extends BaseLazyFragment<HomeFragmentHomeMainBinding> {


    private HomeViewModel homeViewModel;
    private SharedViewModel sharedViewModel;

    @NonNull
    @Override
    protected HomeFragmentHomeMainBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return HomeFragmentHomeMainBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViewModel() {
        homeViewModel = getFragmentScopeViewModel(HomeViewModel.class);
        sharedViewModel = getApplicationScopeViewModel(SharedViewModel.class);
    }

    @Override
    protected void lazyLoadInit() {
        LogUtils.info("Home", "Home启动");
        toolBar();
        homeViewModel.getMessageLiveData().observe(getViewLifecycleOwner(), s -> {
            ProgressDialogUtils.getInstance().hideProgress();
            SnackBarBuilder.getInstance().builderLong(context, s);
        });
        homeViewModel.getTest().observe(getViewLifecycleOwner(), o -> {
            ProgressDialogUtils.getInstance().hideProgress();
            SnackBarBuilder.getInstance().builderShort(context, "成功");
            System.out.println(o);
        });
        getBinding().btn.setOnClickListener(v -> {
            ProgressDialogUtils.getInstance().showProgress(context);
            homeViewModel.test();
        });

        getBinding().btn1.setOnClickListener(v ->
                ARouter.getInstance().build(RouterConstant.PATH_HOME_BUTTON_AND_LAYOUT_ACTIVITY).navigation());

        getBinding().swipeRefresh.setOnRefreshListener(
                () -> new Handler(Looper.getMainLooper()).postDelayed(
                        () -> getBinding().swipeRefresh.setRefreshing(false), 2000));
    }

    private void toolBar() {
        HeadToolBar headToolBar = getBinding().toolbarHead;
        headToolBar.setHeadToolBarBackground(com.module.common.R.color.common_ff009944);
        headToolBar.setMiddleTitle("首页");
    }

    @Override
    public void onDestroy() {
        homeViewModel = null;
        sharedViewModel = null;
        super.onDestroy();
    }
}
