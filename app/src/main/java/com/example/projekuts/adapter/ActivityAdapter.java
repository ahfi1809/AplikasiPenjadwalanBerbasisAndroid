package com.example.projekuts.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekuts.R;
import com.example.projekuts.model.UserActivity;
import com.example.projekuts.ui.AddEditActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private final Context context;
    private final List<UserActivity> activities = new ArrayList<>();

    public ActivityAdapter(Context context) {
        this.context = context;
    }

    public void setActivities(List<UserActivity> list) {
        activities.clear();
        if (list != null) {
            activities.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserActivity activity = activities.get(position);
        holder.bind(activity);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditActivity.class);
            intent.putExtra("activity_id", activity.id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvDateTime;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
        }

        void bind(UserActivity activity) {
            tvTitle.setText(activity.title);
            tvDateTime.setText(String.format("%s %s", activity.date, activity.time));
        }
    }
}
