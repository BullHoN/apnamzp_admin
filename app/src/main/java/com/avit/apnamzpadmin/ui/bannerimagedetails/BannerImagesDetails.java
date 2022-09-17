package com.avit.apnamzpadmin.ui.bannerimagedetails;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.user.BannerData;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.utils.ErrorUtils;
import com.bumptech.glide.Glide;
import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.google.android.gms.common.util.RetainForClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import es.dmoral.toasty.Toasty;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BannerImagesDetails extends AppCompatActivity {

    private Gson gson;
    private BannerData bannerData;
    private Spinner bannertypesSpinner;
    private ImageView bannerImage;
    private TextInputEditText shopIdText;
    String[] bannertypes = { "display_banner", "open_shop", "open_search" };
    private ActivityResultLauncher<Intent> bannerImagePickerLauncher;
    private String TAG = "BannerImagesDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_images_details);

        gson = new Gson();

        if(getIntent() != null && getIntent().getStringExtra("bannerData") != null){
            bannerData = gson.fromJson(getIntent().getStringExtra("bannerData"),BannerData.class);
            findViewById(R.id.delete).setVisibility(View.VISIBLE);
        }else {
            bannerData = new BannerData();
        }

        bannerImage = findViewById(R.id.banner_image);
        shopIdText = findViewById(R.id.shop_id);
        bannertypesSpinner = findViewById(R.id.banner_type_spinner);
        ArrayAdapter bannerSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bannertypes);

        bannerSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bannertypesSpinner.setAdapter(bannerSpinnerAdapter);

        bannertypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0){
                    shopIdText.setVisibility(View.GONE);
                    bannerData.setAction("display_banner");
                }
                else {
                    shopIdText.setVisibility(View.VISIBLE);
                    bannerData.setAction(bannertypes[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                shopIdText.setVisibility(View.GONE);
                bannerData.setAction("display_banner");
            }
        });

        if(bannerData.getImageURL() != null){
            Glide.with(this)
                    .load(bannerData.getImageURL())
                    .into(bannerImage);
        }

        if(bannerData.getAction() != null){
            if(bannerData.getAction().equals("open_shop")){
                bannertypesSpinner.setSelection(1);
                shopIdText.setVisibility(View.VISIBLE);
            }else {
                bannertypesSpinner.setSelection(0);
                shopIdText.setVisibility(View.GONE);
            }
        }

        if(bannerData.getShopId() != null){
            shopIdText.setText(bannerData.getShopId());
        }

        bannerImagePickerLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Uri imageUri = result.getData().getData();
                        bannerData.setNewImageUrl(imageUri.getPath());
//                        shopPartner.setBannerImage(imageUri.getPath());
                        // Use the uri to load the image
                        Glide.with(getApplicationContext()).load(imageUri).into(bannerImage);

                    } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                        // Use ImagePicker.Companion.getError(result.getData()) to show an error
                        ImagePicker.Companion.getError(result.getData());
                    }
                });


        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(bannerData.getNewImageUrl() == null && bannerData.getImageURL() == null){
                    Toasty.error(getApplicationContext(),"Please Select an Image",Toasty.LENGTH_LONG)
                            .show();
                    return;
                }

                if(bannerData.getAction().equals("open_shop") && shopIdText.getText().toString().length() == 0){
                    Toasty.error(getApplicationContext(),"Enter Valid Shop Id",Toasty.LENGTH_LONG)
                            .show();
                    return;
                }

                if(bannerData.getAction().equals("open_shop")){
                    bannerData.setShopId(shopIdText.getText().toString());
                }

                saveBannerImage("add");
            }
        });


        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBannerImage("del");
            }
        });

    }

    private void saveBannerImage(String action){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        MultipartBody.Part bannerImagePart = null;
        if(bannerData.getNewImageUrl() != null && action.equals("add") && !bannerData.getNewImageUrl().contains("http")){
            File file = new File(bannerData.getNewImageUrl());
            RequestBody bannerImageRequestBody = RequestBody.create(MediaType.parse("image/*"),file);
            bannerImagePart = MultipartBody.Part.createFormData("banner_image", file.getName(),bannerImageRequestBody);
        }

        RequestBody bannerDataBody = RequestBody.create(MediaType.parse("application/json"),gson.toJson(bannerData));

        Call<NetworkResponse> call = networkAPI.updateBannerImages(bannerImagePart,bannerDataBody,action);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getApplicationContext(),errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                Toasty.success(getApplicationContext(),"Update Successfull",Toasty.LENGTH_SHORT)
                        .show();
                finish();

            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                Toasty.error(getApplicationContext(),t.getMessage(),Toasty.LENGTH_LONG)
                        .show();
            }
        });

    }

    private void pickImage(){
        ImagePicker.Companion.with(this)
                .crop(9f,5f)
                .maxResultSize(500,500,true)
                .provider(ImageProvider.BOTH)
                .createIntentFromDialog((Function1)(new Function1(){
                    public Object invoke(Object var1) {
                        this.invoke((Intent) var1);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(@NotNull Intent it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        bannerImagePickerLauncher.launch(it);
                    }
                }));
    }

}