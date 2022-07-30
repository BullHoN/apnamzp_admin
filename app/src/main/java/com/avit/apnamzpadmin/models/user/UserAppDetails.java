package com.avit.apnamzpadmin.models.user;

import java.util.List;

public class UserAppDetails {
    private List<BannerData> bannerImages;
    private boolean userServiceOpen;
    private int slurgeCharges;
    private String slurgeReason;
    private int itemsOnTheWayCost;

    public UserAppDetails(List<BannerData> bannerImages, boolean userServiceOpen, int slurgeCharges, String slurgeReason, int itemsOnTheWayCost) {
        this.bannerImages = bannerImages;
        this.userServiceOpen = userServiceOpen;
        this.slurgeCharges = slurgeCharges;
        this.slurgeReason = slurgeReason;
        this.itemsOnTheWayCost = itemsOnTheWayCost;
    }

    public void setBannerImages(List<BannerData> bannerImages) {
        this.bannerImages = bannerImages;
    }

    public void setSlurgeCharges(int slurgeCharges) {
        this.slurgeCharges = slurgeCharges;
    }

    public void setSlurgeReason(String slurgeReason) {
        this.slurgeReason = slurgeReason;
    }

    public void setItemsOnTheWayCost(int itemsOnTheWayCost) {
        this.itemsOnTheWayCost = itemsOnTheWayCost;
    }

    public void setUserServiceOpen(boolean userServiceOpen) {
        this.userServiceOpen = userServiceOpen;
    }

    public int getItemsOnTheWayCost() {
        return itemsOnTheWayCost;
    }

    public List<BannerData> getBannerImages() {
        return bannerImages;
    }

    public boolean isUserServiceOpen() {
        return userServiceOpen;
    }

    public int getSlurgeCharges() {
        return slurgeCharges;
    }

    public String getSlurgeReason() {
        return slurgeReason;
    }

    @Override
    public String toString() {
        return "UserAppDetails{" +
                "bannerImages=" + bannerImages +
                ", userServiceOpen=" + userServiceOpen +
                ", slurgeCharges=" + slurgeCharges +
                ", slurgeReason='" + slurgeReason + '\'' +
                ", itemsOnTheWayCost=" + itemsOnTheWayCost +
                '}';
    }
}
