package com.module.common.api;

import com.module.common.data.CaptchaBean;
import com.module.common.data.RegisterBean;
import com.module.common.data.VersionUpdateBean;
import com.module.common.constant.UrlConstant;
import com.module.common.data.LoginBean;
import com.module.common.data.UpdateUserPwdBean;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 接口回调
 *
 * @author BD
 * @date 2021/11/26
 */
public interface CommonApi {

    @POST(UrlConstant.TEST)
    Call<Object> test();

    /**
     * 验证码
     *
     * @param bean 对象
     * @return Object
     */
    @POST(UrlConstant.SEND_SMS)
    Call<Object> captcha(@Body CaptchaBean bean);

    /**
     * 注册
     *
     * @param bean 对象
     * @return Object
     */
    @POST(UrlConstant.REGISTER)
    Call<Object> register(@Body RegisterBean bean);

    /**
     * 登录
     *
     * @param bean 对象
     * @return Object
     */
    @POST(UrlConstant.LOGIN)
    Call<Object> login(@Body LoginBean bean);

    /**
     * 更新密码
     *
     * @param bean 对象
     * @return Object
     */
    @POST(UrlConstant.UPDATE_USER_PASSWORD)
    Call<Object> updateUserPassword(@Body UpdateUserPwdBean bean);

    /**
     * 用户头像更换
     *
     * @param bean 对象
     * @return Object
     */
    @Multipart
    @POST(UrlConstant.UPLOAD_USER_PORTRAIT)
    Call<Object> uploadUserPortrait(@Part List<MultipartBody.Part> bean);

    /**
     * 广告
     *
     * @return Object
     */
    @POST(UrlConstant.QUERY_START_PAGE)
    Call<Object> advertisingPage();

    /**
     * 检测版本更新
     *
     * @param bean 对象
     * @return Object
     */
    @POST(UrlConstant.VERIFY_VERSION)
    Call<Object> versionUpdate(@Body VersionUpdateBean bean);
}
