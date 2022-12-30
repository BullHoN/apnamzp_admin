package com.avit.apnamzpadmin.ui.allshops;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.shop.ShopData;

import java.util.ArrayList;
import java.util.List;

public class AllShopsActivity extends AppCompatActivity {

    private AllShopsViewModel viewModel;
    private AllShopsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_shops);

        viewModel = new ViewModelProvider(this).get(AllShopsViewModel.class);
        viewModel.getShops(this);

        RecyclerView allShopsRecyclerView = findViewById(R.id.all_shops_recycler_view);
        allShopsRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        adapter = new AllShopsAdapter(this,new ArrayList<>());
        allShopsRecyclerView.setAdapter(adapter);

        viewModel.getMutableShopLiveData().observe(this, new Observer<List<ShopData>>() {
            @Override
            public void onChanged(List<ShopData> shopDataList) {
                adapter.changeList(shopDataList);
            }
        });

    }
}