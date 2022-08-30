package com.avit.apnamzpadmin.models.shop;

public class ShopData {
    private String _id;
    private String name;
    private String menuItemsID;

    public ShopData(String _id, String name, String menuItemsID) {
        this._id = _id;
        this.name = name;
        this.menuItemsID = menuItemsID;
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
