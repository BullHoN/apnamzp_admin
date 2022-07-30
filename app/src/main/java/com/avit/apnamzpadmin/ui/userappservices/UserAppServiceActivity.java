package com.avit.apnamzpadmin.ui.userappservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.user.UserAppDetails;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.utils.ErrorUtils;
import com.google.android.material.textfield.TextInputEditText;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserAppServiceActivity extends AppCompatActivity {

    private LinearLayout shopStatusContainer;
    private ImageView shopStatusImage;
    private TextView shopStatusButton;
    private UserAppServiceViewModel viewModel;
    private TextInputEditText slurgeCharges, slurgeReason, itemsOnTheWayCost;
    private UserAppDetails userAppDetails;
    private String TAG = "UserAppServiceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_app_service);
        viewModel = new ViewModelProvider(this).get(UserAppServiceViewModel.class);
        viewModel.getUserAppDetails(this);

        shopStatusContainer = findViewById(R.id.shop_status_container);
        shopStatusImage = findViewById(R.id.shop_status_image);
        shopStatusButton = findViewById(R.id.shop_status_button);

        slurgeCharges = findViewById(R.id.slurge_charges);
        slurgeReason = findViewById(R.id.slurge_reason);
        itemsOnTheWayCost = findViewById(R.id.items_on_the_way_cost);

        viewModel.getMutableLiveData().observe(this, new Observer<UserAppDetails>() {
            @Override
            public void onChanged(UserAppDetails userAppD) {
                userAppDetails = userAppD;
                Log.i(TAG, "onChanged: " + userAppD.toString());
                updateUI();
            }
        });

        shopStatusContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAppDetails.setUserServiceOpen(!userAppDetails.isUserServiceOpen());
                setShopStatus();
            }
        });

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemsOnTheWayCostValue = Integer.parseInt(itemsOnTheWayCost.getText().toString());
                int slurgeChargesValue = Integer.parseInt(slurgeCharges.getText().toString());
                String slurgeReasonValue = slurgeReason.getText().toString();

                userAppDetails.setItemsOnTheWayCost(itemsOnTheWayCostValue);
                userAppDetails.setSlurgeCharges(slurgeChargesValue);
                userAppDetails.setSlurgeReason(slurgeReasonValue);

                updateDataToServer();
            }
        });

    }

    private void updateDataToServer(){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<NetworkResponse> call = networkAPI.updateUserAppDetails(userAppDetails);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getApplicationContext(),errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                Toasty.success(getApplicationContext(),"Updates Successfully",Toasty.LENGTH_SHORT)
                        .show();

            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: ",t);
            }
        });
    }

    private void updateUI(){
        setShopStatus();

        slurgeCharges.setText(String.valueOf(userAppDetails.getSlurgeCharges()));
        slurgeReason.setText(String.valueOf(userAppDetails.getSlurgeReason()));
        itemsOnTheWayCost.setText(String.valueOf(userAppDetails.getItemsOnTheWayCost()));



    }

    private void setShopStatus(){
        if(userAppDetails.isUserServiceOpen()){
            shopStatusContainer.setBackgroundColor(getResources().getColor(R.color.successColor));
            shopStatusImage.setImageResource(R.drawable.ic_open);
            shopStatusButton.setText("Shop Opened");
            shopStatusButton.setTextColor(getResources().getColor(R.color.successColor));
        }
        else {
            shopStatusContainer.setBackgroundColor(getResources().getColor(R.color.failure));
            shopStatusImage.setImageResource(R.drawable.ic_closed);
            shopStatusButton.setText("Shop Closed");
            shopStatusButton.setTextColor(getResources().getColor(R.color.failure));
        }
    }


}