package com.avit.apnamzpadmin.ui.adminshopservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.order.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class AdminShopServiceActivity extends AppCompatActivity implements OrdersAdapter.OrdersActions{

    private String TAG = "AdminShopServiceActivity";
    private AdminShopServiceViewModel viewModel;
    private RecyclerView pendingOrderItems;
    private OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_shop_service);
        viewModel = new ViewModelProvider(this).get(AdminShopServiceViewModel.class);

        viewModel.getPendingOrders(getApplicationContext());

        pendingOrderItems = findViewById(R.id.pending_orders_list);
        pendingOrderItems.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        ordersAdapter = new OrdersAdapter(new ArrayList<>(),this,this);

        pendingOrderItems.setAdapter(ordersAdapter);

        viewModel.getMutableLivePendingOrderItemsData().observe(this, new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> orderItems) {
                ordersAdapter.updateItems(orderItems);
            }
        });

    }

    @Override
    public void updateOrderStatus(String orderId, Integer updatedStatus, int position) {

    }

    @Override
    public void updateItemsOnTheWayTotalCost(String orderId, String totalCost) {

    }

    @Override
    public void cancelItemsOnTheWay(String orderId) {

    }
}