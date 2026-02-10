package com.project_vn.sql_masterclass;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LearnDetailActivity extends AppCompatActivity {

    LinearLayout tableHeader;
    LinearLayout tableBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_detail);

        // ===== LINK VIEWS =====
        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvDesc = findViewById(R.id.tvDetailDesc);
        TextView tvCode = findViewById(R.id.tvDetailCode);
        tableHeader = findViewById(R.id.tableHeader);
        tableBody = findViewById(R.id.tableBody);
        
        Button btnMenu = findViewById(R.id.btnMenu);
        Button btnTryIt = findViewById(R.id.btnTryIt);

        // ===== GET INTENT DATA =====
        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("desc");
        String code = getIntent().getStringExtra("code");

        // ===== SET CONTENT =====
        tvTitle.setText(title);
        tvDesc.setText(desc);
        tvCode.setText(code);
        
        tableHeader.removeAllViews();
        tableBody.removeAllViews();

        // ===== TABLE OUTPUT FOR ALL TOPICS =====
        if (title != null) {

            if (title.contains("SELECT") && !title.contains("DISTINCT")) {
                addHeader("ID", "Name", "City");
                addRow("1", "Amit", "Mumbai");
                addRow("2", "Priya", "Delhi");
                addRow("3", "Raj", "Pune");

            } else if (title.contains("DISTINCT")) {
                addHeader("City");
                addRow("Mumbai");
                addRow("Delhi");
                addRow("Pune");

            } else if (title.contains("WHERE")) {
                addHeader("ID", "Name", "Status");
                addRow("5", "Vikram", "Active");
                addRow("7", "Anjali", "Active");

            } else if (title.contains("AND")) {
                addHeader("ID", "Name", "City");
                addRow("1", "Amit", "Mumbai");
                addRow("3", "Raj", "Mumbai");

            } else if (title.contains("OR")) {
                addHeader("ID", "Name", "City");
                addRow("2", "Priya", "Delhi");
                addRow("4", "Sneha", "Bangalore");

            } else if (title.contains("NOT")) {
                addHeader("ID", "Name", "City");
                addRow("6", "Rohan", "Chennai");
                addRow("8", "Meera", "Kolkata");

            } else if (title.contains("LIKE")) {
                addHeader("CompanyName");
                addRow("Sharma Ent.");
                addRow("Sharma Tex.");

            } else if (title.contains("INSERT")) {
                addHeader("ID", "Name", "City");
                addRow("1", "Rohit", "Delhi");
                addRow("2", "Reddy Sol.", "Hyderabad");

            } else if (title.contains("UPDATE")) {
                addHeader("ID", "Name", "City");
                addRow("1", "Amit Sharma", "Pune");

            } else if (title.contains("DELETE")) {
                addHeader("ID", "Name");
                addRow("2", "Mehta Tex.");

            } else if (title.contains("MIN")) {
                addHeader("Product", "Price");
                addRow("Milk", "1.10");

            } else if (title.contains("MAX")) {
                addHeader("Product", "Price");
                addRow("Bread", "2.50");

            } else if (title.contains("COUNT")) {
                addHeader("Metric", "Value");
                addRow("Total Orders", "2");

            } else if (title.contains("SUM")) {
                addHeader("Metric", "Value");
                addRow("Total Amount", "300");

            } else if (title.contains("JOIN")) {
                addHeader("ID", "Name");
                addRow("101", "Amit");
                addRow("102", "Priya");

            } else if (title.contains("GROUP")) {
                addHeader("Country", "Total");
                addRow("India", "300");
                addRow("UK", "300");

            } else if (title.contains("CREATE")) {
                addHeader("Object", "Name", "Action");
                addRow("Table", "Persons", "Created");

            } else if (title.contains("DROP")) {
                addHeader("Object", "Name", "Action");
                addRow("Table", "Persons", "Dropped");

            } else {
                addHeader("ID", "Name", "Result");
                addRow("1", "Sample", "Result");
                addRow("2", "Example", "Output");
            }
        }

        // ===== MENU BUTTON =====
        btnMenu.setOnClickListener(v -> finish());

        // ===== TRY IT IN PLAYGROUND =====
        btnTryIt.setOnClickListener(v -> {
            Intent intent = new Intent(LearnDetailActivity.this, PlaygroundActivity.class);
            intent.putExtra("PRE_FILLED_QUERY", code);
            startActivity(intent);
        });
    }

    // ========================== HELPERS ==========================

    private void addHeader(String... titles) {
        for (String title : titles) {
            TextView tv = new TextView(this);
            tv.setText(title);
            tv.setTextColor(Color.WHITE);
            tv.setTypeface(Typeface.DEFAULT_BOLD);
            tv.setTextSize(14);
            tv.setPadding(8, 16, 8, 16);
            tv.setGravity(android.view.Gravity.CENTER_VERTICAL);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1
            );
            tv.setLayoutParams(params);
            tableHeader.addView(tv);
        }
    }

    private void addRow(String... cols) {
        LinearLayout rowLayout = new LinearLayout(this);
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        rowLayout.setPadding(0, 0, 0, 0);

        for (String col : cols) {
            TextView tv = new TextView(this);
            tv.setText(col);
            tv.setTextColor(Color.parseColor("#334155")); // Slate 700
            tv.setTextSize(14);
            tv.setPadding(8, 20, 8, 20);
            tv.setGravity(android.view.Gravity.CENTER_VERTICAL);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1
            );
            tv.setLayoutParams(params);
            rowLayout.addView(tv);
        }

        // Add a divider
        android.view.View divider = new android.view.View(this);
        divider.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
        divider.setBackgroundColor(Color.parseColor("#F1F5F9")); // Slate 100

        tableBody.addView(rowLayout);
        tableBody.addView(divider);
    }
}
