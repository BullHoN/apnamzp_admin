package com.avit.apnamzpadmin.ui.deliverysathistatus;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.deliverysathi.DeliverySathiOrderDetails;
import com.avit.apnamzpadmin.models.deliverysathi.DeliverySathiStatus;
import com.avit.apnamzpadmin.models.user.UserInfo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class DeliverySathiStatusAdapter extends RecyclerView.Adapter<DeliverySathiStatusAdapter.DeliverySathiStatusViewHolder>{

    public interface DeliverySathiStatusActions {
        void assignDeliverySathi(String deliverySathiPhoneNo);
        void updateDeliverySathi(UserInfo userInfo);
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

            for(DeliverySathiOrderDetails orderDetails : curr.getOrderDetailsList()){
                View view = LayoutInflater.from(context).inflate(R.layout.item_delivery_sathi_order,null,false);

                CardView toggleView = view.findViewById(R.id.toggle_delivery_sathi_order_visibility);
                LinearLayout orderContentContainer = view.findViewById(R.id.curr_order_container);
                TextView shopNameTitleView = view.findViewById(R.id.shop_name_title);

                shopNameTitleView.setText("Shop Name : " + orderDetails.getShopData().getName());

                toggleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(orderDetails.isShow()){
                            orderContentContainer.setVisibility(View.GONE);
                        }
                        else {
                            orderContentContainer.setVisibility(View.VISIBLE);
                        }

                        orderDetails.setShow(!orderDetails.isShow());
                    }
                });

                TextView shopPhoneNoView = view.findViewById(R.id.shop_phoneNo);
                TextView shopNameView = view.findViewById(R.id.shop_name);
                TextView shopAddressView = view.findViewById(R.id.shop_address);

                shopPhoneNoView.setText("+91 " + orderDetails.getShopData().getPhoneNo());
                shopPhoneNoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        call(orderDetails.getShopData().getPhoneNo());
                    }
                });

                shopNameView.setText(orderDetails.getShopData().getName());
                shopAddressView.setText(orderDetails.getShopData().getRawAddress());

                shopAddressView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openGoogleMaps(orderDetails.getShopData().getLatitude(),orderDetails.getShopData().getLongitude());
                    }
                });

                TextView userPhoneNoView = view.findViewById(R.id.user_phoneNo);
                TextView userAddressView = view.findViewById(R.id.user_address);

                userPhoneNoView.setText("+91 " + orderDetails.getCustomerData().getPhoneNo());
                userPhoneNoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        call(orderDetails.getCustomerData().getPhoneNo());
                    }
                });

                userAddressView.setText(orderDetails.getCustomerData().getRawAddress());
                userAddressView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openGoogleMaps(orderDetails.getCustomerData().getLatitude(),orderDetails.getCustomerData().getLongitude());

                    }
                });

                holder.ordersContainer.addView(view);
            }

        }

        holder.deliverySathiPhoneView.setText("+91" + curr.getDeliverySathi().getPhoneNo());

        holder.deliverySathiPhoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(curr.getDeliverySathi().getPhoneNo());
            }
        });

        holder.deliverySathiAddressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoogleMaps(curr.getDeliverySathi().getLatitude(),curr.getDeliverySathi().getLongitude());
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

        holder.currOrdersView.setText(String.valueOf(curr.getDeliverySathi().getCurrOrders()));
        holder.changeCurrOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newCurrOrderVal = Integer.valueOf(holder.currOrdersView.getText().toString());
                curr.getDeliverySathi().setCurrOrders(newCurrOrderVal);
                deliverySathiStatusActions.updateDeliverySathi(curr.getDeliverySathi());
            }
        });

        holder.cashInHandView.setText(String.valueOf(curr.getDeliverySathi().getCashInHand()));

        holder.changeCurrOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newCashInHand = Integer.valueOf(holder.cashInHandView.getText().toString());
                curr.getDeliverySathi().setCashInHand(newCashInHand);
                deliverySathiStatusActions.updateDeliverySathi(curr.getDeliverySathi());
            }
        });

    }

    public void updateItems(List<DeliverySathiStatus> newDeliverySathiStatus){
        deliverySathiStatusList = newDeliverySathiStatus;
        notifyDataSetChanged();
    }

    private void openGoogleMaps(String latitude,String longitude){
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude+","+longitude);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        context.startActivity(mapIntent);
    }

    private void call(String phoneNo){
        Intent callingIntent = new Intent();
        callingIntent.setAction(Intent.ACTION_DIAL);
        callingIntent.setData(Uri.parse("tel: " + phoneNo));
        context.startActivity(callingIntent);
    }

    @Override
    public int getItemCount() {
        return deliverySathiStatusList.size();
    }

    public class DeliverySathiStatusViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout ordersContainer;
        private TextView deliverySathiPhoneView, deliverySathiAddressView;
        private MaterialButton assignButton;
        private TextInputEditText currOrdersView, cashInHandView;
        private ImageButton changeCurrOrdersButton, changeCashInhandButton;

        public DeliverySathiStatusViewHolder(@NonNull View itemView) {
            super(itemView);

            ordersContainer = itemView.findViewById(R.id.orders_container);

            deliverySathiPhoneView = itemView.findViewById(R.id.delivery_sathi_phoneNo);
            deliverySathiAddressView = itemView.findViewById(R.id.delivery_sathi_address);

            currOrdersView = itemView.findViewById(R.id.curr_orders);
            changeCurrOrdersButton = itemView.findViewById(R.id.change_curr_orders);

            assignButton = itemView.findViewById(R.id.assign_delivery_sathi);

            cashInHandView = itemView.findViewById(R.id.cash_in_hand);
            changeCurrOrdersButton = itemView.findViewById(R.id.change_cash_in_hand);
        }
    }
}
