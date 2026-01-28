package com.project_vn.sql_masterclass;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PracticeListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PracticeAdapter adapter;
    List<TopicItem> topicItems;
    EditText etSearch; // The Search Bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_list);

        // 1. Initialize Views
        recyclerView = findViewById(R.id.rvPractice);
        etSearch = findViewById(R.id.etSearch); // Link to the XML search box

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2. Load Data
        updateList();

        // 3. Add Search Listener (Logic)
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // As you type, this runs
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filter(String text) {
        List<TopicItem> filteredList = new ArrayList<>();

        for (TopicItem item : topicItems) {
            // Logic: If the item title matches the search text...
            if (item.title.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        // Send the smaller list to the adapter
        if (adapter != null) {
            adapter.setFilteredList(filteredList);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter != null) adapter.notifyDataSetChanged();
    }

    private void updateList() {
        topicItems = new ArrayList<>();

        // --- SECTION 1: BASICS ---
        topicItems.add(new TopicItem("1. Core Concepts"));
        topicItems.add(new TopicItem("SELECT Statement", "", ""));
        topicItems.add(new TopicItem("SELECT DISTINCT", "", ""));
        topicItems.add(new TopicItem("WHERE Clause", "", ""));
        topicItems.add(new TopicItem("ORDER BY", "", ""));

        // --- SECTION 2: FILTERS ---
        topicItems.add(new TopicItem("2. Advanced Filtering"));
        topicItems.add(new TopicItem("AND, OR, NOT", "", ""));
        topicItems.add(new TopicItem("LIKE (Wildcards)", "", ""));
        topicItems.add(new TopicItem("IN Operator", "", ""));

        // --- SECTION 3: MODIFIERS ---
        topicItems.add(new TopicItem("3. Modifying Data"));
        topicItems.add(new TopicItem("INSERT INTO", "", ""));
        topicItems.add(new TopicItem("UPDATE", "", ""));
        topicItems.add(new TopicItem("DELETE", "", ""));

        // --- SECTION 4: AGGREGATES ---
        topicItems.add(new TopicItem("4. Aggregates"));
        topicItems.add(new TopicItem("MIN() and MAX()", "", ""));
        topicItems.add(new TopicItem("COUNT, AVG, SUM", "", ""));
        topicItems.add(new TopicItem("GROUP BY", "", ""));

        // --- SECTION 5: JOINS ---
        topicItems.add(new TopicItem("5. Joining Tables"));
        topicItems.add(new TopicItem("INNER JOIN", "", ""));
        topicItems.add(new TopicItem("LEFT JOIN", "", ""));

        // --- SECTION 6: STRUCTURE ---
        topicItems.add(new TopicItem("6. Database Structure"));
        topicItems.add(new TopicItem("CREATE TABLE", "", ""));
        topicItems.add(new TopicItem("DROP TABLE", "", ""));

        adapter = new PracticeAdapter(this, topicItems);
        recyclerView.setAdapter(adapter);
    }
}