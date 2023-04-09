package com.example.myapplication.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SyncService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Perform the sync operation here
        SyncData syncData = new SyncData(getApplicationContext());
        syncData.execute();
        // Stop the service when the sync is complete
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}