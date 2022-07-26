package com.avit.apnamzpadmin.ui.deliverysathistatus;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.deliverysathi.DeliverySathiStatus;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class DeliverySathiStatusAdapter extends RecyclerView.Adapter<DeliverySathiStatusAdapter.DeliverySathiStatusViewHolder>{

    public interface DeliverySathiStatusActions {
        void assignDeliverySathi(String deliverySathiPhoneNo);
    }

    private List<DeliverySathiStatus> deliverySathiStatusList;
    private Context context;
    private boolean showAssignButton;
    private DeliverySathiStatusActions deliverySathiStatusActions;
    private String TAG = "DeliverySathisStatusActivity";

    public DeliverySathiStatusAdapter(List<DeliverySathiStatus> deliverySathiStatusList, Context context, boolean showAssignButton, DeliverySathiStatusActions deliverySathiStatusActions) {
        this.deliverySathiStatusList = deliverySathiStatusList;
        this.context = context;
        this.showAssignButton = showAssignButton;
        this.deliverySathiStatusActions = deliverySathiStatusActions;
    }

    @NonNull
    @Override
    public DeliverySathiStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_delivery_sathi_location,parent,false);
        return new DeliverySathiStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliverySathiStatusViewHolder holder, int position) {
        DeliverySathiStatus curr = deliverySathiStatusList.get(position);
        Log.i(TAG, "onBindViewHolder: " + curr.getDeliverySathi().getPhoneNo());

        if(!curr.isDeliverySathiFree()){
            holder.orderStatusContainer.setVisibility(View.VISIBLE);

            holder.shopPhoneNoView.setText("+91 " + curr.getShopData().getPhoneNo());
            holder.shopNameView.setText(curr.getShopData().getName());
            holder.shopAddressView.setText(curr.getShopData().getRawAddress());

            holder.shopAddressView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: Show location on google map
                }
            });

            holder.userPhoneNoView.setText("+91 " + curr.getCustomerData().getPhoneNo());
            holder.userAddressView.setText(curr.getCustomerData().getRawAddress());

            holder.userAddressView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: Show location on google map
                }
            });
        }

        holder.deliverySathiPhoneView.setText("+91" + curr.getDeliverySathi().getPhoneNo());

        holder.deliverySathiAddressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Show location on map
            }
        });

        holder.assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deliverySathiStatusActions.assignDeliverySathi(curr.getDeliverySathi().getPhoneNo());
            }
        });

        if(!showAssignButton){
            holder.assignButton.setVisibility(View.GONE);
        }

    }

    public void updateItems(List<DeliverySathiStatus> newDeliverySathiStatus){
        deliverySathiStatusList = newDeliverySathiStatus;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return deliverySathiStatusList.size();
    }

    public class DeliverySathiStatusViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout orderStatusContainer;
        private TextView deliverySathiPhoneView, deliverySathiAddressView, shopNameView,
                shopPhoneNoView, shopAddressView, userPhoneNoView, userAddressView;
        private MaterialButton assignButton;

        public DeliverySathiStatusViewHolder(@NonNull View itemView) {
            super(itemView);

            orderStatusContainer = itemView.findViewById(R.id.curr_order_status_container);

            deliverySathiPhoneView = itemView.findViewById(R.id.delivery_sathi_phoneNo);
            deliverySathiAddressView = itemView.findViewById(R.id.delivery_sathi_address);

            shopNameView = itemView.findViewById(R.id.shop_name);
            shopAddressView = itemView.findViewById(R.id.shop_address);
            shopPhoneNoView = itemView.findViewById(R.id.shop_phoneNo);

            userPhoneNoView = itemView.findViewById(R.id.user_phoneNo);
            userAddressView = itemView.findViewById(R.id.user_address);

            assignButton = itemView.findViewById(R.id.assign_delivery_sathi);
        }
    }
}
