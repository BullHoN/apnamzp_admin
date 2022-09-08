package com.avit.apnamzpadmin.models.user;

public class BannerData {
    private String imageURL;
    private String action;
    private String shopId;
    private String newImageUrl;

    public BannerData(){

    }

    public BannerData(String imageURL, String action, String shopId) {
        this.imageURL = imageURL;
        this.action = action;
        this.shopId = shopId;
    }

    public BannerData(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setNewImageUrl(String newImageUrl) {
        this.newImageUrl = newImageUrl;
    }

    public String getNewImageUrl() {
        return newImageUrl;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getAction() {
        return action;
    }

    public String getShopId() {
        return shopId;
    }

    public String getImageURL() {
        return imageURL;
    }
}
