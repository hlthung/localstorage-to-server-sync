package com.example.myapplication.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                Log.i("[TEST]", "WiFi connected");
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Log.i("[TEST]", "Mobile data connected");
            }
            Log.i("[TEST]", "Syncing data for network connection");
            // Start the sync service
            Intent syncIntent = new Intent(context, SyncService.class);
            context.startService(syncIntent);
        }
    }
}