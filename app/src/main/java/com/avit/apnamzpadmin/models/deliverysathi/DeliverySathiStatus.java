package com.avit.apnamzpadmin.models.deliverysathi;

import com.avit.apnamzpadmin.models.user.UserInfo;

public class DeliverySathiStatus {

    private UserInfo deliverySathi;
    private UserInfo shopData;
    private UserInfo customerData;

    public DeliverySathiStatus(UserInfo deliverySathi, UserInfo shopData, UserInfo customerData) {
        this.deliverySathi = deliverySathi;
        this.shopData = shopData;
        this.customerData = customerData;
    }

    public boolean isDeliverySathiFree(){
        return (shopData == null);
    }

    public UserInfo getDeliverySathi() {
        return deliverySathi;
    }

    public UserInfo getShopData() {
        return shopData;
    }

    public UserInfo getCustomerData() {
        return customerData;
    }
}
