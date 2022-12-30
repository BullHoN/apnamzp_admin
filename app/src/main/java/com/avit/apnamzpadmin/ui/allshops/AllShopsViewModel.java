package com.avit.apnamzpadmin.ui.allshops;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.shop.ShopData;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.utils.ErrorUtils;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AllShopsViewModel extends ViewModel {

    private MutableLiveData<List<ShopData>> mutableShopLiveData;

    public AllShopsViewModel(){
        mutableShopLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<ShopData>> getMutableShopLiveData() {
        return mutableShopLiveData;
    }

    public void getShops(Context context){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<List<ShopData>> call = networkAPI.getAllShops();
        call.enqueue(new Callback<List<ShopData>>() {
            @Override
            public void onResponse(Call<List<ShopData>> call, Response<List<ShopData>> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(context,errorResponse.getDesc(),Toasty.LENGTH_LONG)
                            .show();
                    return;
                }

                mutableShopLiveData.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<ShopData>> call, Throwable t) {
                Toasty.error(context,t.getMessage(),Toasty.LENGTH_LONG)
                        .show();
            }
        });

    }

}
