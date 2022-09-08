package com.avit.apnamzpadmin.ui.bannerimageservice;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.user.BannerData;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.utils.ErrorUtils;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BannerImagesViewModel extends ViewModel {

    private MutableLiveData<List<BannerData>> mutableBannerLiveData;

    public BannerImagesViewModel(){
        mutableBannerLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<BannerData>> getMutableBannerLiveData() {
        return mutableBannerLiveData;
    }

    public void getBannerImages(Context context){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<List<BannerData>> call = networkAPI.getmainScreenbannerImages();
        call.enqueue(new Callback<List<BannerData>>() {
            @Override
            public void onResponse(Call<List<BannerData>> call, Response<List<BannerData>> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(context,errorResponse.getDesc(),Toasty.LENGTH_LONG)
                            .show();
                    return;
                }

                mutableBannerLiveData.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<BannerData>> call, Throwable t) {
                Toasty.error(context,t.getMessage(),Toasty.LENGTH_LONG)
                        .show();
            }
        });

    }

}
