package com.example.projekuts.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projekuts.data.AppDatabase;
import com.example.projekuts.data.ActivityDao;
import com.example.projekuts.model.UserActivity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivityRepository {
    private final ActivityDao activityDao;
    private final LiveData<List<UserActivity>> allActivities;
    private final ExecutorService executorService;

    public ActivityRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        activityDao = db.activityDao();
        allActivities = activityDao.getAllActivities();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Untuk mendapatkan semua aktivitas dalam bentuk LiveData
    public LiveData<List<UserActivity>> getAllActivities() {
        return allActivities;
    }

    // Untuk mendapatkan 1 aktivitas berdasarkan id
    public LiveData<UserActivity> getActivityById(int id) {
        return activityDao.getActivityById(id);
    }

    // Insert data di background thread
    public void insert(UserActivity activity, Runnable onComplete) {
        executorService.execute(() -> {
            activityDao.insert(activity);
            if (onComplete != null) onComplete.run();
        });
    }

    // Update data di background thread
    public void update(UserActivity activity, Runnable onComplete) {
        executorService.execute(() -> {
            activityDao.update(activity);
            if (onComplete != null) onComplete.run();
        });
    }

    // Delete data di background thread
    public void delete(UserActivity activity, Runnable onComplete) {
        executorService.execute(() -> {
            activityDao.delete(activity);
            if (onComplete != null) onComplete.run();
        });
    }

    // Matikan ExecutorService saat tidak dipakai lagi (opsional)
    public void shutdown() {
        executorService.shutdown();
    }
}
