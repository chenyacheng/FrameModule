package com.module.me.ui.activity.me;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.me.ui.fragment.MeFragment;
import com.module.me.databinding.MeActivityMeMainBinding;
import com.module.arch.base.BaseActivity;
import com.module.common.constant.RouterConstant;
import com.module.common.store.UserInfo;

/**
 * @author BD
 */
public class MeActivity extends BaseActivity<MeActivityMeMainBinding> {

    private MeFragment fragment;

    @NonNull
    @Override
    protected MeActivityMeMainBinding getViewBinding() {
        return MeActivityMeMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected void init() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        String a = "userInfo";
        if (((String) UserInfo.getInstance().getData(a, "")).isEmpty()) {
            ARouter.getInstance().build(RouterConstant.PATH_COMMON_LOGIN_ACTIVITY).navigation();
        } else {
            fragment = new MeFragment();
            getBinding().meViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle()));
        }
    }

    private class MyFragmentPagerAdapter extends FragmentStateAdapter {

        public MyFragmentPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragment;
        }

        @Override
        public int getItemCount() {
            return 1;
        }
    }
}
