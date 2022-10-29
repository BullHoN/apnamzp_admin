package com.avit.apnamzpadmin.ui.shopdataservice;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.shop.ShopData;
import com.avit.apnamzpadmin.models.subscription.Subscription;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.utils.ErrorUtils;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShopDataViewModel extends ViewModel {

    private MutableLiveData<List<ShopData>> mutableShopLiveData;
    private MutableLiveData<Subscription> mutableSubscriptionLiveData;


    public ShopDataViewModel(){
        mutableShopLiveData = new MutableLiveData<>();
        mutableSubscriptionLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Subscription> getMutableSubscriptionLiveData() {
        return mutableSubscriptionLiveData;
    }

    public MutableLiveData<List<ShopData>> getMutableShopLiveData() {
        return mutableShopLiveData;
    }

    public void searchShop(Context context, String shopName){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<List<ShopData>> call = networkAPI.searchShop(shopName);
        call.enqueue(new Callback<List<ShopData>>() {
            @Override
            public void onResponse(Call<List<ShopData>> call, Response<List<ShopData>> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(context,errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                mutableShopLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ShopData>> call, Throwable t) {
                Toasty.error(context,t.getMessage(),Toasty.LENGTH_SHORT)
                        .show();
            }
        });

    }

    public void getActiveSubscription(Context context, String shopId){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkApi = retrofit.create(NetworkAPI.class);

        Call<Subscription> call = networkApi.getSubscription(shopId);
        call.enqueue(new Callback<Subscription>() {
            @Override
            public void onResponse(Call<Subscription> call, Response<Subscription> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(context,errorResponse.getDesc(),Toasty.LENGTH_LONG)
                            .show();
                    return;
                }

                mutableSubscriptionLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Subscription> call, Throwable t) {
                Toasty.error(context,t.getMessage(),Toasty.LENGTH_LONG)
                        .show();
            }
        });

    }


}
