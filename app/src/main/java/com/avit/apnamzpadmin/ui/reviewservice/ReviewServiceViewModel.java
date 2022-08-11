package com.avit.apnamzpadmin.ui.reviewservice;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.review.ReviewData;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.utils.ErrorUtils;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReviewServiceViewModel extends ViewModel {

    private MutableLiveData<List<ReviewData>> mutableReviewLiveData;
    private String TAG = "ReviewService";

    public ReviewServiceViewModel(){
        mutableReviewLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<ReviewData>> getMutableReviewLiveData() {
        return mutableReviewLiveData;
    }

    public void getReviews(Context context){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<List<ReviewData>> call = networkAPI.getApnaReviews();
        call.enqueue(new Callback<List<ReviewData>>() {
            @Override
            public void onResponse(Call<List<ReviewData>> call, Response<List<ReviewData>> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.success(context,errorResponse.getDesc(),Toasty.LENGTH_LONG)
                            .show();
                }

                mutableReviewLiveData.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<ReviewData>> call, Throwable t) {
                Toasty.error(context,t.getMessage(),Toasty.LENGTH_SHORT)
                        .show();
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

}
