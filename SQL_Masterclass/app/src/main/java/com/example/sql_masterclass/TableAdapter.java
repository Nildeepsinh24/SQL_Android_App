package com.example.sql_masterclass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.RowHolder> {

    private final List<String[]> rows;

    public TableAdapter(List<String[]> rows) {
        this.rows = rows;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_table_row, parent, false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        String[] row = rows.get(position);
        holder.tvId.setText(row[0]);
        holder.tvName.setText(row[1]);
        holder.tvCity.setText(row[2]);
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    static class RowHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvCity;

        RowHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            tvCity = itemView.findViewById(R.id.tvCity);
        }
    }
}
