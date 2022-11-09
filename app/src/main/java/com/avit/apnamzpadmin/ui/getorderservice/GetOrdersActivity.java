package com.avit.apnamzpadmin.ui.getorderservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.order.OrderItem;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.ui.adminshopservice.OrdersAdapter;
import com.avit.apnamzpadmin.ui.deliverysathistatus.DeliverySathisStatusActivity;
import com.avit.apnamzpadmin.utils.ErrorUtils;
import com.google.gson.Gson;

import java.lang.reflect.GenericSignatureFormatError;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetOrdersActivity extends AppCompatActivity implements OrdersAdapter.OrdersActions{

    private String TAG = "GetOrdersActivityTag";
    private GetOrdersViewModel viewModel;
    private OrdersAdapter ordersAdapter;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_orders);

        gson = new Gson();
        viewModel = new ViewModelProvider(this).get(GetOrdersViewModel.class);

        if(getIntent() != null && getIntent().getStringExtra("orderId") != null){
            String orderId = getIntent().getStringExtra("orderId");
            viewModel.getOrders(getApplicationContext(),null,orderId);
        }
        else {
            viewModel.getOrders(getApplicationContext(),null,null);
        }

        SearchView searchView =  findViewById(R.id.search_orders);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG, "onQueryTextSubmit: " + query);
                viewModel.getOrders(getApplicationContext(),query,null);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        RecyclerView recyclerView = findViewById(R.id.searched_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        ordersAdapter = new OrdersAdapter(new ArrayList<>(),this,this);
        recyclerView.setAdapter(ordersAdapter);

        viewModel.getMutableLiveData().observe(this, new Observer<List<OrderItem>>() {
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
    public void cancelOrder(String orderId, String cancelReason) {
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
}