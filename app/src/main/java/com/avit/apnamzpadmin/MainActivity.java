package com.avit.apnamzpadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.avit.apnamzpadmin.ui.adminshopservice.AdminShopServiceActivity;
import com.avit.apnamzpadmin.ui.deliverysathistatus.DeliverySathisStatusActivity;
import com.avit.apnamzpadmin.ui.getorderservice.GetOrdersActivity;
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