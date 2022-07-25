package com.avit.apnamzpadmin.ui.adminshopservice;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.order.OrderItem;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.utils.ErrorUtils;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdminShopServiceViewModel extends ViewModel {

    private String TAG = "AdminShopServiceActivity";
    private MutableLiveData<List<OrderItem>> mutableLivePendingOrderItemsData;

    public AdminShopServiceViewModel(){
        mutableLivePendingOrderItemsData = new MutableLiveData<>();
    }

    public MutableLiveData<List<OrderItem>> getMutableLivePendingOrderItemsData() {
        return mutableLivePendingOrderItemsData;
    }

    public void getPendingOrders(Context context){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<List<OrderItem>> call = networkAPI.getPendingOrders();
        call.enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.success(context,errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                mutableLivePendingOrderItemsData.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<OrderItem>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toasty.error(context,t.getMessage(),Toasty.LENGTH_SHORT)
                        .show();
            }
        });
    }

}
