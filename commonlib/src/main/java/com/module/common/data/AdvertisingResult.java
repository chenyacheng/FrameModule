package com.module.common.data;

public class AdvertisingResult {

    private String promptContent;
    private String contentKey;
    private String createTime;
    private Integer managementStatus;
    private String managementKey;
    private String pictureAddress;
    private String managementTitle;

    public String getPromptContent() {
        return promptContent;
    }

    public void setPromptContent(String promptContent) {
        this.promptContent = promptContent;
    }

    public String getContentKey() {
        return contentKey;
    }

    public void setContentKey(String contentKey) {
        this.contentKey = contentKey;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getManagementStatus() {
        return managementStatus;
    }

    public void setManagementStatus(Integer managementStatus) {
        this.managementStatus = managementStatus;
    }

    public String getManagementKey() {
        return managementKey;
    }

    public void setManagementKey(String managementKey) {
        this.managementKey = managementKey;
    }

    public String getPictureAddress() {
        return pictureAddress;
    }

    public void setPictureAddress(String pictureAddress) {
        this.pictureAddress = pictureAddress;
    }

    public String getManagementTitle() {
        return managementTitle;
    }

    public void setManagementTitle(String managementTitle) {
        this.managementTitle = managementTitle;
    }
}
