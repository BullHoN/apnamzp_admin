package com.avit.apnamzpadmin.ui.servicestatus;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzpadmin.models.admin.ServiceStatus;
import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.utils.ErrorUtils;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ServiceStatusViewModel extends ViewModel {
    private MutableLiveData<ServiceStatus> mutableServiceStatusLiveData;

    public ServiceStatusViewModel(){
        mutableServiceStatusLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<ServiceStatus> getMutableServiceStatusLiveData() {
        return mutableServiceStatusLiveData;
    }

    public void getStatus(Context context){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<ServiceStatus> call = networkAPI.getServiceStatus();
        call.enqueue(new Callback<ServiceStatus>() {
            @Override
            public void onResponse(Call<ServiceStatus> call, Response<ServiceStatus> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(context,errorResponse.getDesc(),Toasty.LENGTH_LONG)
                            .show();
                    return;
                }

                mutableServiceStatusLiveData.setValue(response.body());

            }

            @Override
            public void onFailure(Call<ServiceStatus> call, Throwable t) {
                Toasty.error(context,t.getMessage(),Toasty.LENGTH_LONG)
                        .show();
            }
        });

    }

}
