package com.avit.apnamzpadmin.models.shop;

public class ShopData {
    private String _id;
    private String name;
    private String menuItemsID;
    private String shopType;
    private boolean isOpen;
    private boolean adminShopService;
    private String phoneNO;

    public ShopData(String _id, String name, String menuItemsID, String shopType, boolean isOpen, boolean adminShopService, String phoneNO) {
        this._id = _id;
        this.name = name;
        this.menuItemsID = menuItemsID;
        this.shopType = shopType;
        this.isOpen = isOpen;
        this.adminShopService = adminShopService;
        this.phoneNO = phoneNO;
    }

    public boolean isAdminShopService() {
        return adminShopService;
    }

    public String getPhoneNO() {
        return phoneNO;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public String getShopType() {
        return shopType;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getMenuItemsID() {
        return menuItemsID;
    }
}
