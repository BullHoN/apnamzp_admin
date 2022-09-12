package com.avit.apnamzpadmin.models.order;

import com.avit.apnamzpadmin.models.shop.ShopItemData;
import com.avit.apnamzpadmin.models.user.UserInfo;

import java.util.List;

public class OrderItem {

    private String _id;
    private List<ShopItemData> orderItems;
    private UserInfo shopInfo;
    private UserInfo userInfo;
    private Integer orderStatus;
    private List<String> itemsOnTheWay;
    private boolean itemsOnTheWayVisible;
    private Integer totalAmountToTake, totalAmountToGive;
    private boolean isPaid;
    private boolean itemsOnTheWayCancelled;
    private int itemsOnTheWayActualCost;
    private boolean orderAcceptedByDeliverySathi;
    private BillingDetails billingDetails;
    private String offerCode;
    private String shopID;
    private String userId;
    private UserInfo deliveryAddress;
    private String expectedDeliveryTime;
    private Boolean paymentReceivedToShop;
    private String actualDistance;
    private boolean adminShopService;
    private String shopCategory;

    public OrderItem(){

    }

    public OrderItem(String _id, List<ShopItemData> orderItems, UserInfo shopInfo, UserInfo userInfo, Integer orderStatus, List<String> itemsOnTheWay, boolean itemsOnTheWayVisible, Integer totalAmountToTake, Integer totalAmountToGive, boolean isPaid, boolean itemsOnTheWayCancelled, int itemsOnTheWayActualCost, boolean orderAcceptedByDeliverySathi, BillingDetails billingDetails, String offerCode) {
        this._id = _id;
        this.orderItems = orderItems;
        this.shopInfo = shopInfo;
        this.userInfo = userInfo;
        this.orderStatus = orderStatus;
        this.itemsOnTheWay = itemsOnTheWay;
        this.itemsOnTheWayVisible = itemsOnTheWayVisible;
        this.totalAmountToTake = totalAmountToTake;
        this.totalAmountToGive = totalAmountToGive;
        this.isPaid = isPaid;
        this.itemsOnTheWayCancelled = itemsOnTheWayCancelled;
        this.itemsOnTheWayActualCost = itemsOnTheWayActualCost;
        this.orderAcceptedByDeliverySathi = orderAcceptedByDeliverySathi;
        this.billingDetails = billingDetails;
        this.offerCode = offerCode;
    }

    public OrderItem(String _id, List<ShopItemData> orderItems, UserInfo shopInfo, UserInfo userInfo, Integer orderStatus, List<String> itemsOnTheWay, boolean itemsOnTheWayVisible, Integer totalAmountToTake, Integer totalAmountToGive, boolean isPaid, boolean itemsOnTheWayCancelled, int itemsOnTheWayActualCost, boolean orderAcceptedByDeliverySathi, BillingDetails billingDetails) {
        this._id = _id;
        this.orderItems = orderItems;
        this.shopInfo = shopInfo;
        this.userInfo = userInfo;
        this.orderStatus = orderStatus;
        this.itemsOnTheWay = itemsOnTheWay;
        this.itemsOnTheWayVisible = itemsOnTheWayVisible;
        this.totalAmountToTake = totalAmountToTake;
        this.totalAmountToGive = totalAmountToGive;
        this.isPaid = isPaid;
        this.itemsOnTheWayCancelled = itemsOnTheWayCancelled;
        this.itemsOnTheWayActualCost = itemsOnTheWayActualCost;
        this.orderAcceptedByDeliverySathi = orderAcceptedByDeliverySathi;
        this.billingDetails = billingDetails;
    }

    public OrderItem(String _id, List<ShopItemData> orderItems, UserInfo shopInfo, UserInfo userInfo, Integer orderStatus, List<String> itemsOnTheWay, boolean itemsOnTheWayVisible, Integer totalAmountToTake, Integer totalAmountToGive, boolean isPaid, boolean itemsOnTheWayCancelled, int itemsOnTheWayActualCost, boolean orderAcceptedByDeliverySathi) {
        this._id = _id;
        this.orderItems = orderItems;
        this.shopInfo = shopInfo;
        this.userInfo = userInfo;
        this.orderStatus = orderStatus;
        this.itemsOnTheWay = itemsOnTheWay;
        this.itemsOnTheWayVisible = itemsOnTheWayVisible;
        this.totalAmountToTake = totalAmountToTake;
        this.totalAmountToGive = totalAmountToGive;
        this.isPaid = isPaid;
        this.itemsOnTheWayCancelled = itemsOnTheWayCancelled;
        this.itemsOnTheWayActualCost = itemsOnTheWayActualCost;
        this.orderAcceptedByDeliverySathi = orderAcceptedByDeliverySathi;
    }

    public OrderItem(String _id, List<ShopItemData> orderItems, UserInfo shopInfo, UserInfo userInfo, Integer orderStatus, List<String> itemsOnTheWay, boolean itemsOnTheWayVisible, Integer totalAmountToTake, Integer totalAmountToGive, boolean isPaid, boolean itemsOnTheWayCancelled, int itemsOnTheWayActualCost) {
        this._id = _id;
        this.orderItems = orderItems;
        this.shopInfo = shopInfo;
        this.userInfo = userInfo;
        this.orderStatus = orderStatus;
        this.itemsOnTheWay = itemsOnTheWay;
        this.itemsOnTheWayVisible = itemsOnTheWayVisible;
        this.totalAmountToTake = totalAmountToTake;
        this.totalAmountToGive = totalAmountToGive;
        this.isPaid = isPaid;
        this.itemsOnTheWayCancelled = itemsOnTheWayCancelled;
        this.itemsOnTheWayActualCost = itemsOnTheWayActualCost;
    }

    public OrderItem(String _id, List<ShopItemData> orderItems, UserInfo shopInfo, UserInfo userInfo, Integer orderStatus, List<String> itemsOnTheWay, boolean itemsOnTheWayVisible, Integer totalAmountToTake, Integer totalAmountToGive, boolean isPaid, boolean itemsOnTheWayCancelled) {
        this._id = _id;
        this.orderItems = orderItems;
        this.shopInfo = shopInfo;
        this.userInfo = userInfo;
        this.orderStatus = orderStatus;
        this.itemsOnTheWay = itemsOnTheWay;
        this.itemsOnTheWayVisible = itemsOnTheWayVisible;
        this.totalAmountToTake = totalAmountToTake;
        this.totalAmountToGive = totalAmountToGive;
        this.isPaid = isPaid;
        this.itemsOnTheWayCancelled = itemsOnTheWayCancelled;
    }

    public OrderItem(String _id, List<ShopItemData> orderItems, UserInfo shopInfo, UserInfo userInfo, Integer orderStatus, List<String> itemsOnTheWay, boolean itemsOnTheWayVisible, Integer totalAmountToTake, Integer totalAmountToGive, boolean isPaid) {
        this._id = _id;
        this.orderItems = orderItems;
        this.shopInfo = shopInfo;
        this.userInfo = userInfo;
        this.orderStatus = orderStatus;
        this.itemsOnTheWay = itemsOnTheWay;
        this.itemsOnTheWayVisible = itemsOnTheWayVisible;
        this.totalAmountToTake = totalAmountToTake;
        this.totalAmountToGive = totalAmountToGive;
        this.isPaid = isPaid;
    }

    public OrderItem(String _id, List<ShopItemData> orderItems, UserInfo shopInfo, UserInfo userInfo, Integer orderStatus, List<String> itemsOnTheWay, boolean itemsOnTheWayVisible, Integer totalAmountToTake, Integer totalAmountToGive) {
        this._id = _id;
        this.orderItems = orderItems;
        this.shopInfo = shopInfo;
        this.userInfo = userInfo;
        this.orderStatus = orderStatus;
        this.itemsOnTheWay = itemsOnTheWay;
        this.itemsOnTheWayVisible = itemsOnTheWayVisible;
        this.totalAmountToTake = totalAmountToTake;
        this.totalAmountToGive = totalAmountToGive;
    }

    public OrderItem(String _id, List<ShopItemData> orderItems, UserInfo shopInfo, UserInfo userInfo, Integer orderStatus, List<String> itemsOnTheWay, boolean itemsOnTheWayVisible, Integer totalAmountToTake) {
        this._id = _id;
        this.orderItems = orderItems;
        this.shopInfo = shopInfo;
        this.userInfo = userInfo;
        this.orderStatus = orderStatus;
        this.itemsOnTheWay = itemsOnTheWay;
        this.itemsOnTheWayVisible = itemsOnTheWayVisible;
        this.totalAmountToTake = totalAmountToTake;
    }

    public OrderItem(String _id, List<ShopItemData> orderItems, UserInfo shopInfo, UserInfo userInfo, Integer orderStatus, List<String> itemsOnTheWay) {
        this._id = _id;
        this.orderItems = orderItems;
        this.shopInfo = shopInfo;
        this.userInfo = userInfo;
        this.orderStatus = orderStatus;
        this.itemsOnTheWay = itemsOnTheWay;
        this.itemsOnTheWayVisible = false;
    }

    public OrderItem(String _id, List<ShopItemData> orderItems, UserInfo shopInfo, UserInfo userInfo) {
        this._id = _id;
        this.orderItems = orderItems;
        this.shopInfo = shopInfo;
        this.userInfo = userInfo;
    }

    public OrderItem(String _id, List<ShopItemData> orderItems, UserInfo shopInfo, UserInfo userInfo, Integer orderStatus) {
        this._id = _id;
        this.orderItems = orderItems;
        this.shopInfo = shopInfo;
        this.userInfo = userInfo;
        this.orderStatus = orderStatus;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public String getActualDistance() {
        return actualDistance;
    }

    public void setAdminShopService(boolean adminShopService) {
        this.adminShopService = adminShopService;
    }

    public boolean isAdminShopService() {
        return adminShopService;
    }

    public void setActualDistance(String actualDistance) {
        this.actualDistance = actualDistance;
    }

    public void setPaymentReceivedToShop(Boolean paymentReceivedToShop) {
        this.paymentReceivedToShop = paymentReceivedToShop;
    }

    public Boolean getPaymentReceivedToShop() {
        return paymentReceivedToShop;
    }

    public String getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(String expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }

    public void setDeliveryAddress(UserInfo deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public UserInfo getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getShopID() {
        return shopID;
    }

    public String getUserId() {
        return userId;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setOrderItems(List<ShopItemData> orderItems) {
        this.orderItems = orderItems;
    }

    public void setShopInfo(UserInfo shopInfo) {
        this.shopInfo = shopInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setItemsOnTheWay(List<String> itemsOnTheWay) {
        this.itemsOnTheWay = itemsOnTheWay;
    }

    public void setTotalAmountToTake(Integer totalAmountToTake) {
        this.totalAmountToTake = totalAmountToTake;
    }

    public void setTotalAmountToGive(Integer totalAmountToGive) {
        this.totalAmountToGive = totalAmountToGive;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void setItemsOnTheWayCancelled(boolean itemsOnTheWayCancelled) {
        this.itemsOnTheWayCancelled = itemsOnTheWayCancelled;
    }

    public void setItemsOnTheWayActualCost(int itemsOnTheWayActualCost) {
        this.itemsOnTheWayActualCost = itemsOnTheWayActualCost;
    }

    public void setOrderAcceptedByDeliverySathi(boolean orderAcceptedByDeliverySathi) {
        this.orderAcceptedByDeliverySathi = orderAcceptedByDeliverySathi;
    }

    public void setBillingDetails(BillingDetails billingDetails) {
        this.billingDetails = billingDetails;
    }

    public void setOfferCode(String offerCode) {
        this.offerCode = offerCode;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public BillingDetails getBillingDetails() {
        return billingDetails;
    }

    public boolean isOrderAcceptedByDeliverySathi() {
        return orderAcceptedByDeliverySathi;
    }

    public int getItemsOnTheWayActualCost() {
        return itemsOnTheWayActualCost;
    }

    public boolean isItemsOnTheWayCancelled() {
        return itemsOnTheWayCancelled;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public Integer getTotalAmountToGive() {
        return totalAmountToGive;
    }

    public Integer getTotalAmountToTake() {
        return totalAmountToTake;
    }

    public boolean isItemsOnTheWayVisible() {
        return itemsOnTheWayVisible;
    }

    public void setItemsOnTheWayVisible(boolean itemsOnTheWayVisible) {
        this.itemsOnTheWayVisible = itemsOnTheWayVisible;
    }

    public List<String> getItemsOnTheWay() {
        return itemsOnTheWay;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public String get_id() {
        return _id;
    }

    public List<ShopItemData> getOrderItems() {
        return orderItems;
    }

    public UserInfo getShopInfo() {
        return shopInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}