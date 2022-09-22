package com.avit.apnamzpadmin.ui.editshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.shop.ShopData;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.utils.ErrorUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditShopService extends AppCompatActivity {

    private ShopData shopData;
    private SearchView searchView;
    private EditShopViewModel viewModel;
    private TextInputEditText shopNameView;
    private Switch isOpenView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shop_service);
        viewModel = new ViewModelProvider(this).get(EditShopViewModel.class);

        searchView = findViewById(R.id.search_bar);
        shopNameView = findViewById(R.id.shopName);
        isOpenView = findViewById(R.id.isOpen);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.searchShop(getApplicationContext(),query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        viewModel.getMutableShopLiveData().observe(this, new Observer<List<ShopData>>() {
            @Override
            public void onChanged(List<ShopData> shopDataList) {
                shopAllShopsDialog(shopDataList);
            }
        });

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeShopStatus();
            }
        });

    }

    private void changeShopStatus(){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<NetworkResponse> call = networkAPI.changeShopStatus(shopData.getPhoneNO(),isOpenView.isChecked());
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getApplicationContext(),errorResponse.getDesc(),Toasty.LENGTH_LONG)
                            .show();
                    return;
                }

                Toasty.success(getApplicationContext(),"Changed Successfully",Toasty.LENGTH_LONG)
                        .show();
                finish();

            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                Toasty.success(getApplicationContext(),t.getMessage(),Toasty.LENGTH_LONG)
                        .show();
            }
        });

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
                shopNameView.setText(shopData.getName());
                isOpenView.setChecked(shopData.isOpen());
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}