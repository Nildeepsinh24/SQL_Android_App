package com.example.sql_masterclass;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LearnAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<TopicItem> items;
    private final Context context;

    public LearnAdapter(Context context, List<TopicItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).isHeader ? 0 : 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View v = LayoutInflater.from(context).inflate(R.layout.list_item_header, parent, false);
            return new HeaderViewHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false);
            return new TopicViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TopicItem item = items.get(position);

        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).headerTitle.setText(item.title);
        } else if (holder instanceof TopicViewHolder) {
            TopicViewHolder topicHolder = (TopicViewHolder) holder;
            topicHolder.topicTitle.setText(item.title);

            SharedPreferences prefs = context.getSharedPreferences("SQL_PROGRESS", Context.MODE_PRIVATE);
            int progress = prefs.getInt(item.title, 0);
            topicHolder.tvPercent.setText(progress + "%");

            topicHolder.tvPercent.setVisibility(View.GONE);

            topicHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title", item.title);
                intent.putExtra("desc", item.description);
                intent.putExtra("code", item.code);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitle;
        HeaderViewHolder(View v) {
            super(v);
            headerTitle = v.findViewById(R.id.tvHeaderTitle);
        }
    }

    static class TopicViewHolder extends RecyclerView.ViewHolder {
        TextView topicTitle, tvPercent;
        TopicViewHolder(View v) {
            super(v);
            topicTitle = v.findViewById(R.id.tvTopicName);
            tvPercent = v.findViewById(R.id.tvPercentage);
        }
    }
}

