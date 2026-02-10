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

public class DetailActivity extends AppCompatActivity {

    LinearLayout tableHeader;
    LinearLayout tableBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_detail);

        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvDesc = findViewById(R.id.tvDetailDesc);
        TextView tvCode = findViewById(R.id.tvDetailCode);
        tableHeader = findViewById(R.id.tableHeader);
        tableBody = findViewById(R.id.tableBody);

        Button btnMenu = findViewById(R.id.btnMenu);
        Button btnTryIt = findViewById(R.id.btnTryIt);

        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("desc");
        String code = getIntent().getStringExtra("code");

        tvTitle.setText(title);
        tvDesc.setText(desc);
        tvCode.setText(code);

        tableHeader.removeAllViews();
        tableBody.removeAllViews();

        // ========================== LOGIC FOR EACH TOPIC ==========================
        if (title.contains("SELECT DISTINCT")) {
            addHeader("Country");
            addRow("India");
            addRow("Singapore");
            addRow("UK");
            addRow("UAE");

        } else if (title.equals("SELECT Statement")) {
            addHeader("ID", "Name", "City");
            addRow("1", "Rohit", "Mumbai");
            addRow("2", "Anjali", "Delhi");
            addRow("3", "Rajesh", "Singapore");

        } else if (title.contains("WHERE")) {
            addHeader("ID", "Name", "City");
            addRow("4", "Priya", "London");
            addRow("5", "Amit", "Dubai");

        } else if (title.contains("AND") || title.contains("OR")) {
            addHeader("ID", "Name", "Country");
            addRow("1", "Rohit", "India");
            addRow("2", "Anjali", "India");
            addRow("4", "Priya", "UK");

        } else if (title.contains("LIKE")) {
            addHeader("Customer");
            addRow("Sharma Ent.");
            addRow("Singh Tech");

        } else if (title.contains("IN ")) {
            addHeader("Country");
            addRow("India");
            addRow("Singapore");
            addRow("UK");

        } else if (title.contains("INSERT")) {
            addHeader("ID", "Name", "City");
            addRow("6", "New User", "Pune");
            addRow("7", "Tech Corp", "Bangalore");

        } else if (title.contains("UPDATE")) {
            addHeader("ID", "Name", "City");
            addRow("1", "Rohit Sharma", "Pune");

        } else if (title.contains("DELETE")) {
            addHeader("ID", "Name");
            addRow("2", "Mehta Tex.");

        } else if (title.contains("MIN") || title.contains("MAX")) {
            addHeader("Product", "Price");
            addRow("Milk", "50.00");
            addRow("Cheese", "250.00");
            addRow("Bread", "40.00");

        } else if (title.contains("COUNT")) {
            addHeader("Metric", "Value");
            addRow("Total Orders", "3");

        } else if (title.contains("SUM") || title.contains("AVG")) {
            addHeader("Metric", "Value");
            addRow("Total Price", "340.00");

        } else if (title.contains("LEFT JOIN")) {
            addHeader("Customer", "OrderID");
            addRow("Rohit", "101");
            addRow("Anjali", "102");
            addRow("Rajesh", "NULL");

        } else if (title.contains("INNER JOIN")) {
            addHeader("OrderID", "Customer");
            addRow("101", "Rohit");
            addRow("102", "Anjali");

        } else if (title.contains("GROUP")) {
            addHeader("Country", "Total");
            addRow("India", "300");
            addRow("UK", "150");

        } else if (title.contains("CREATE TABLE")) {
            addHeader("Action", "Table");
            addRow("Created", "Persons");
            addRow("Cols", "ID, Name");

        } else if (title.contains("DROP TABLE")) {
            addHeader("Action", "Table");
            addRow("Dropped", "Persons");
            addRow("Status", "Deleted");

        } else {
            // DEFAULT FALLBACK
            addHeader("ID", "Name", "Status");
            addRow("1", "Sample", "Result");
            addRow("2", "Example", "Output");
        }

        // MENU BUTTON
        btnMenu.setOnClickListener(v -> finish());

        // TRY IT BUTTON
        btnTryIt.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, PlaygroundActivity.class);
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


