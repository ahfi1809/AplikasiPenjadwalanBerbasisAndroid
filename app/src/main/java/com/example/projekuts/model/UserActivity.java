package com.example.projekuts.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "activities")
public class UserActivity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String description;
    public String date;  // Format: yyyy-MM-dd
    public String time;  // Format: HH:mm
}
