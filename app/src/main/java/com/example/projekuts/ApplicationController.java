package com.example.projekuts;

import android.app.Application;
import android.util.Log;

import com.example.projekuts.data.AppDatabase;

public class ApplicationController extends Application {
    private static final String TAG = "ApplicationController";
    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            database = AppDatabase.getInstance(this);
            Log.d(TAG, "Database initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Database initialization failed", e);
        }
    }

    public static AppDatabase getDatabase() {
        if (database == null) {
            throw new IllegalStateException("Database belum siap!");
        }
        return database;
    }
}
