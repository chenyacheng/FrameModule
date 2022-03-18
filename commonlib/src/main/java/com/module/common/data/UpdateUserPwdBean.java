package com.module.common.data;

public class UpdateUserPwdBean {

    private final String userAccount;
    private final String verificationCode;
    private final String newPassword;
    private final String confirmNewPwd;

    public UpdateUserPwdBean(String userAccount, String verificationCode, String newPassword, String confirmNewPwd) {
        this.userAccount = userAccount;
        this.verificationCode = verificationCode;
        this.newPassword = newPassword;
        this.confirmNewPwd = confirmNewPwd;
    }
}
