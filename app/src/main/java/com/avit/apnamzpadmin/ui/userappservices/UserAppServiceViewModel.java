package com.avit.apnamzpadmin.ui.userappservices;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.user.UserAppDetails;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.utils.ErrorUtils;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserAppServiceViewModel extends ViewModel {

    private MutableLiveData<UserAppDetails> mutableLiveData;
    private String TAG = "UserAppServiceActivity";

    public UserAppServiceViewModel(){
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<UserAppDetails> getMutableLiveData() {
        return mutableLiveData;
    }

    public void getUserAppDetails(Context context){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<UserAppDetails> call = networkAPI.getUserAppDetails();
        call.enqueue(new Callback<UserAppDetails>() {
            @Override
            public void onResponse(Call<UserAppDetails> call, Response<UserAppDetails> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(context,errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserAppDetails> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

}
