package com.module.home.ui.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chenyacheng.snackbar.SnackBarBuilder;
import com.module.home.databinding.HomeFragmentHomeMainBinding;
import com.module.arch.base.BaseLazyFragment;
import com.module.arch.utils.LogUtils;
import com.module.common.data.CaptchaBean;
import com.module.common.message.SharedViewModel;
import com.module.home.ui.HomeViewModel;

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
    protected void init() {
        LogUtils.info("Home", "Home启动");

        homeViewModel.homeRequest.getMessageLiveData().observe(getViewLifecycleOwner(), s -> SnackBarBuilder.getInstance().builderLong(context, s));
        homeViewModel.homeRequest.captcha(new CaptchaBean("1", "```"));
        homeViewModel.homeRequest.getCaptchaLiveData().observe(getViewLifecycleOwner(), o -> {
            System.out.println(o);
        });
    }

    @Override
    public void onDestroy() {
        homeViewModel = null;
        sharedViewModel = null;
        super.onDestroy();
    }
}
