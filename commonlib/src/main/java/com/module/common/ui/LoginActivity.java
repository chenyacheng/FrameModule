package com.module.common.ui;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chenyacheng.snackbar.SnackBarBuilder;
import com.module.arch.base.BaseActivity;
import com.module.arch.utils.LogUtils;
import com.module.common.constant.RouterConstant;
import com.module.common.databinding.ActivityLoginBinding;
import com.module.common.message.SharedViewModel;
import com.module.common.navigationbar.UltimateBar;

/**
 * 登录页
 *
 * @author BD
 * @date 2021/11/17
 */
@Route(path = RouterConstant.PATH_COMMON_LOGIN_ACTIVITY)
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    /**
     * 出现顶号时，字段不会为空
     */
    @Autowired
    public String accountExceptionTips = "";
    private boolean pwdShow = false;
    private CommonViewModel viewModel;
    private SharedViewModel sharedViewModel;

    @NonNull
    @Override
    protected ActivityLoginBinding getViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViewModel() {
        viewModel = getActivityScopeViewModel(CommonViewModel.class);
        sharedViewModel = getApplicationScopeViewModel(SharedViewModel.class);
        getLifecycle().addObserver(viewModel.commonRequest);
    }

    @Override
    protected void init() {
        // 路由传递参数，需要初始化
        ARouter.getInstance().inject(this);
        if (!"".equals(accountExceptionTips)) {
            SnackBarBuilder.getInstance().builderLong(this, accountExceptionTips);
        }
        LogUtils.info(getClass().getSimpleName(), "登录页");
        // 设置状态栏
        UltimateBar.Companion.with(this).create().immersionBar();


    }

    @Override
    protected void onDestroy() {
        viewModel = null;
        sharedViewModel = null;
        super.onDestroy();
    }
}
