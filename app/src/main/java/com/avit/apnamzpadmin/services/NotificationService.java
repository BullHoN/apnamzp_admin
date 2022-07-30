package com.avit.apnamzpadmin.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.avit.apnamzpadmin.ui.adminshopservice.AdminShopServiceActivity;
import com.avit.apnamzpadmin.utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {
    
    private String TAG = "NotificationService";
    
    public NotificationService() {
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.i(TAG, "onNewToken: ");
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        handleNewNotification();
    }

    private void handleNewNotification(){
        NotificationUtils.playSound(getApplicationContext());

        Intent intent = new Intent(getApplicationContext(), AdminShopServiceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("com.avit.apnamzp_admin.PENDING_ORDER_NOTIFICATION");
        startActivity(intent);

    }

}