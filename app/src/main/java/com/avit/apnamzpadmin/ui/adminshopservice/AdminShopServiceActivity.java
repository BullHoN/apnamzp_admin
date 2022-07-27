package com.avit.apnamzpadmin.ui.adminshopservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.order.OrderItem;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.ui.deliverysathistatus.DeliverySathisStatusActivity;
import com.avit.apnamzpadmin.utils.ErrorUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdminShopServiceActivity extends AppCompatActivity implements OrdersAdapter.OrdersActions{

    private String TAG = "AdminShopServiceActivity";
    private AdminShopServiceViewModel viewModel;
    private RecyclerView pendingOrderItems;
    private OrdersAdapter ordersAdapter;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_shop_service);
        viewModel = new ViewModelProvider(this).get(AdminShopServiceViewModel.class);
        gson = new Gson();

        viewModel.getPendingOrders(getApplicationContext());

        pendingOrderItems = findViewById(R.id.pending_orders_list);
        pendingOrderItems.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        ordersAdapter = new OrdersAdapter(new ArrayList<>(),this,this);

        pendingOrderItems.setAdapter(ordersAdapter);

        viewModel.getMutableLivePendingOrderItemsData().observe(this, new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> orderItems) {
                ordersAdapter.updateItems(orderItems);
            }
        });



    }

    @Override
    public void assignDeliverySathi(OrderItem orderItem) {
        Intent deliverySathiStatusIntent = new Intent(this, DeliverySathisStatusActivity.class);
        deliverySathiStatusIntent.putExtra("orderItem", gson.toJson(orderItem));
        startActivity(deliverySathiStatusIntent);
    }

    @Override
    public void addDeliverySathiIncome(String orderId, String totalCost) {
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<NetworkResponse> call = networkAPI.addDeliverySathiIncome(orderId,totalCost);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getApplicationContext(),errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                Toasty.success(getApplicationContext(),"Update Successfull",Toasty.LENGTH_SHORT)
                        .show();

            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    @Override
    public void cancelOrder(String orderId,String cancelReason) {
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<NetworkResponse> call = networkAPI.cancelOrder(orderId,cancelReason);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                if (!response.isSuccessful()) {
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getApplicationContext(), errorResponse.getDesc(), Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                Toasty.success(getApplicationContext(), "Update Successfull", Toasty.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getPendingOrders(getApplicationContext());
    }
}