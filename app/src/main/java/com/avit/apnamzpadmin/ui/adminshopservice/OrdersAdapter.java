package com.avit.apnamzpadmin.ui.adminshopservice;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.order.OrderItem;
import com.avit.apnamzpadmin.ui.orderdetails.OrderDetailsActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersAdapterViewHolder>{

    public interface OrdersActions {
        void assignDeliverySathi(OrderItem orderItem);
        void addDeliverySathiIncome(String orderId, String totalCost);
        void cancelOrder(String orderId, String cancelReason);
    }

    private List<OrderItem> orderItems;
    private Context context;
    private OrdersActions ordersActions;


    public OrdersAdapter(List<OrderItem> orderItems, Context context,OrdersActions ordersActions) {
        this.orderItems = orderItems;
        this.context = context;
        this.ordersActions = ordersActions;
    }

    @NonNull
    @Override
    public OrdersAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order,parent,false);
        return new OrdersAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapterViewHolder holder, int position) {


        OrderItem curr = orderItems.get(position);

        holder.orderingItemsList.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        OrderingItemsAdapter adapter = new OrderingItemsAdapter(curr.getOrderItems(),context);
        holder.orderingItemsList.setAdapter(adapter);


        holder.orderId.setText("Order Id: #" + curr.get_id());
        holder.shopName.setText("Name: " + curr.getShopInfo().getName());
        holder.shopAddress.setText("Address: " + curr.getShopInfo().getRawAddress());

        holder.shopAddressContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoogleMaps(curr.getShopInfo().getLatitude(),curr.getShopInfo().getLongitude());
            }
        });

        holder.shopPhoneNo.setText("Phone No: " + curr.getShopInfo().getPhoneNo());


        holder.customerName.setText("Name: " + curr.getUserInfo().getName());
        holder.customerAddress.setText("Address: " + curr.getUserInfo().getRawAddress());

        holder.customerAddressContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoogleMaps(curr.getUserInfo().getLatitude(),curr.getUserInfo().getLongitude());
            }
        });

        holder.customerPhoneNo.setText("PhoneNo: " + curr.getUserInfo().getPhoneNo());

        holder.customerDetailsToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(curr.getUserInfo().isVissible()){
                    curr.getUserInfo().setVissible(false);
                    holder.expandableCustomerDetailsView.setVisibility(View.GONE);
                }
                else {
                    curr.getUserInfo().setVissible(true);
                    holder.expandableCustomerDetailsView.setVisibility(View.VISIBLE);
                }
            }
        });


        holder.shopDetailsToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(curr.getShopInfo().isVissible()){
                    curr.getShopInfo().setVissible(false);
                    holder.expandableShopDetailsView.setVisibility(View.GONE);
                }
                else {
                    curr.getShopInfo().setVissible(true);
                    holder.expandableShopDetailsView.setVisibility(View.VISIBLE);
                }
            }
        });

        if(curr.getItemsOnTheWay() != null && curr.getItemsOnTheWay().size() > 0){
            holder.itemsOnTheWayRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            OrderItemsOnTheWayAdapter orderingItemsAdapter = new OrderItemsOnTheWayAdapter(context,curr.getItemsOnTheWay());
            holder.itemsOnTheWayRecyclerView.setAdapter(orderingItemsAdapter);

            holder.itemsOnTheWayToggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(curr.isItemsOnTheWayVisible()){
                        curr.setItemsOnTheWayVisible(false);
                        holder.expandableItemsOnTheWayDetailsView.setVisibility(View.GONE);
                    }
                    else {
                        curr.setItemsOnTheWayVisible(true);
                        holder.expandableItemsOnTheWayDetailsView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }



        holder.moreActionsMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view,curr.get_id(),curr);
            }
        });

        holder.nextActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ordersActions.assignDeliverySathi(curr);
            }
        });

//        int currPosition = position;
//        holder.nextActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ordersActions.updateOrderStatus(curr.get_id(), curr.getOrderStatus()+1,currPosition);
//            }
//        });

    }

    public void removeOrderItem(int position){
        orderItems.remove(position);
        notifyDataSetChanged();
    }

    private void openGoogleMaps(String latitude,String longitude){
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude+","+longitude);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        context.startActivity(mapIntent);
    }

    private void showMenu(View v,String order_id,OrderItem orderItem){
        PopupMenu popupMenu = new PopupMenu(context,v);
        popupMenu.inflate(R.menu.menu_order);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.cancel_order:
                        ordersActions.cancelOrder(order_id,"No Delivery Sathi Found");
                        orderItems.remove(orderItem);
                        notifyDataSetChanged();
                        return true;
                    case R.id.add_delivery_sathi_income:
                        addDeliverySathiIncome(order_id);
                        return true;
                    case R.id.more_details:
                        openOrderDetails(orderItem);
                        return true;
                    default:
                        return  false;
                }

            }
        });

        popupMenu.show();
    }

    private void openOrderDetails(OrderItem orderItem){
        Intent intent = new Intent(context, OrderDetailsActivity.class);
        Gson gson = new Gson();
        intent.putExtra("orderItem", gson.toJson(orderItem));
        context.startActivity(intent);
    }

    private void addDeliverySathiIncome(String order_id){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_order_item_actions,null,false);

        TextView titleView = view.findViewById(R.id.title);
        titleView.setText("Enter delivery sathi income");

        TextInputEditText textInputEditText = view.findViewById(R.id.dialog_input);

        builder.setView(view);

        builder.setPositiveButton("submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String data = textInputEditText.getText().toString();
                if(data.length() == 0){
                    Toasty.error(context,"Enter Valid Data",Toasty.LENGTH_SHORT).show();
                    return;
                }

                ordersActions.addDeliverySathiIncome(order_id,data);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public void updateItems(List<OrderItem> newItems){
        orderItems = newItems;
        notifyDataSetChanged();
    }

    public class OrdersAdapterViewHolder extends RecyclerView.ViewHolder {

        public RecyclerView orderingItemsList;
        public TextView orderId,customerName,customerPhoneNo,customerAddress;
        public TextView shopName,shopPhoneNo,shopAddress;
        public LinearLayout customerDetailsToggleButton, expandableCustomerDetailsView;
        public LinearLayout shopDetailsToggleButton, expandableShopDetailsView;
        public LinearLayout itemsOnTheWayToggleButton, expandableItemsOnTheWayDetailsView;
        public RecyclerView itemsOnTheWayRecyclerView;
        public MaterialButton nextActionButton;
        public ImageButton moreActionsMenuButton;
        public TextView totalAmountToTakeView, totalAmountToGiveView;
        public LinearLayout customerAddressContainer, shopAddressContainer;

        public OrdersAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            orderingItemsList = itemView.findViewById(R.id.order_items);
            orderId = itemView.findViewById(R.id.order_id);
            customerName = itemView.findViewById(R.id.customer_name);
            customerPhoneNo = itemView.findViewById(R.id.customer_phoneNo);
            customerAddress = itemView.findViewById(R.id.customer_address);
            customerDetailsToggleButton = itemView.findViewById(R.id.customer_toggle_button);
            expandableCustomerDetailsView = itemView.findViewById(R.id.expandable_customer_details);

            itemsOnTheWayToggleButton = itemView.findViewById(R.id.itemsOn_the_way_toggle_button);
            expandableItemsOnTheWayDetailsView = itemView.findViewById(R.id.items_on_the_way_expandable_layout);
            itemsOnTheWayRecyclerView = itemView.findViewById(R.id.items_on_the_way_recyclerview);

            shopName = itemView.findViewById(R.id.shop_name);
            shopPhoneNo = itemView.findViewById(R.id.shop_phoneNo);
            shopAddress = itemView.findViewById(R.id.shop_address);
            shopDetailsToggleButton = itemView.findViewById(R.id.shop_toggle_button);
            expandableShopDetailsView = itemView.findViewById(R.id.shop_expandable_layout);

            nextActionButton = itemView.findViewById(R.id.next_step);
            moreActionsMenuButton = itemView.findViewById(R.id.more_actions);

            customerAddressContainer = itemView.findViewById(R.id.customer_address_container);
            shopAddressContainer = itemView.findViewById(R.id.shop_address_container);

        }
    }
}
