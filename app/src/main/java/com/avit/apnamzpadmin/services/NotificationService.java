package com.avit.apnamzpadmin.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import com.avit.apnamzpadmin.ui.adminshopservice.AdminShopServiceActivity;
import com.avit.apnamzpadmin.utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {
    
    private String TAG = "NotificationService";
    public static final String CHANNEL_ORDER_ID = "OrdersStatusChannel";
    public static final String CHANNEL_OFFERS_ID = "OffersChannel";
    public static final String CHANNEL_NEWS_ID = "NewsChannel";
    private NotificationManagerCompat notificationManager;
    public static int ORDER_NOTIFICATION_ID = 1;
    public static int NEWS_NOTIFICATION_ID = 101;
    
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

    public void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel orderStatusChannel = new NotificationChannel(CHANNEL_ORDER_ID,"Orders Status Notification Channel", NotificationManager.IMPORTANCE_HIGH);
            orderStatusChannel.setDescription("This channel is responsible for all the notification regarding your order status.");

            NotificationChannel offersChannel = new NotificationChannel(CHANNEL_OFFERS_ID,"Offers Notification Channel", NotificationManager.IMPORTANCE_HIGH);
            orderStatusChannel.setDescription("This channel is responsible for all the notification regarding new exclusive offers on ApnaMZP");

            NotificationChannel newsChannel = new NotificationChannel(CHANNEL_NEWS_ID,"News Notification Channel", NotificationManager.IMPORTANCE_HIGH);
            orderStatusChannel.setDescription("This channel is responsible for all the notification regarding what's new and recommendations");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(orderStatusChannel);
            manager.createNotificationChannel(offersChannel);
            manager.createNotificationChannel(newsChannel);

        }
    }



}