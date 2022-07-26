package com.avit.apnamzpadmin.ui.deliverysathistatus;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzpadmin.models.deliverysathi.DeliverySathiStatus;
import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.utils.ErrorUtils;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DeliverySathisStatusViewModel extends ViewModel {

    private MutableLiveData<List<DeliverySathiStatus>> mutableLiveDeliverySathiStatusData;
    private String TAG = "DeliverySathisStatusActivity";

    public DeliverySathisStatusViewModel(){
        mutableLiveDeliverySathiStatusData = new MutableLiveData<>();
    }

    public MutableLiveData<List<DeliverySathiStatus>> getMutableLiveDeliverySathiStatusData() {
        return mutableLiveDeliverySathiStatusData;
    }

    public void getDeliverySathisStatus(Context context){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<List<DeliverySathiStatus>> call = networkAPI.getDeliverySathiStatus();
        call.enqueue(new Callback<List<DeliverySathiStatus>>() {
            @Override
            public void onResponse(Call<List<DeliverySathiStatus>> call, Response<List<DeliverySathiStatus>> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(context,errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                Log.i(TAG, "onResponse: " + response.body().size());
                mutableLiveDeliverySathiStatusData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<DeliverySathiStatus>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

}
