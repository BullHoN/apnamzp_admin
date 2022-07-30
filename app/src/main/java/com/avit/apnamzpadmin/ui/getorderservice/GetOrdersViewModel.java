package com.avit.apnamzpadmin.ui.getorderservice;

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

public class GetOrdersViewModel extends ViewModel {

    private String TAG = "GetOrdersActivity";
    private MutableLiveData<List<OrderItem>> mutableLiveData;

    public GetOrdersViewModel() {
        this.mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<OrderItem>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void getOrders(Context context, String phoneNo){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<List<OrderItem>> call = networkAPI.getOrdersByPhoneNo(phoneNo);
        call.enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(context,errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                mutableLiveData.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<OrderItem>> call, Throwable t) {
                Log.i(TAG, "onFailure: ",t);
            }
        });

    }

}
