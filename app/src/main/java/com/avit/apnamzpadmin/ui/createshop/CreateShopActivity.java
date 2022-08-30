package com.avit.apnamzpadmin.ui.createshop;

import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.shop.CreateShopPostData;
import com.avit.apnamzpadmin.models.shop.ShopAddressData;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.utils.ErrorUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateShopActivity extends AppCompatActivity {

    private String shopTypes[] = {"Sweets","Pure Vegetarian Resturants","Non veg Resturants","Street Food","Home Chefs",
        "Groceries", "Medicines"};
    private Spinner shopTypesSpinner;
    private MaterialButton createShopButton;
    private TextInputEditText shopNameView, shopPhoneNoView, shopPasswordView, shopAddressView, shopLatitudeView, shopLongitudeView;
    private Switch stallShopSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shop);

        shopNameView = findViewById(R.id.shop_name);
        shopPhoneNoView = findViewById(R.id.shop_phoneNo);
        shopPasswordView = findViewById(R.id.shop_password);
        shopAddressView  = findViewById(R.id.shop_mainAddress);
        shopLatitudeView = findViewById(R.id.shop_latitude);
        shopLongitudeView = findViewById(R.id.shop_longitude);
        stallShopSwitch = findViewById(R.id.stall_shop);

        // Sweets, Pure Vegetarian Resturants, Non veg Resturants, Street Food, Home Chefs, Groceries, Medicines

        shopTypesSpinner  = findViewById(R.id.shop_type);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,shopTypes);

        shopTypesSpinner.setAdapter(arrayAdapter);

        createShopButton = findViewById(R.id.create_shop);
        createShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shopName = shopNameView.getText().toString();
                String shopPhoneNo = shopPhoneNoView.getText().toString();
                String shopPassword = shopPasswordView.getText().toString();
                String shopMainAddress = shopAddressView.getText().toString();
                String shopLatitude = shopLatitudeView.getText().toString();
                String shopLongitude = shopLongitudeView.getText().toString();
                boolean stallShop = stallShopSwitch.isChecked();
                String shopType = shopTypes[shopTypesSpinner.getSelectedItemPosition()];

                if(shopName.length() < 3 || shopPhoneNo.length() != 10 || shopPassword.length() < 5
                    || shopMainAddress.length() < 4 || shopLatitude.length() < 4 || shopLongitude.length() < 4){
                    Toasty.error(getApplicationContext(),"Hosh me dal le details",Toasty.LENGTH_LONG)
                            .show();
                    return;
                }

                createShopButton.setEnabled(false);

                CreateShopPostData  postData = new CreateShopPostData(shopName,shopPhoneNo,
                        shopPassword,shopType,new ShopAddressData(shopMainAddress,shopLatitude,shopLongitude),stallShop);

                createShop(postData);

            }
        });

    }

    private void createShop(CreateShopPostData  postData){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<NetworkResponse> call = networkAPI.createShop(postData);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getApplicationContext(),errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                Toasty.success(getApplicationContext(),"Shop Added Successfully",Toasty.LENGTH_SHORT)
                        .show();

                finish();
            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                Toasty.error(getApplicationContext(),t.getMessage(),Toasty.LENGTH_LONG)
                        .show();
            }
        });

    }

}