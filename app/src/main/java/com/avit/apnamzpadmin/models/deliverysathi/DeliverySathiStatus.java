package com.avit.apnamzpadmin.models.deliverysathi;

import com.avit.apnamzpadmin.models.user.UserInfo;

import java.util.List;

public class DeliverySathiStatus {

    private UserInfo deliverySathi;
    private List<DeliverySathiOrderDetails> orderDetailsList;

    public DeliverySathiStatus(UserInfo deliverySathi, List<DeliverySathiOrderDetails> orderDetailsList) {
        this.deliverySathi = deliverySathi;
        this.orderDetailsList = orderDetailsList;
    }

    public boolean isDeliverySathiFree(){
        return orderDetailsList == null || orderDetailsList.size() == 0;
    }

    public List<DeliverySathiOrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public UserInfo getDeliverySathi() {
        return deliverySathi;
    }

}
