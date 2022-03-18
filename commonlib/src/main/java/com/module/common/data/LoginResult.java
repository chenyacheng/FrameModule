package com.module.common.data;

public class LoginResult {

    private String userPassword;
    private Integer userStatus;
    private String userPortrait;
    private String createTime;
    private String recommendKey;
    private String userAccount;
    private String userDetailsKey;
    private String sessionId;
    private String userNickName;
    private Integer userType;
    private String recommendCode;
    private Integer isReal;
    private String realIdCard;
    private String realName;

    /***************废弃****************/
    private String sessionTime;
    private String updateTime;

    public Integer getIsReal() {
        return isReal;
    }

    public void setIsReal(Integer isReal) {
        this.isReal = isReal;
    }

    public String getRealIdCard() {
        return realIdCard;
    }

    public void setRealIdCard(String realIdCard) {
        this.realIdCard = realIdCard;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }

    public String getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserPortrait() {
        return userPortrait;
    }

    public void setUserPortrait(String userPortrait) {
        this.userPortrait = userPortrait;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRecommendKey() {
        return recommendKey;
    }

    public void setRecommendKey(String recommendKey) {
        this.recommendKey = recommendKey;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserDetailsKey() {
        return userDetailsKey;
    }

    public void setUserDetailsKey(String userDetailsKey) {
        this.userDetailsKey = userDetailsKey;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
