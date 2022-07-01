package com.module.app.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chenyacheng.snackbar.SnackBarBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.module.app.R;
import com.module.app.databinding.ActivityMainFragmentBinding;
import com.module.app.databinding.TabMainBottomLayoutBinding;
import com.module.app.request.AppViewModel;
import com.module.arch.base.BaseActivity;
import com.module.common.constant.RouterConstant;
import com.module.home.ui.fragment.HomeFragment;
import com.module.me.ui.fragment.MeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 *
 * @author BD
 * @date 2021/09/09
 */
@Route(path = RouterConstant.PATH_APP_MAIN_FRAGMENT_ACTIVITY)
public class MainFragmentActivity extends BaseActivity<ActivityMainFragmentBinding> {

    /**
     * 选项卡的各个选项的TextView的集合：用于切换时改变图标和文字颜色
     */
    private final List<TextView> bottomTabChecked = new ArrayList<>(2);
    private final String[] titles = {"首页", "我的"};
    private final int[] colors = {com.module.common.R.color.common_ff009944, com.module.common.R.color.common_ff717071};
    private final int[] normalIcon = {R.drawable.home_icon_normal, R.drawable.me_icon_normal};
    private final int[] selectedIcon = {R.drawable.home_icon_selected, R.drawable.me_icon_selected};
    private final List<Fragment> fragmentList = new ArrayList<>(2);
    /**
     * 第一次按下返回键的时间
     */
    private long lastBackPressedTime = 0;
    private TabLayoutMediator mediator;
    private ViewPagerOnPageChangeCallback onPageChangeCallback;
    private boolean smoothScroll;
    private AppViewModel viewModel;

    @NonNull
    @Override
    protected ActivityMainFragmentBinding getViewBinding() {
        return ActivityMainFragmentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViewModel() {
        viewModel = getActivityScopeViewModel(AppViewModel.class);
    }

    @Override
    protected void init() {
        final HomeFragment homeFragment = new HomeFragment();
        final MeFragment meFragment = new MeFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(meFragment);

        getBinding().mainViewPager.setAdapter(new MyFragmentPagerAdapter(this));
        getBinding().mainViewPager.setOffscreenPageLimit(fragmentList.size());
        // 禁止滑动
        getBinding().mainViewPager.setUserInputEnabled(false);
        // 去掉两侧的光晕效果
        getBinding().mainViewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        onPageChangeCallback = new ViewPagerOnPageChangeCallback();
        getBinding().mainViewPager.registerOnPageChangeCallback(onPageChangeCallback);

        // 将tabLayout和ViewPager2绑定
        TabLayout mTabLayout = getBinding().tabLayout;
        mediator = new TabLayoutMediator(mTabLayout, getBinding().mainViewPager, (tab, position) -> tab.setCustomView(getTabView(position)));
        mediator.attach();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTextAndIconColor(tab.getPosition(), true);
                getBinding().mainViewPager.setCurrentItem(tab.getPosition(), smoothScroll);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setTextAndIconColor(tab.getPosition(), false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // No-op
            }
        });
        // 设置第一选项卡为选中状态
        setTextAndIconColor(0, true);
    }

    private View getTabView(int curPos) {
        TabMainBottomLayoutBinding binding = TabMainBottomLayoutBinding.inflate(getLayoutInflater());
        binding.tabItemTv.setText(titles[curPos]);
        Drawable drawableIcon = ResourcesCompat.getDrawable(getResources(), normalIcon[curPos], null);
        if (null != drawableIcon) {
            drawableIcon.setBounds(0, 0, drawableIcon.getMinimumWidth(), drawableIcon.getMinimumHeight());
            binding.tabItemTv.setCompoundDrawables(null, drawableIcon, null, null);
        }
        bottomTabChecked.add(binding.tabItemTv);
        return binding.getRoot();
    }

    private void setTextAndIconColor(int i, boolean select) {
        bottomTabChecked.get(i).setTextColor(ContextCompat.getColor(this, select ? colors[0] : colors[1]));
        Drawable drawableIcon = ResourcesCompat.getDrawable(getResources(), select ? selectedIcon[i] : normalIcon[i], null);
        if (null != drawableIcon) {
            drawableIcon.setBounds(0, 0, drawableIcon.getMinimumWidth(), drawableIcon.getMinimumHeight());
            bottomTabChecked.get(i).setCompoundDrawables(null, drawableIcon, null, null);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // 当前 activity 被系统回收，不保存状态，清除掉
        outState.clear();
    }

    @Override
    public void onBackPressed() {
        // 按下返回键的时间间隔
        final int backPressedInterval = 2000;
        // 与上次点击返回键时刻作差
        if ((System.currentTimeMillis() - lastBackPressedTime) > backPressedInterval) {
            // 大于2000ms则认为是误操作，使用Toast进行提示
            SnackBarBuilder.getInstance().builderShort(this, "再按一次退出程序");
            // 并记录下本次点击“返回键”的时刻，以便下次进行判断
            lastBackPressedTime = System.currentTimeMillis();
        } else {
            SnackBarBuilder.getInstance().hideView();
            // 小于2000ms，App切入后台
            if (!moveTaskToBack(false)) {
                // 非根Activity，切入后台失败，执行返回操作
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onDestroy() {
        getBinding().mainViewPager.unregisterOnPageChangeCallback(onPageChangeCallback);
        onPageChangeCallback = null;
        mediator.detach();
        mediator = null;
        bottomTabChecked.clear();
        fragmentList.clear();
        viewModel = null;
        super.onDestroy();
    }

    private class MyFragmentPagerAdapter extends FragmentStateAdapter {


        public MyFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }

    private class ViewPagerOnPageChangeCallback extends ViewPager2.OnPageChangeCallback {

        @Override
        public void onPageScrollStateChanged(int state) {
            // 点击 tab 无动画、滑动有动画
            if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                smoothScroll = true;
            } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                smoothScroll = false;
            }
        }
    }
}