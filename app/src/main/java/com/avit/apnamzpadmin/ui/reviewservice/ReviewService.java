package com.avit.apnamzpadmin.ui.reviewservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.airbnb.lottie.L;
import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.review.ReviewData;

import java.util.ArrayList;
import java.util.List;

public class ReviewService extends AppCompatActivity {

    private String TAG = "ReviewService";
    private ReviewServiceViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_service);

        viewModel = new ViewModelProvider(this).get(ReviewServiceViewModel.class);
        viewModel.getReviews(this);

        RecyclerView recyclerView = findViewById(R.id.review_items);
        ReviewAdapter adapter = new ReviewAdapter(new ArrayList<>(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        viewModel.getMutableReviewLiveData().observe(this, new Observer<List<ReviewData>>() {
            @Override
            public void onChanged(List<ReviewData> reviewData) {
                adapter.addItems(reviewData);
            }
        });

    }
}