package com.avit.apnamzpadmin.models.deliverysathi;

import com.avit.apnamzpadmin.models.user.UserInfo;

public class DeliverySathiOrderDetails {

    private UserInfo shopData;
    private UserInfo customerData;
    private boolean show;

    public DeliverySathiOrderDetails(UserInfo shopData, UserInfo customerData, boolean show) {
        this.shopData = shopData;
        this.customerData = customerData;
        this.show = false;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public UserInfo getShopData() {
        return shopData;
    }

    public UserInfo getCustomerData() {
        return customerData;
    }

    public boolean isShow() {
        return show;
    }
}
