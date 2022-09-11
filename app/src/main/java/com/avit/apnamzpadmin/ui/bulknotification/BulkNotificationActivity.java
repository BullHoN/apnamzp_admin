package com.avit.apnamzpadmin.ui.bulknotification;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.models.notification.BulkNotificationPostBody;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.utils.ErrorUtils;
import com.bumptech.glide.Glide;
import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
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

public class BulkNotificationActivity extends AppCompatActivity {

    private Gson gson;
    private BulkNotificationPostBody notificationPostBody;
    private ActivityResultLauncher<Intent> notificationImagePickerLauncher;
    private ImageView notificationImage;
    private TextInputEditText titleView, descView, shopIdView;
    private String notificationTypes[] = {"apnamzp_user","apnamzp_partner"};
    private Spinner notificationTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulk_notification);

        gson = new Gson();
        notificationPostBody = new BulkNotificationPostBody();

        notificationImage = findViewById(R.id.notification_image);
        titleView = findViewById(R.id.notification_title);
        descView = findViewById(R.id.notification_desc);
        shopIdView = findViewById(R.id.notification_shopId);
        notificationTypeSpinner = findViewById(R.id.notification_type_spinner);

        ArrayAdapter bannerSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, notificationTypes);

        bannerSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notificationTypeSpinner.setAdapter(bannerSpinnerAdapter);

        notificationTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                notificationPostBody.setTargetGroup(notificationTypes[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                notificationPostBody.setTargetGroup("apnamzp_user");
            }
        });

        findViewById(R.id.bulk_notification_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title, desc, shopId;

                title =  titleView.getText().toString();
                desc = descView.getText().toString();
                shopId = shopIdView.getText().toString();

                if(title.length() == 0 || desc.length() == 0){
                    return;
                }

                notificationPostBody.setTitle(title);
                notificationPostBody.setDesc(desc);

                if(shopId.length() != 0){
                    notificationPostBody.setShopId(shopId);
                }

                sendBulkNotification(null);
            }
        });

        notificationImagePickerLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Uri imageUri = result.getData().getData();
                        notificationPostBody.setImageUrl(imageUri.getPath());
//                        shopPartner.setBannerImage(imageUri.getPath());
                        // Use the uri to load the image
                        Glide.with(getApplicationContext()).load(imageUri).into(notificationImage);

                    } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                        // Use ImagePicker.Companion.getError(result.getData()) to show an error
                        ImagePicker.Companion.getError(result.getData());
                    }
                });


        findViewById(R.id.notification_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        findViewById(R.id.test_notification_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void pickImage(){
        ImagePicker.Companion.with(this)
                .crop()
                .maxResultSize(500,500,true)
                .provider(ImageProvider.BOTH)
                .createIntentFromDialog((Function1)(new Function1(){
                    public Object invoke(Object var1) {
                        this.invoke((Intent) var1);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(@NotNull Intent it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        notificationImagePickerLauncher.launch(it);
                    }
                }));
    }

    private void sendBulkNotification(String testPhoneNo){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        MultipartBody.Part imagePart = null;
        if(notificationPostBody.getImageUrl() != null){
            File notificationImageFile = new File(notificationPostBody.getImageUrl());
            RequestBody notificationBody = RequestBody.create(MediaType.parse("image/*"),notificationImageFile);
            imagePart = MultipartBody.Part.createFormData("nofication_image",notificationImageFile.getName(),notificationBody);
        }

        RequestBody notificationData = RequestBody.create(MediaType.parse("application/json"),gson.toJson(notificationPostBody));

        Call<NetworkResponse> call = networkAPI.sendBulkNotification(imagePart,notificationData,testPhoneNo);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getApplicationContext(),errorResponse.getDesc(),Toasty.LENGTH_LONG)
                            .show();
                    return;
                }

                Toasty.success(getApplicationContext(),"Notification Send Successfully",Toasty.LENGTH_SHORT)
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

}