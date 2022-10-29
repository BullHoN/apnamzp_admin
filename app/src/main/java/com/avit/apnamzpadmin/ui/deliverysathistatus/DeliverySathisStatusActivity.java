package com.avit.apnamzpadmin.ui.deliverysathistatus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.deliverysathi.DeliverySathiStatus;
import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.order.OrderItem;
import com.avit.apnamzpadmin.models.user.UserInfo;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.utils.ErrorUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DeliverySathisStatusActivity extends AppCompatActivity implements DeliverySathiStatusAdapter.DeliverySathiStatusActions{

    private String TAG = "DeliverySathisStatusActivity";
    private RecyclerView deliverySathisRecyclerView;
    private DeliverySathisStatusViewModel viewModel;
    private DeliverySathiStatusAdapter deliverySathiStatusAdapter;
    private Gson gson;
    private OrderItem currOrderItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_sathis_status);

        viewModel = new ViewModelProvider(this).get(DeliverySathisStatusViewModel.class);
        viewModel.getDeliverySathisStatus(getApplicationContext());
        gson = new Gson();

        if(getIntent() != null && getIntent().getStringExtra("orderItem") != null){
            String orderItemString = getIntent().getStringExtra("orderItem");
            currOrderItem = gson.fromJson(orderItemString,OrderItem.class);
        }

        deliverySathisRecyclerView = findViewById(R.id.delivery_sathis);
        deliverySathisRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        deliverySathiStatusAdapter = new DeliverySathiStatusAdapter(new ArrayList<>(),this, (currOrderItem != null),this);
        deliverySathisRecyclerView.setAdapter(deliverySathiStatusAdapter);

        viewModel.getMutableLiveDeliverySathiStatusData().observe(this, new Observer<List<DeliverySathiStatus>>() {
            @Override
            public void onChanged(List<DeliverySathiStatus> deliverySathiStatuses) {
                deliverySathiStatusAdapter.updateItems(deliverySathiStatuses);
            }
        });

    }

    @Override
    public void assignDeliverySathi(String deliverySathiPhoneNo) {
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<NetworkResponse> call = networkAPI.assignDeliverySathi(currOrderItem.get_id(),deliverySathiPhoneNo);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getApplicationContext(),errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                finish();
            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    @Override
    public void changeCurrOrder(int newCurrOrder, UserInfo userInfo) {
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        userInfo.setCurrOrders(newCurrOrder);

        Call<NetworkResponse> call = networkAPI.updateDeliverySathi(userInfo);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getApplicationContext(),errorResponse.getDesc(),Toasty.LENGTH_LONG)
                            .show();
                    return;
                }

                Toasty.success(getApplicationContext(),"Update Sucessfull",Toasty.LENGTH_LONG)
                        .show();

            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                Toasty.error(getApplicationContext(),t.getMessage(),Toasty.LENGTH_LONG)
                        .show();
            }
        });

    }
}