package com.avit.apnamzpadmin.ui.editshop;

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

public class EditShopViewModel extends ViewModel {

    private MutableLiveData<List<ShopData>> mutableShopLiveData;

    public EditShopViewModel(){
        mutableShopLiveData = new MutableLiveData<>();
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


}
