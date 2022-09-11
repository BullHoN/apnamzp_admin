package com.avit.apnamzpadmin.models.notification;

public class BulkNotificationPostBody {
    private String title;
    private String desc;
    private String shopId;
    private String imageUrl;
    private String targetGroup;

    public BulkNotificationPostBody(){

    }

    public BulkNotificationPostBody(String title, String desc, String shopId, String imageUrl, String targetGroup) {
        this.title = title;
        this.desc = desc;
        this.shopId = shopId;
        this.imageUrl = imageUrl;
        this.targetGroup = targetGroup;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTargetGroup(String targetGroup) {
        this.targetGroup = targetGroup;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getShopId() {
        return shopId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTargetGroup() {
        return targetGroup;
    }
}
