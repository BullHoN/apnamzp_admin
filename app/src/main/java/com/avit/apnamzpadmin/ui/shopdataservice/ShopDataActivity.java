package com.avit.apnamzpadmin.ui.shopdataservice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.shop.ShopData;
import com.avit.apnamzpadmin.models.subscription.Subscription;
import com.avit.apnamzpadmin.models.subscription.SubscriptionPricings;
import com.avit.apnamzpadmin.ui.editshop.EditShopViewModel;
import com.avit.apnamzpadmin.utils.PrettyStrings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class ShopDataActivity extends AppCompatActivity {

    private ShopData shopData;
    private SearchView searchView;
    private ShopDataViewModel viewModel;
    private TextInputEditText shopNameView;
    private Subscription currentSubscription;
    private TextView fromDate, endDate, totalEarnings, expectedPay;
    private SubscriptionPricings currPricing;

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

        fromDate.setText(currentSubscription.getStartDate().toLocaleString().split(" ")[0]);
        endDate.setText(currentSubscription.getEndDate().toLocaleString().split(" ")[0]);

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