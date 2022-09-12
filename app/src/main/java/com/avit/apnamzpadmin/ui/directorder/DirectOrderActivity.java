package com.avit.apnamzpadmin.ui.directorder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Switch;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.order.BillingDetails;
import com.avit.apnamzpadmin.models.order.OrderItem;
import com.avit.apnamzpadmin.models.shop.ShopData;
import com.avit.apnamzpadmin.models.shop.ShopItemData;
import com.avit.apnamzpadmin.models.shop.ShopPricingData;
import com.avit.apnamzpadmin.models.user.UserInfo;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.utils.ErrorUtils;
import com.avit.apnamzpadmin.utils.PrettyStrings;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DirectOrderActivity extends AppCompatActivity {

    private TextInputEditText shopNameEditText;
    private List<ShopItemData> allShopItems, orderItems;
    private DirectOrderViewModel viewModel;
    private ShopData shopData;
    private RecyclerView orderItemsView;
    private OrderItemsAdapter orderMenuItemsAdapter;
    private TextInputEditText itemTotalView, totalItemDiscountView, totalTaxesAndPackingChargeView,
            totalPayView, processingFeeView, expectedDeliveryTimeView, actualDistanceView, customerPhoneNo,
            customerLatituteView, customerLongtitudeView, customerRawAddressView,deliveryChargeView;
    private OrderItem currOrderItem;
    private Switch adminShopServiceSwitch;
    private String TAG = "DirectOrderActivitys";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_order);
        viewModel = new ViewModelProvider(this).get(DirectOrderViewModel.class);
        currOrderItem = new OrderItem();

        shopNameEditText = findViewById(R.id.shop_name);
        itemTotalView = findViewById(R.id.itemTotal);
        totalItemDiscountView = findViewById(R.id.totalDiscount);
        totalTaxesAndPackingChargeView = findViewById(R.id.totalTaxesAndPackingCharge);
        totalPayView = findViewById(R.id.totalPay);
        processingFeeView = findViewById(R.id.processingFee);
        expectedDeliveryTimeView = findViewById(R.id.expectedDeliveryTime);
        actualDistanceView = findViewById(R.id.actualDistance);
        customerPhoneNo = findViewById(R.id.customer_phoneNo);
        customerLatituteView = findViewById(R.id.customer_latitude);
        customerLongtitudeView = findViewById(R.id.customer_longtitude);
        customerRawAddressView = findViewById(R.id.customer_raw_address);
        deliveryChargeView = findViewById(R.id.deliveryCharge);
        adminShopServiceSwitch = findViewById(R.id.admin_shop_service);


        orderItemsView = findViewById(R.id.order_items);
        orderItems = new ArrayList<>();

        findViewById(R.id.search_shop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shopName = shopNameEditText.getText().toString();
                viewModel.searchShop(getApplicationContext(),shopName);
            }
        });

        orderItemsView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        orderMenuItemsAdapter = new OrderItemsAdapter(new ArrayList<>(),this,
                itemTotalView,totalItemDiscountView,totalTaxesAndPackingChargeView);
        orderItemsView.setAdapter(orderMenuItemsAdapter);


        viewModel.getMutableShopLiveData().observe(this, new Observer<List<ShopData>>() {
            @Override
            public void onChanged(List<ShopData> shopDataList) {
                shopAllShopsDialog(shopDataList);
            }
        });


        findViewById(R.id.addOrderItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchDialog();
            }
        });



        findViewById(R.id.placeOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrder();
            }
        });

    }

    private void placeOrder(){
        currOrderItem.setOrderItems(orderItems);
        currOrderItem.setBillingDetails(new BillingDetails(
                getValueFromView(deliveryChargeView), getValueFromView(itemTotalView), 0,
                getValueFromView(totalItemDiscountView), getValueFromView(totalTaxesAndPackingChargeView),
                getValueFromView(totalPayView), 0, 0, 5000,
                "0",true, getValueFromView(processingFeeView)));

        currOrderItem.setDeliveryAddress(new UserInfo(null,getTextFromView(customerLatituteView),
                getTextFromView(customerLongtitudeView), null, getTextFromView(customerRawAddressView)));

        currOrderItem.setUserId(getTextFromView(customerPhoneNo));

        if(adminShopServiceSwitch.isChecked()){
            currOrderItem.setOrderStatus(4);
        }

        currOrderItem.setAdminShopService(adminShopServiceSwitch.isChecked());

        currOrderItem.setActualDistance(getTextFromView(actualDistanceView));
        currOrderItem.setExpectedDeliveryTime(getTextFromView(expectedDeliveryTimeView));

        currOrderItem.setPaymentReceivedToShop(true);

        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<NetworkResponse> call = networkAPI.createDirectOrder(currOrderItem);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getApplicationContext(),errorResponse.getDesc(),Toasty.LENGTH_LONG)
                            .show();
                    return;
                }

                Toasty.success(getApplicationContext(),"order created",Toasty.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                Toasty.error(getApplicationContext(),t.getMessage(),Toasty.LENGTH_LONG)
                        .show();
            }
        });


    }

    private int getValueFromView(TextInputEditText view){
        return Integer.parseInt(view.getText().toString());
    }

    private String getTextFromView(TextInputEditText view){
        return view.getText().toString();
    }

    private void showSearchDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_menu_items,null,false);

        RecyclerView menuItems = view.findViewById(R.id.menu_items);
        SearchView searchView = view.findViewById(R.id.search_bar);

        MenuItemsAdapter menuItemsAdapter = new MenuItemsAdapter(this, allShopItems, new MenuItemsAdapter.MenuItemsActions() {
            @Override
            public void addToOrderItems(ShopItemData shopItemData) {
                Log.i(TAG, "addToOrderItems: " + shopItemData.getName());
                showItemPricingDialog(shopItemData);
            }
        });
        menuItems.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        menuItems.setAdapter(menuItemsAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<ShopItemData> filteredItems = new ArrayList<>();
                for(ShopItemData menuItem : allShopItems){
                    if(menuItem.getName().toLowerCase().contains(query.toLowerCase())){
                        filteredItems.add(menuItem);
                    }
                }
                Log.i(TAG, "onQueryTextSubmit: " + filteredItems.size() + " " + query);
                menuItemsAdapter.replaceItems(filteredItems);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void showItemPricingDialog(ShopItemData shopItemData){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String pricingsName[] = new String[shopItemData.getPricings().size()];
        for(int i=0;i<shopItemData.getPricings().size();i++){
            ShopPricingData curr = shopItemData.getPricings().get(i);
            pricingsName[i] = curr.getType() + "-" + curr.getPrice();
        }

        builder.setItems(pricingsName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ArrayList<ShopPricingData> newPricings = new ArrayList<>();
                newPricings.add(shopItemData.getPricings().get(i));
                shopItemData.setPricings(newPricings);

                orderItems.add(shopItemData);
//                updateBillingdetails();
                orderMenuItemsAdapter.replaceItems(orderItems);
            }
        });

        builder.show();

    }

    private void shopAllShopsDialog(List<ShopData> shopDataList){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Shop");

        String[] shopNames = new String[shopDataList.size()];

        for(int i=0;i<shopDataList.size();i++){
            shopNames[i] = shopDataList.get(i).getName();
        }

        builder.setItems(shopNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                shopData = shopDataList.get(i);
                shopNameEditText.setText(shopData.getName());
                currOrderItem.setShopID(shopData.get_id());
                currOrderItem.setShopCategory(shopData.getShopType());
                getShopMenuItems(shopData.getMenuItemsID());
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void getShopMenuItems(String shopMenuItemsId){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<List<ShopItemData>> call = networkAPI.getShopMenuItems(shopMenuItemsId);
        call.enqueue(new Callback<List<ShopItemData>>() {
            @Override
            public void onResponse(Call<List<ShopItemData>> call, Response<List<ShopItemData>> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getApplicationContext(),errorResponse.getDesc(),Toasty.LENGTH_LONG)
                            .show();
                    return;
                }

                allShopItems = response.body();
                Toasty.success(getApplicationContext(),"Menu Items Loaded..",Toasty.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onFailure(Call<List<ShopItemData>> call, Throwable t) {
                Toasty.error(getApplicationContext(),t.getMessage(),Toasty.LENGTH_SHORT)
                        .show();
            }
        });

    }

}