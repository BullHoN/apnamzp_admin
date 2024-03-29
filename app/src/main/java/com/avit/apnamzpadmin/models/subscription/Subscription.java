package com.avit.apnamzpadmin.models.subscription;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Subscription {

    @SerializedName("_id")
    private String id;
    private boolean isPaid;
    private Date startDate;
    private Date endDate;
    private boolean isFree;
    private int totalEarning;
    private int directOrderEarning;
    private List<SubscriptionPricings> subscriptionPricings;
    private String paymentId;
    private int amount;
    private int newPlanPrice;
    private boolean createNewPlan;

    public Subscription(String id, String paymentId, int amount, boolean createNewPlan){
        this.id = id;
        this.paymentId  = paymentId;
        this.amount = amount;
        this.createNewPlan = createNewPlan;
    }

    public Subscription(String id, boolean isPaid, Date startDate, Date endDate, boolean isFree, int totalEarning, int directOrderEarning, List<SubscriptionPricings> subscriptionPricings, String paymentId, int amount, int newPlanPrice, boolean createNewPlan) {
        this.id = id;
        this.isPaid = isPaid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFree = isFree;
        this.totalEarning = totalEarning;
        this.directOrderEarning = directOrderEarning;
        this.subscriptionPricings = subscriptionPricings;
        this.paymentId = paymentId;
        this.amount = amount;
        this.newPlanPrice = newPlanPrice;
        this.createNewPlan = createNewPlan;
    }

    public Subscription(String id, boolean isPaid, Date startDate, Date endDate, boolean isFree, int totalEarning, List<SubscriptionPricings> subscriptionPricings, String paymentId, int amount, int newPlanPrice) {
        this.id = id;
        this.isPaid = isPaid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFree = isFree;
        this.totalEarning = totalEarning;
        this.subscriptionPricings = subscriptionPricings;
        this.paymentId = paymentId;
        this.amount = amount;
        this.newPlanPrice = newPlanPrice;
    }

    public int getTotalAppSales(){
        return totalEarning-directOrderEarning;
    }

    public int getDirectOrderEarning() {
        return directOrderEarning;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isCreateNewPlan() {
        return createNewPlan;
    }

    public int getNewPlanPrice() {
        return newPlanPrice;
    }

    public int getAmount() {
        return amount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public int getTotalEarning() {
        return totalEarning;
    }

    public List<SubscriptionPricings> getSubscriptionPricings() {
        return subscriptionPricings;
    }

    public String getId() {
        return id;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isFree() {
        return isFree;
    }
}
