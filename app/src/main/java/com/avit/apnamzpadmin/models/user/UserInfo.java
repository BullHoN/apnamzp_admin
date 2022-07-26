package com.avit.apnamzpadmin.models.user;

public class UserInfo {
    private String name;
    private String latitude;
    private String longitude;
    private String phoneNo;
    private String rawAddress;
    private boolean isVissible;
    private int currOrders;
    private String landmark;
    private String houseNo;
    private int cashInHand;

    public UserInfo(String name, String latitude, String longitude, String phoneNo, String rawAddress, boolean isVissible, int currOrders, String landmark, String houseNo, int cashInHand) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNo = phoneNo;
        this.rawAddress = rawAddress;
        this.isVissible = isVissible;
        this.currOrders = currOrders;
        this.landmark = landmark;
        this.houseNo = houseNo;
        this.cashInHand = cashInHand;
    }


    public UserInfo(String name, String latitude, String longitude, String phoneNo, String rawAddress) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNo = phoneNo;
        this.rawAddress = rawAddress;
        this.isVissible = false;
    }

    public int getCashInHand() {
        return cashInHand;
    }

    public void setCashInHand(int cashInHand) {
        this.cashInHand = cashInHand;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setCurrOrders(int currOrders) {
        this.currOrders = currOrders;
    }

    public int getCurrOrders() {
        return currOrders;
    }

    public boolean isVissible() {
        return isVissible;
    }

    public void setVissible(boolean vissible) {
        isVissible = vissible;
    }

    public String getName() {
        return name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getRawAddress() {
        return rawAddress;
    }
}
