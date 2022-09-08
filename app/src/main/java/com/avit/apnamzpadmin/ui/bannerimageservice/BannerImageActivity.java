package com.avit.apnamzpadmin.ui.bannerimageservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.user.BannerData;
import com.avit.apnamzpadmin.ui.bannerimagedetails.BannerImagesDetails;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BannerImageActivity extends AppCompatActivity implements BannerImagesAdapter.BannerImageActions {

    private BannerImagesViewModel viewModel;
    private BannerImagesAdapter adapter;
    private RecyclerView bannerImagesRecyclerView;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_image);

        viewModel = new ViewModelProvider(this).get(BannerImagesViewModel.class);
        gson = new Gson();

        bannerImagesRecyclerView = findViewById(R.id.banner_images_list);
        bannerImagesRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        adapter = new BannerImagesAdapter(new ArrayList<>(),this,this);
        bannerImagesRecyclerView.setAdapter(adapter);

        viewModel.getMutableBannerLiveData().observe(this, new Observer<List<BannerData>>() {
            @Override
            public void onChanged(List<BannerData> bannerData) {
                adapter.replaceBannerImages(bannerData);
            }
        });

        findViewById(R.id.add_new_banner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bannerImagesDetailsIntent = new Intent(getApplicationContext(), BannerImagesDetails.class);
                startActivity(bannerImagesDetailsIntent);
            }
        });

    }

    @Override
    public void openBannerDetails(BannerData bannerData) {
        Intent bannerImagesDetailsIntent = new Intent(this, BannerImagesDetails.class);
        bannerImagesDetailsIntent.putExtra("bannerData",gson.toJson(bannerData));
        startActivity(bannerImagesDetailsIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getBannerImages(this);
    }
}