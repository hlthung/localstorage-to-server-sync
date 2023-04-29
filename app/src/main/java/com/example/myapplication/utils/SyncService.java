package com.example.myapplication.utils;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.MainActivity;

public class SyncService extends IntentService {

    Integer delay;
    private NotificationManager mgr;

    public SyncService() {
        super("SyncService");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // This method won't be called since we're handling the intent in the onStartCommand() method
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        performSync();
        startSyncThread();
        mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final NotificationCompat.Builder builder = buildForeground();
        startForeground(1, builder.build());
        return START_STICKY;
    }

    public boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void startSyncThread() {
        Handler handler = new Handler();
        delay = 1000;

        handler.postDelayed(new Runnable() {
            public void run() {
                performSync();
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public void performSync() {
        if (isInternetConnected()) {
            Log.i("SyncService:", "Network connected, start syncing...");
            SyncData syncData = new SyncData(getApplicationContext());
            syncData.execute();
            delay = 60000;
        } else {
            Log.i("SyncService:", "Network IS NOT connected, ABORT syncing...");
            delay = 1000;
        }
    }

    private NotificationCompat.Builder buildForeground() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel for the service notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "channel_id",
                    "Channel Name",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder b = new NotificationCompat.Builder(this, "channel_id");

        b.setContentTitle("App is running")
                .setSmallIcon(android.R.drawable.star_on)
                .setOngoing(true)
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent);

        return (b);
    }
}