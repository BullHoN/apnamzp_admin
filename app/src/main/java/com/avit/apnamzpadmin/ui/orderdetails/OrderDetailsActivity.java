package com.avit.apnamzpadmin.ui.orderdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.order.OrderItem;
import com.avit.apnamzpadmin.utils.PrettyStrings;
import com.google.gson.Gson;

public class OrderDetailsActivity extends AppCompatActivity {

    private OrderItem orderItem;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        gson = new Gson();
        orderItem = gson.fromJson(getIntent().getStringExtra("orderItem"),OrderItem.class);

        String[] messages = {"Order Placed","Order Confirmed","Order In Preperation","Rider Assign","Rider Reached Shop","Rider On The Way","Order Delivered","Order Cancelled"};

        TextView orderStatusView =  findViewById(R.id.order_status);
        orderStatusView.setText(messages[orderItem.getOrderStatus()]);

        TextView serviceType = findViewById(R.id.service_type);
        if(orderItem.getBillingDetails().getDeliveryService()){
            serviceType.setText("Delivery Service");
        }else {
            serviceType.setText("Pickup service");
        }

        TextView deliveryChargeView = findViewById(R.id.delivery_charge);
        deliveryChargeView.setText(PrettyStrings.getPriceInRupees(orderItem.getBillingDetails().getDeliveryCharge()));

        TextView offerCodeView = findViewById(R.id.offer_code);
        offerCodeView.setText(orderItem.getOfferCode());

        TextView offerDeducted = findViewById(R.id.offer_deducted_amount);
        offerDeducted.setText(PrettyStrings.getPriceInRupees(orderItem.getBillingDetails().getOfferDiscountedAmount()));

        TextView totalItemsDiscount = findViewById(R.id.total_items_discount);
        totalItemsDiscount.setText(PrettyStrings.getPriceInRupees(orderItem.getBillingDetails().getTotalDiscount()));

        TextView totalTaxAndPackagingCharge = findViewById(R.id.tax_and_pacgign_charge);
        totalTaxAndPackagingCharge.setText(PrettyStrings.getPriceInRupees(orderItem.getBillingDetails().getTotalTaxesAndPackingCharge()));

        TextView itemsOnTheWayDeliveryCost = findViewById(R.id.itemsOn_the_way_delivery_cost);
        itemsOnTheWayDeliveryCost.setText(PrettyStrings.getPriceInRupees(orderItem.getBillingDetails().getItemsOnTheWayTotalCost()));

        TextView itemsOnTheActualCost = findViewById(R.id.items_on_the_way_acutal_cost);
        itemsOnTheActualCost.setText(PrettyStrings.getPriceInRupees(orderItem.getBillingDetails().getItemsOnTheWayActualCost()));

        TextView freeDeliveryPrice = findViewById(R.id.free_delivery_price);
        freeDeliveryPrice.setText(PrettyStrings.getPriceInRupees(orderItem.getBillingDetails().getFreeDeliveryPrice()));

        TextView taxPercentage = findViewById(R.id.tax_percentage);
        taxPercentage.setText(String.valueOf(orderItem.getBillingDetails().getTaxPercentage()));

        TextView totalPay = findViewById(R.id.total_pay);
        totalPay.setText(String.valueOf(orderItem.getBillingDetails().getTotalPay()));

        TextView totalItemsPay = findViewById(R.id.total_items_cost);
        totalItemsPay.setText(PrettyStrings.getPriceInRupees(orderItem.getBillingDetails().getItemTotal()));

    }
}