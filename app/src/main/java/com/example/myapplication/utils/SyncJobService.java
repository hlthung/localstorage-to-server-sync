package com.example.myapplication.utils;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class SyncJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        SyncData syncData = new SyncData(getApplicationContext());
        syncData.execute();
        return false; // Job is not recurring
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false; // Job should not be retried
    }
}