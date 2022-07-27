package com.avit.apnamzpadmin.network;

import com.avit.apnamzpadmin.models.deliverysathi.DeliverySathiStatus;
import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.order.OrderItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NetworkAPI {
    public static String SERVER_URL = "http://192.168.63.85:5000/";
//    String SERVER_URL = "https://2ba0-2409-4063-2109-67d5-58fa-eac6-db57-6edd.ngrok.io";

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


}
