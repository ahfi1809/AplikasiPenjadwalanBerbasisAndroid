package com.example.projekuts.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.projekuts.R;
import com.example.projekuts.model.UserActivity;
import com.example.projekuts.utils.NotificationHelper;
import com.example.projekuts.viewmodel.ActivityViewModel;

import java.util.Calendar;
import java.util.Locale;

public class AddEditActivity extends AppCompatActivity {
    private EditText etTitle, etDescription, etDate, etTime;
    private ActivityViewModel viewModel;
    private int activityId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        viewModel = new ViewModelProvider(this).get(ActivityViewModel.class);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnDelete = findViewById(R.id.btnDelete);

        etDate.setOnClickListener(v -> showDatePicker());
        etTime.setOnClickListener(v -> showTimePicker());

        btnSave.setOnClickListener(v -> saveData());
        btnDelete.setOnClickListener(v -> deleteActivity());

        activityId = getIntent().getIntExtra("activity_id", -1);
        if (activityId != -1) {
            loadActivity();
        }
    }

    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) ->
                etDate.setText(String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth)),
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker() {
        Calendar cal = Calendar.getInstance();
        new TimePickerDialog(this, (view, hourOfDay, minute) ->
                etTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)),
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
    }

    private void saveActivity() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String time = etTime.getText().toString().trim();

        if (title.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Title, date, and time are required", Toast.LENGTH_SHORT).show();
            return;
        }

        UserActivity activity = new UserActivity();
        activity.title = title;
        activity.description = description;
        activity.date = date;
        activity.time = time;

        if (activityId != -1) {
            activity.id = activityId;
            viewModel.update(activity, this::finish);
        } else {
            viewModel.insert(activity, this::finish);
        }
    }

    private void deleteActivity() {
        if (activityId != -1) {
            viewModel.getAllActivities().observe(this, activities -> {
                for (UserActivity activity : activities) {
                    if (activity.id == activityId) {
                        viewModel.delete(activity, this::finish);
                        break;
                    }
                }
            });
        }
    }


    private void loadActivity() {
        viewModel.getAllActivities().observe(this, activities -> {
            for (UserActivity activity : activities) {
                if (activity.id == activityId) {
                    etTitle.setText(activity.title);
                    etDescription.setText(activity.description);
                    etDate.setText(activity.date);
                    etTime.setText(activity.time);
                    break;
                }
            }
        });
    }
    private void saveData() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String time = etTime.getText().toString().trim();

        if (title.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        UserActivity activity = new UserActivity();
        activity.title = title;
        activity.description = description;
        activity.date = date;
        activity.time = time;

        if (activityId != -1) {
            activity.id = activityId;
            viewModel.update(activity, this::finish);
        } else {
            viewModel.insert(activity, () -> {
                NotificationHelper.scheduleNotification(this, activity); // Set Alarm untuk notifikasi nanti
                finish();
            });
        }
    }
    private void sendImmediateNotification(UserActivity activity) {
        NotificationHelper.createNotificationChannel(this);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "activity_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(activity.title)
                .setContentText(activity.description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

}
