package com.module.common.data;

public class RegisterBean {

    private final String userAccount;
    private final String verificationCode;
    private final String userPassword;
    private final String confirmNewPwd;
    private final String recommendCode;

    public RegisterBean(String userAccount, String verificationCode, String userPassword, String confirmNewPwd, String recommendCode) {
        this.userAccount = userAccount;
        this.verificationCode = verificationCode;
        this.userPassword = userPassword;
        this.confirmNewPwd = confirmNewPwd;
        this.recommendCode = recommendCode;
    }
}
