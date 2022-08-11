package com.avit.apnamzpadmin.network;

import com.avit.apnamzpadmin.models.deliverysathi.DeliverySathiStatus;
import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.order.OrderItem;
import com.avit.apnamzpadmin.models.review.ReviewData;
import com.avit.apnamzpadmin.models.user.UserAppDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NetworkAPI {
    public static String SERVER_URL = "http://192.168.138.85:5000/";
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
    Call<List<OrderItem>> getOrdersByPhoneNo(@Query("phoneNo") String phoneNo);

    @GET("/apna_mzp/admin/userAppData")
    Call<UserAppDetails> getUserAppDetails();

    @POST("/apna_mzp/admin/userAppData")
    Call<NetworkResponse> updateUserAppDetails(@Body UserAppDetails userAppDetails);

    @GET("/apna_mzp/admin/apna_reviews")
    Call<List<ReviewData>> getApnaReviews();

}
