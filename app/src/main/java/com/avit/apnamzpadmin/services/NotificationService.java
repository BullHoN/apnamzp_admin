package com.avit.apnamzpadmin.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.ui.adminshopservice.AdminShopServiceActivity;
import com.avit.apnamzpadmin.ui.reviewservice.ReviewService;
import com.avit.apnamzpadmin.utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {
    
    private String TAG = "NotificationServices";
    public static final String CHANNEL_ORDER_ID = "OrdersStatusChannel";
    public static final String CHANNEL_OFFERS_ID = "OffersChannel";
    public static final String CHANNEL_NEWS_ID = "NewsChannel";
    private NotificationManagerCompat notificationManager;
    public static int ORDER_NOTIFICATION_ID = 1;
    public static int OFFERS_NOTIFICATION_ID = 301;
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

        notificationManager = NotificationManagerCompat.from(getApplicationContext());
        createNotificationChannels();

        String type = remoteMessage.getData().get("type");

        Log.i(TAG, "onMessageReceived: " + type);
        if(type != null && type.equals("review_created")){
            showReviewsNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("desc"));
        }
        else if(type != null && type.contains("subscription")){
            showSubscriptionNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("desc"));
        }
        else if(type != null && type.contains("order_alerts")){
            showSubscriptionNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("desc"));
        }
        else {
            handleNewNotification();
        }
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

            NotificationChannel offersChannel = new NotificationChannel(CHANNEL_OFFERS_ID,"Reviews Notification Channel", NotificationManager.IMPORTANCE_HIGH);
            orderStatusChannel.setDescription("This channel is responsible for all the notification regarding Reviews on ApnaMZP");

            NotificationChannel newsChannel = new NotificationChannel(CHANNEL_NEWS_ID,"News Notification Channel", NotificationManager.IMPORTANCE_HIGH);
            orderStatusChannel.setDescription("This channel is responsible for all the notification regarding what's new and recommendations");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(orderStatusChannel);
            manager.createNotificationChannel(offersChannel);
            manager.createNotificationChannel(newsChannel);

        }
    }

    private void showSubscriptionNotification(String title, String desc){
//        Intent viewReviewsIntent = new Intent(getApplicationContext(), ReviewService.class);
//        viewReviewsIntent.setAction("com.avit.apnamzp_review_created");
//
//        PendingIntent pendingIntent;
//        if (android.os.Build.VERSION.SDK_INT >= 31) {
//            pendingIntent = PendingIntent.getActivity
//                    (this, 0, viewReviewsIntent, PendingIntent.FLAG_IMMUTABLE);
//        }
//        else
//        {
//            pendingIntent =  PendingIntent.getActivity
//                    (this,0,viewReviewsIntent,PendingIntent.FLAG_UPDATE_CURRENT);
//        }


        Notification notification = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_OFFERS_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.removed_bg_main_icon)
                .setContentText(desc)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(desc))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .build();

        if(OFFERS_NOTIFICATION_ID > 1073741824){
            OFFERS_NOTIFICATION_ID = 0;
        }

        notificationManager.notify(OFFERS_NOTIFICATION_ID++,notification);
    }

    private void showReviewsNotification(String title, String desc){
        Intent viewReviewsIntent = new Intent(getApplicationContext(), ReviewService.class);
        viewReviewsIntent.setAction("com.avit.apnamzp_review_created");

        PendingIntent pendingIntent;
        if (android.os.Build.VERSION.SDK_INT >= 31) {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, viewReviewsIntent, PendingIntent.FLAG_IMMUTABLE);
        }
        else
        {
            pendingIntent =  PendingIntent.getActivity
                    (this,0,viewReviewsIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        }


        Notification notification = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_OFFERS_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.removed_bg_main_icon)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .build();

        if(OFFERS_NOTIFICATION_ID > 1073741824){
            OFFERS_NOTIFICATION_ID = 0;
        }

        notificationManager.notify(OFFERS_NOTIFICATION_ID++,notification);
    }

}