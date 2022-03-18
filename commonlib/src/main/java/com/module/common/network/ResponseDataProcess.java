package com.module.common.network;

import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.arch.base.BaseResponse;
import com.module.arch.callback.ResponseListener;
import com.module.arch.utils.GsonUtils;
import com.module.arch.utils.LogUtils;
import com.module.common.constant.RouterConstant;
import com.module.common.store.UserInfo;

import java.util.List;

/**
 * 处理接口返回的数据
 *
 * @author BD
 * @date 2021/12/30 11:18
 */
public class ResponseDataProcess implements ResponseListener {

    private final ResponseDataProcessListener responseDataProcessListener;

    public ResponseDataProcess(ResponseDataProcessListener responseDataProcessListener) {
        this.responseDataProcessListener = responseDataProcessListener;
    }

    @Override
    public void onSuccess(Object o) {
        BaseResponse baseResponse = GsonUtils.removeSpaceFromJson(o, BaseResponse.class);
        String code = "200";
        // 状态码999，说明是顶号，关闭所有页面，并跳转到登录页
        String codeStatus = "999";
        if (codeStatus.equals(baseResponse.getCode())) {
            String a = "userInfo";
            if (!((String) UserInfo.getInstance().getData(a, "")).isEmpty()) {
                UserInfo.getInstance().clearAllData();
                ARouter.getInstance().build(RouterConstant.PATH_COMMON_LOGIN_ACTIVITY)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .withBoolean("accountExceptionTips", true)
                        .navigation();
            }
            return;
        }
        if (code.equals(baseResponse.getCode())) {
            if (null != baseResponse.getData()) {
                if (baseResponse.getData() instanceof List) {
                    if (!((List<?>) baseResponse.getData()).isEmpty()) {
                        responseDataProcessListener.onSuccess(baseResponse.getData());
                    }
                } else {
                    responseDataProcessListener.onSuccess(baseResponse.getData());
                }
            } else {
                responseDataProcessListener.onSuccess(baseResponse.getMessage());
            }
        } else {
            ExceptionHandleUtils exceptionHandleUtils = new ExceptionHandleUtils(baseResponse.getMessage());
            responseDataProcessListener.onFailure(exceptionHandleUtils.getMessage());
        }
    }

    @Override
    public void onFailure(Throwable t) {
        LogUtils.showLogCompletion("onFail:", t.toString(), 1000, "verbose");
        // 自定义异常处理
        ExceptionHandleUtils exceptionHandleUtils = ExceptionHandleUtils.handleException(t);
        responseDataProcessListener.onFailure(exceptionHandleUtils.getMessage());

    }
}
