package com.avit.apnamzpadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.avit.apnamzpadmin.ui.adminshopservice.AdminShopServiceActivity;
import com.avit.apnamzpadmin.ui.bannerimageservice.BannerImageActivity;
import com.avit.apnamzpadmin.ui.bulknotification.BulkNotificationActivity;
import com.avit.apnamzpadmin.ui.createshop.CreateShopActivity;
import com.avit.apnamzpadmin.ui.deliverysathistatus.DeliverySathisStatusActivity;
import com.avit.apnamzpadmin.ui.directorder.DirectOrderActivity;
import com.avit.apnamzpadmin.ui.getorderservice.GetOrdersActivity;
import com.avit.apnamzpadmin.ui.reviewservice.ReviewService;
import com.avit.apnamzpadmin.ui.userappservices.UserAppServiceActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.admin_shop_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminShopServiceIntent = new Intent(getApplicationContext(), AdminShopServiceActivity.class);
                startActivity(adminShopServiceIntent);
            }
        });

        findViewById(R.id.delivery_sathis_status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deliverySathiStatusIntent = new Intent(getApplicationContext(), DeliverySathisStatusActivity.class);
                startActivity(deliverySathiStatusIntent);
            }
        });

        findViewById(R.id.search_orders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchOrdersIntent = new Intent(getApplicationContext(), GetOrdersActivity.class);
                startActivity(searchOrdersIntent);
            }
        });

        findViewById(R.id.user_services).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userServicesIntent = new Intent(getApplicationContext(), UserAppServiceActivity.class);
                startActivity(userServicesIntent);
            }
        });

        findViewById(R.id.apna_reviews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent apnaReviews = new Intent(getApplicationContext(), ReviewService.class);
                startActivity(apnaReviews);
            }
        });

        findViewById(R.id.direct_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent directOrder = new Intent(getApplicationContext(), DirectOrderActivity.class);
                startActivity(directOrder);
            }
        });

        findViewById(R.id.create_shop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createShop = new Intent(getApplicationContext(), CreateShopActivity.class);
                startActivity(createShop);
            }
        });

        findViewById(R.id.banner_images).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bannerImages = new Intent(getApplicationContext(), BannerImageActivity.class);
                startActivity(bannerImages);
            }
        });

        findViewById(R.id.bulk_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bulkNotification = new Intent(getApplicationContext(), BulkNotificationActivity.class);
                startActivity(bulkNotification);
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("pending_orders");

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.i(TAG, "onComplete: " + token);
                    }
                });

    }
}