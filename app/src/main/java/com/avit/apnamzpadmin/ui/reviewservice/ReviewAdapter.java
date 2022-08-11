package com.avit.apnamzpadmin.ui.reviewservice;

import android.content.Context;
import android.media.TimedText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.review.ReviewData;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewVeiwHolder>{

    private List<ReviewData> reviewDataList;
    private Context context;

    public ReviewAdapter(List<ReviewData> reviewDataList, Context context) {
        this.reviewDataList = reviewDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewVeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_showreview,parent,false);
        return new ReviewVeiwHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewVeiwHolder holder, int position) {
        ReviewData curr = reviewDataList.get(position);

        holder.nameView.setText(curr.getUserName());
        holder.initialView.setText(String.valueOf(curr.getUserName().charAt(0)));
        holder.reviewBodyView.setText(curr.getUserMessage());

        holder.ratingsBar.setRating(Float.parseFloat(curr.getRating()));
        holder.orderIdView.setText(curr.getOrderId());


    }

    @Override
    public int getItemCount() {
        return reviewDataList.size();
    }

    public void addItems(List<ReviewData> newReviewData){
        reviewDataList.addAll(newReviewData);
        notifyDataSetChanged();
    }

    public class ReviewVeiwHolder extends RecyclerView.ViewHolder {

        public TextView nameView, initialView, reviewBodyView, orderIdView;
        public ScaleRatingBar ratingsBar;

        public ReviewVeiwHolder(@NonNull View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.nameView);
            initialView = itemView.findViewById(R.id.initial_view);
            reviewBodyView = itemView.findViewById(R.id.reviewBody);
            orderIdView = itemView.findViewById(R.id.orderId);

            ratingsBar = itemView.findViewById(R.id.ratingsBar);
        }
    }
}
