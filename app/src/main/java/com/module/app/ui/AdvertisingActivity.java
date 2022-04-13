package com.module.app.ui;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chenyacheng.snackbar.SnackBarBuilder;
import com.module.app.databinding.ActivityAdvertisingBinding;
import com.module.app.databinding.ActivityAdvertisingItemBinding;
import com.module.arch.base.BaseActivity;
import com.module.arch.utils.GsonUtils;
import com.module.arch.utils.LogUtils;
import com.module.common.AppConfig;
import com.module.common.constant.RouterConstant;
import com.module.common.data.AdvertisingResult;
import com.module.common.navigationbar.UltimateBar;
import com.module.common.store.UserInfo;
import com.module.common.store.UserInfoResult;
import com.module.common.utils.ImageViewDisplayUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

/**
 * 广告页
 *
 * @author BD
 * @date 2021/12/23
 */
@Route(path = RouterConstant.PATH_APP_ADVERTISING_ACTIVITY)
public class AdvertisingActivity extends BaseActivity<ActivityAdvertisingBinding> {

    private final List<View> viewList = new ArrayList<>();
    private AppViewModel appViewModel;

    @NonNull
    @Override
    protected ActivityAdvertisingBinding getViewBinding() {
        return ActivityAdvertisingBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViewModel() {
        appViewModel = getActivityScopeViewModel(AppViewModel.class);
        getLifecycle().addObserver(appViewModel.appRequest);
    }

    @Override
    protected void init() {
        LogUtils.info(getClass().getSimpleName(), "广告页");
        // 设置状态栏
        UltimateBar.Companion.with(this).create().immersionBar();

        appViewModel.appRequest.getMessageLiveData().observe(this, s -> SnackBarBuilder.getInstance().builderLong(this, s));
        appViewModel.appRequest.advertising();
        appViewModel.appRequest.getAdvertisingLiveData().observe(this, o -> {
            AdvertisingResult advertisingResult = GsonUtils.removeSpaceFromJson(o, AdvertisingResult.class);
            ActivityAdvertisingItemBinding binding = ActivityAdvertisingItemBinding.inflate(getLayoutInflater());
            viewList.add(binding.getRoot());
            ImageViewDisplayUtils.Companion.imageViewNotPlaceholderDisplay(binding.imageView, AppConfig.IMAGE_URL + advertisingResult.getPictureAddress());
            getBinding().viewPager.setAdapter(new ViewPagerAdapter());
            final int[] a = {5};
            getBinding().countDown.setText(5 + "s|跳过");
            getBinding().countDown.setOnChronometerTickListener(chronometer -> {
                if (a[0] > 0) {
                    getBinding().countDown.setText(a[0] + "s|跳过");
                    a[0]--;
                } else {
                    getBinding().countDown.stop();
                    getBinding().countDown.setVisibility(View.GONE);
                    onFinish();
                }
            });
            getBinding().countDown.start();
            getBinding().countDown.setOnClickListener(v -> onFinish());
        });
    }

    private void onFinish() {
        String a = "userInfo";
        if (((String) UserInfo.getInstance().getData(a, "")).isEmpty()) {
            ARouter.getInstance().build(RouterConstant.PATH_COMMON_LOGIN_ACTIVITY).navigation();
        } else {
            // 设置用户ID，能精确定位到某个用户的异常
            CrashReport.setUserId(UserInfoResult.getUserInfo().getUserAccount());
            ARouter.getInstance().build(RouterConstant.PATH_APP_MAIN_FRAGMENT_ACTIVITY).navigation();
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        appViewModel = null;
        super.onDestroy();
    }

    /**
     * ViewPager数据适配器
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
