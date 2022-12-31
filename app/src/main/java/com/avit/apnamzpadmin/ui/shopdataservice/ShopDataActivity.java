package com.avit.apnamzpadmin.ui.shopdataservice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.shop.ShopData;
import com.avit.apnamzpadmin.models.subscription.Subscription;
import com.avit.apnamzpadmin.models.subscription.SubscriptionPricings;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.ui.editshop.EditShopViewModel;
import com.avit.apnamzpadmin.utils.ErrorUtils;
import com.avit.apnamzpadmin.utils.PrettyStrings;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShopDataActivity extends AppCompatActivity {

    private ShopData shopData;
    private SearchView searchView;
    private ShopDataViewModel viewModel;
    private TextInputEditText shopNameView;
    private Subscription currentSubscription;
    private TextView fromDate, endDate, totalEarnings, expectedPay;
    private SubscriptionPricings currPricing;
    private LinearLayout fromDateContainer, endDateContainer;
    private MaterialButton saveChangesButton;
    private String TAG = "ShopDataActivityTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_data);

        viewModel = new ViewModelProvider(this).get(ShopDataViewModel.class);

        searchView = findViewById(R.id.search_bar);
        shopNameView = findViewById(R.id.shopName);
        fromDate = findViewById(R.id.from_date);
        endDate = findViewById(R.id.end_date);
        totalEarnings = findViewById(R.id.total_earnings);
        expectedPay = findViewById(R.id.expected_pay);
        fromDateContainer = findViewById(R.id.from_date_container);
        endDateContainer = findViewById(R.id.end_date_container);
        saveChangesButton = findViewById(R.id.save_changes_button);

        Gson gson = new Gson();

        if(getIntent() != null && getIntent().getStringExtra("shopData") != null){
            shopData = gson.fromJson(getIntent().getStringExtra("shopData"),ShopData.class);
            shopNameView.setText(shopData.getName());
            viewModel.getActiveSubscription(getApplicationContext(),shopData.get_id());
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.searchShop(getApplicationContext(),query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        viewModel.getMutableShopLiveData().observe(this, new Observer<List<ShopData>>() {
            @Override
            public void onChanged(List<ShopData> shopDataList) {
                shopAllShopsDialog(shopDataList);
            }
        });

        viewModel.getMutableSubscriptionLiveData().observe(this, new Observer<Subscription>() {
            @Override
            public void onChanged(Subscription subscription) {
                setUpCurrentValidPlan(subscription);
            }
        });

    }

    private void setUpCurrentValidPlan(Subscription subscription){
        currentSubscription = subscription;

        if(subscription == null || subscription.getId() == null){
            Toasty.error(getApplicationContext(),"No Subscription Found",Toasty.LENGTH_LONG)
                    .show();
            return;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        fromDate.setText(simpleDateFormat.format(currentSubscription.getStartDate()));
        endDate.setText(simpleDateFormat.format(currentSubscription.getEndDate()));

        fromDateContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ShopDataActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date d =  sf.parse(selectedDate);
                            fromDate.setText(simpleDateFormat.format(d));
                            currentSubscription.setStartDate(d);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        endDateContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ShopDataActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date d =  sf.parse(selectedDate);
                            endDate.setText(simpleDateFormat.format(d));
                            currentSubscription.setEndDate(d);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        totalEarnings.setText("Total Sales: " + PrettyStrings.getPriceInRupees(currentSubscription.getTotalEarning()));

        for(SubscriptionPricings pricings : currentSubscription.getSubscriptionPricings()){
            if(currentSubscription.getTotalEarning() >= pricings.getFrom() && currentSubscription.getTotalEarning() <= pricings.getTo()){
                currPricing = new SubscriptionPricings(pricings);
                break;
            }
        }

        if(subscription.isFree()){
            expectedPay.setText("Expected Pay: Free!! 🎉🎉");
        }
        else {
            expectedPay.setText("Expected Pay: " + PrettyStrings.getPriceInRupees(currPricing.getAmount()));
        }

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });

    }

    private void saveChanges(){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<NetworkResponse> call = networkAPI.updateSubscription(currentSubscription);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getApplicationContext(),errorResponse.getDesc(),Toasty.LENGTH_LONG)
                            .show();
                    return;
                }

                Toasty.success(getApplicationContext(),"Saved",Toasty.LENGTH_LONG)
                        .show();

            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                Toasty.error(getApplicationContext(),t.getMessage(),Toasty.LENGTH_LONG)
                        .show();
            }
        });

    }

    private void shopAllShopsDialog(List<ShopData> shopDataList){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Shop");

        String[] shopNames = new String[shopDataList.size()];

        for(int i=0;i<shopDataList.size();i++){
            shopNames[i] = shopDataList.get(i).getName();
        }

        builder.setItems(shopNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                shopData = shopDataList.get(i);
                shopNameView.setText(shopData.getName());
                viewModel.getActiveSubscription(getApplicationContext(),shopData.get_id());
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}