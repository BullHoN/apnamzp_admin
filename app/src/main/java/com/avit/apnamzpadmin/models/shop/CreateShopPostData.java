package com.avit.apnamzpadmin.models.shop;

public class CreateShopPostData {
    private String shopName;
    private String shopPhoneNumber;
    private String shopPassword;
    private String shopType;
    private ShopAddressData addressData;
    private boolean adminShopService;

    public CreateShopPostData(String shopName, String shopPhoneNumber, String shopPassword, String shopType, ShopAddressData addressData, boolean adminShopService) {
        this.shopName = shopName;
        this.shopPhoneNumber = shopPhoneNumber;
        this.shopPassword = shopPassword;
        this.shopType = shopType;
        this.addressData = addressData;
        this.adminShopService = adminShopService;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopPhoneNumber() {
        return shopPhoneNumber;
    }

    public String getShopPassword() {
        return shopPassword;
    }

    public String getShopType() {
        return shopType;
    }

    public ShopAddressData getAddressData() {
        return addressData;
    }

    public boolean isAdminShopService() {
        return adminShopService;
    }
}
