package com.avit.apnamzpadmin.ui.directorder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.shop.ShopData;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class DirectOrderActivity extends AppCompatActivity {

    private TextInputEditText shopNameEditText;
    private DirectOrderViewModel viewModel;
    private ShopData shopData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_order);
        viewModel = new ViewModelProvider(this).get(DirectOrderViewModel.class);

        shopNameEditText = findViewById(R.id.shop_name);

        findViewById(R.id.search_shop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shopName = shopNameEditText.getText().toString();
                viewModel.searchShop(getApplicationContext(),shopName);
            }
        });

        viewModel.getMutableShopLiveData().observe(this, new Observer<List<ShopData>>() {
            @Override
            public void onChanged(List<ShopData> shopDataList) {
                shopAllShopsDialog(shopDataList);
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
                shopData = shopDataList.get(i);
                shopNameEditText.setText(shopData.getName());
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}