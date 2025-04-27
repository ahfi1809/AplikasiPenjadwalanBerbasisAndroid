package com.example.projekuts.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projekuts.model.UserActivity;
import com.example.projekuts.repository.ActivityRepository;

import java.util.List;

public class ActivityViewModel extends AndroidViewModel {
    private final ActivityRepository repository;

    public ActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new ActivityRepository(application);
    }

    public LiveData<List<UserActivity>> getAllActivities() {
        return repository.getAllActivities();
    }

    public void insert(UserActivity activity, Runnable onComplete) {
        repository.insert(activity, onComplete);
    }

    public void update(UserActivity activity, Runnable onComplete) {
        repository.update(activity, onComplete);
    }

    public void delete(UserActivity activity, Runnable onComplete) {
        repository.delete(activity, onComplete);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.shutdown();
    }
}
