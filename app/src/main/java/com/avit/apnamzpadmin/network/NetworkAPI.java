package com.avit.apnamzpadmin.network;

import com.avit.apnamzpadmin.models.deliverysathi.DeliverySathiStatus;
import com.avit.apnamzpadmin.models.order.OrderItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NetworkAPI {
    public static String SERVER_URL = "http://192.168.63.85:5000/";

    @GET("/apna_mzp/admin/pendingOrders")
    Call<List<OrderItem>> getPendingOrders();

    @GET("/apna_mzp/admin/delivery_sathis")
    Call<List<DeliverySathiStatus>> getDeliverySathiStatus();

}
