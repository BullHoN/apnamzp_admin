package com.avit.apnamzpadmin.models.deliverysathi;

import com.avit.apnamzpadmin.models.user.UserInfo;

import java.util.List;

public class DeliverySathiStatus {

    private UserInfo deliverySathi;
    private List<DeliverySathiOrderDetails> orderDetailsList;
    private int currOrders;

    public DeliverySathiStatus(UserInfo deliverySathi, List<DeliverySathiOrderDetails> orderDetailsList, int currOrders) {
        this.deliverySathi = deliverySathi;
        this.orderDetailsList = orderDetailsList;
        this.currOrders = currOrders;
    }

    public int getCurrOrders() {
        return currOrders;
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
