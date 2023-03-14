package com.avit.apnamzpadmin.network;

import com.avit.apnamzpadmin.models.admin.ServiceStatus;
import com.avit.apnamzpadmin.models.deliverysathi.DeliverySathiStatus;
import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.order.OrderItem;
import com.avit.apnamzpadmin.models.review.ReviewData;
import com.avit.apnamzpadmin.models.shop.CreateShopPostData;
import com.avit.apnamzpadmin.models.shop.ShopData;
import com.avit.apnamzpadmin.models.shop.ShopItemData;
import com.avit.apnamzpadmin.models.subscription.Subscription;
import com.avit.apnamzpadmin.models.user.BannerData;
import com.avit.apnamzpadmin.models.user.UserAppDetails;
import com.avit.apnamzpadmin.models.user.UserInfo;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkAPI {
     String SERVER_URL = "http://192.168.1.4:5000/";
//    String SERVER_URL = "https://apnamzp.in/";

    @GET("/apna_mzp/admin/pendingOrders")
    Call<List<OrderItem>> getPendingOrders();

    @GET("/apna_mzp/admin/delivery_sathis")
    Call<List<DeliverySathiStatus>> getDeliverySathiStatus();

    @POST("/apna_mzp/admin/assign_delivery_sathi")
    Call<NetworkResponse> assignDeliverySathi(@Query("orderId") String orderId, @Query("deliverySathiNo") String deliverySathiNo);

    @POST("/apna_mzp/admin/addDeliverySathiIncome")
    Call<NetworkResponse> addDeliverySathiIncome(@Query("orderId") String orderId, @Query("deliverySathiIncome") String deliverySathiIncome);

    @POST("/apna_mzp/admin/cancelOrder")
    Call<NetworkResponse> cancelOrder(@Query("orderId") String orderId, @Query("cancelReason") String cancelReason);

    @GET("/apna_mzp/admin/orders")
    Call<List<OrderItem>> getOrdersByPhoneNo(@Query("phoneNo") String phoneNo,@Query("orderId") String orderId);

    @GET("/apna_mzp/admin/userAppData")
    Call<UserAppDetails> getUserAppDetails();

    @POST("/apna_mzp/admin/userAppData")
    Call<NetworkResponse> updateUserAppDetails(@Body UserAppDetails userAppDetails);

    @GET("/apna_mzp/admin/apna_reviews")
    Call<List<ReviewData>> getApnaReviews();

    @GET("/apna_mzp/admin/search_shop")
    Call<List<ShopData>> searchShop(@Query("shopName") String shopName);

    @POST("/apna_mzp/admin/createShop")
    Call<NetworkResponse> createShop(@Body CreateShopPostData shopPostData);

    @POST("/apna_mzp/admin/closeAllShops")
    Call<NetworkResponse> closeAllShops();

    @GET("/apna_mzp/admin/shopItems/{shopMenuItemsId}")
    Call<List<ShopItemData>> getShopMenuItems(@Path("shopMenuItemsId") String shopMenuItemsId);

    @GET("/user/bannerImages")
    Call<List<BannerData>> getmainScreenbannerImages();

    @Multipart
    @POST("/apna_mzp/admin/bannerImages")
    Call<NetworkResponse> updateBannerImages(@Part MultipartBody.Part bannerImage,
                                             @Part("bannerData") RequestBody bannerData,
                                             @Query("action") String action);

    @Multipart
    @POST("/apna_mzp/admin/sendBulkNotification")
    Call<NetworkResponse> sendBulkNotification(@Part MultipartBody.Part notificationImage,
                                               @Part("notificationData") RequestBody notificationData,
                                               @Query("test_notification_number") String testNotificationNumber);


    @POST("/apna_mzp/admin/direct-order")
    Call<NetworkResponse> createDirectOrder(@Body OrderItem orderItem);

    @POST("/partner/changeShopStatus")
    Call<NetworkResponse> changeShopStatus(@Query("phoneNo") String phoneNo, @Query("isOpen") boolean isOpen, @Query("adminShopService") boolean isAdminShopService);

    @GET("/user/serviceStatus")
    Call<ServiceStatus> getServiceStatus();

    @POST("/apna_mzp/admin/service-status")
    Call<NetworkResponse> changeServiceStatus(@Body ServiceStatus serviceStatus);

    @POST("/apna_mzp/admin/update-delivery-sathi")
    Call<NetworkResponse> updateDeliverySathi(@Body UserInfo userInfo);

    @GET("/partner/subscription/{shopId}")
    Call<Subscription> getSubscription(@Path("shopId") String shopId);

    @GET("/apna_mzp/admin/all-shops")
    Call<List<ShopData>> getAllShops();

    @POST("/apna_mzp/admin/update/subscription")
    Call<NetworkResponse> updateSubscription(@Body Subscription subscription);

    @POST("/apna_mzp/admin/order/updateStatus")
    Call<NetworkResponse> updateOrderStatus(@Body OrderItem orderItem);

    @POST("/apna_mzp/admin/toogle_checkout")
    Call<NetworkResponse> toggleShopCheckout(@Body ShopData shopData, @Query("allShops") boolean allShops);

}
