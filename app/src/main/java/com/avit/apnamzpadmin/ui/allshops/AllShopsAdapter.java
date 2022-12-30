package com.avit.apnamzpadmin.ui.allshops;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.shop.ShopData;
import com.avit.apnamzpadmin.ui.shopdataservice.ShopDataActivity;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AllShopsAdapter extends RecyclerView.Adapter<AllShopsAdapter.AllShopsViewHolder>{

    private Context context;
    private List<ShopData> allShopsList;

    public AllShopsAdapter(Context context, List<ShopData> allShopsList) {
        this.context = context;
        this.allShopsList = allShopsList;
    }

    @NonNull
    @Override
    public AllShopsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_shop,parent,false);
        return new AllShopsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllShopsViewHolder holder, int position) {
        ShopData curr = allShopsList.get(position);

        holder.shopNameView.setText(curr.getName());

        if(curr.getCurrentSubsciption() == null){
            holder.subscriptionStatus.setText("Subscription Status: Not Active");
            return;
        }

        if(hasPlanExpired(curr.getCurrentSubsciption().getEndDate())){
            holder.subscriptionStatus.setText("Subscription Status: Not Active");
            holder.subscriptionStatus.setTextColor(context.getColor(R.color.errorColor));
        }
        else {
            holder.subscriptionStatus.setText("Subscription Status: Active");
        }

        SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MMM-yyyy");

        holder.startDate.setText("Start Date:\n" + displayFormat.format(curr.getCurrentSubsciption().getStartDate()));
        holder.endDate.setText("End Date:\n" + displayFormat.format(curr.getCurrentSubsciption().getEndDate()));

        holder.shopContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewShopDetailsIntent = new Intent(context, ShopDataActivity.class);
                Gson gson = new Gson();
                viewShopDetailsIntent.putExtra("shopData",gson.toJson(curr));

                context.startActivity(viewShopDetailsIntent);
            }
        });

    }

    private boolean hasPlanExpired(Date endDate){
        Date currDate = new Date();

        if(endDate.before(currDate)){
            return true;
        }

        return false;
    }

    @Override
    public int getItemCount() {
        return allShopsList.size();
    }

    public void changeList(List<ShopData> newList){
        allShopsList = newList;
        notifyDataSetChanged();
    }

    public class AllShopsViewHolder extends RecyclerView.ViewHolder {

        public TextView shopNameView, subscriptionStatus, startDate, endDate;
        private CardView shopContainer;

        public AllShopsViewHolder(@NonNull View itemView) {
            super(itemView);
            shopNameView = itemView.findViewById(R.id.shop_name);
            subscriptionStatus = itemView.findViewById(R.id.subscription_status);
            startDate = itemView.findViewById(R.id.start_date);
            endDate = itemView.findViewById(R.id.end_date);
            shopContainer = itemView.findViewById(R.id.shop_container);
        }
    }
}
