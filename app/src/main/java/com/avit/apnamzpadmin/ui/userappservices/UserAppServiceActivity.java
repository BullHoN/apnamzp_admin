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
import com.avit.apnamzpadmin.models.user.DistanceBasePricings;
import com.avit.apnamzpadmin.models.user.UserAppDetails;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.utils.ErrorUtils;
import com.avit.apnamzpadmin.utils.Validations;
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
    private TextInputEditText slurgeCharges, slurgeReason, itemsOnTheWayCost, deliveryBasePriceBelowThreeView,
        deliveryBasePriceAboveThreeView;
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
        deliveryBasePriceBelowThreeView = findViewById(R.id.delivery_price_below_three);
        deliveryBasePriceAboveThreeView = findViewById(R.id.delivery_base_price_after_three);

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
                closeAllShops();
                setShopStatus();
            }
        });

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Validations.isNumber(itemsOnTheWayCost.getText().toString())
                        || !Validations.isNumber(slurgeCharges.getText().toString())
                        || slurgeReason.getText().toString().length() == 0
                        || !Validations.isNumber(deliveryBasePriceBelowThreeView.getText().toString())
                        || !Validations.isNumber(deliveryBasePriceAboveThreeView.getText().toString())){

                    Toasty.error(getApplicationContext(),"BSDK Iske liy tum hamare darvaze pe chay aay, sahi kar usse.",Toasty.LENGTH_LONG)
                            .show();
                    return;
                }

                int itemsOnTheWayCostValue = Integer.parseInt(itemsOnTheWayCost.getText().toString());
                int slurgeChargesValue = Integer.parseInt(slurgeCharges.getText().toString());
                String slurgeReasonValue = slurgeReason.getText().toString();

                int belowThreePrice = Integer.parseInt(deliveryBasePriceBelowThreeView.getText().toString());
                int aboveThree = Integer.parseInt(deliveryBasePriceAboveThreeView.getText().toString());

                DistanceBasePricings distanceBasePricings = new DistanceBasePricings(belowThreePrice,aboveThree);

                userAppDetails.setItemsOnTheWayCost(itemsOnTheWayCostValue);
                userAppDetails.setSlurgeCharges(slurgeChargesValue);
                userAppDetails.setSlurgeReason(slurgeReasonValue);
                userAppDetails.setDistanceBasePricings(distanceBasePricings);

                updateDataToServer();
            }
        });

    }

    private void closeAllShops(){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<NetworkResponse> call = networkAPI.closeAllShops();
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getApplicationContext(),errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                Toasty.warning(getApplicationContext(),"All Shops are closed",Toasty.LENGTH_SHORT)
                        .show();

            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                Toasty.error(getApplicationContext(),t.getMessage(),Toasty.LENGTH_SHORT)
                        .show();
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
        deliveryBasePriceBelowThreeView.setText(String.valueOf(userAppDetails.getDistanceBasePricings().getBELOW_THREE()));
        deliveryBasePriceAboveThreeView.setText(String.valueOf(userAppDetails.getDistanceBasePricings().getBELOW_SIX()));
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