package com.avit.apnamzpadmin.ui.bannerimageservice;

import android.content.Context;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.user.BannerData;
import com.bumptech.glide.Glide;

import java.util.List;

public class BannerImagesAdapter extends RecyclerView.Adapter<BannerImagesAdapter.BannerImagesViewHolder>{

    public interface BannerImageActions {
        void openBannerDetails(BannerData bannerData);
    };

    private List<BannerData> bannerDataList;
    private Context context;
    private BannerImageActions bannerImageActions;

    public BannerImagesAdapter(List<BannerData> bannerDataList, Context context, BannerImageActions bannerImageActions) {
        this.bannerDataList = bannerDataList;
        this.context = context;
        this.bannerImageActions = bannerImageActions;
    }

    @NonNull
    @Override
    public BannerImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bannerimage,parent,false);
        return new BannerImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerImagesViewHolder holder, int position) {
        BannerData curr = bannerDataList.get(position);

        Glide.with(context)
                .load(curr.getImageURL())
                .into(holder.bannerImageView);

        holder.bannerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bannerImageActions.openBannerDetails(curr);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bannerDataList.size();
    }

    public void replaceBannerImages(List<BannerData> newImages){
        bannerDataList = newImages;
        notifyDataSetChanged();
    }

    public class BannerImagesViewHolder extends RecyclerView.ViewHolder {

        public ImageView bannerImageView;

        public BannerImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerImageView = itemView.findViewById(R.id.banner_image);
        }
    }
}
