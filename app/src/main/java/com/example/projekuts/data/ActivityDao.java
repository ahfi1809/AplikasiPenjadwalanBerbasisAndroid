package com.example.projekuts.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.projekuts.model.UserActivity;
import java.util.List;

@Dao
public interface ActivityDao {
    @Insert
    long insert(UserActivity activity);

    @Update
    void update(UserActivity activity);

    @Delete
    void delete(UserActivity activity);

    @Query("SELECT * FROM activities ORDER BY date, time")
    LiveData<List<UserActivity>> getAllActivities(); // Fix pakai LiveData

    @Query("SELECT * FROM activities WHERE id = :id LIMIT 1")
    LiveData<UserActivity> getActivityById(int id);   // Fix pakai LiveData
}
