package com.module.common.network;

import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import com.module.arch.utils.LogUtils;
import com.module.common.constant.RouterConstant;
import com.module.common.store.UserInfo;
import com.module.network.BaseResponse;
import com.module.network.ResponseListener;

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
        Gson gson = new GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.LAZILY_PARSED_NUMBER).serializeNulls().create();
        BaseResponse baseResponse = gson.fromJson(new Gson().toJson(o), BaseResponse.class);
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
                        .withString("accountExceptionTips", baseResponse.getMessage())
                        .navigation();
            }
            return;
        }
        if (code.equals(baseResponse.getCode())) {
            if (null != baseResponse.getData()) {
                responseDataProcessListener.onSuccess(baseResponse.getData());
            } else {
                responseDataProcessListener.onSuccessNoData(baseResponse.getMessage());
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
