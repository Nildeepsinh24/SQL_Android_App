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
    TextView tvTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_detail);

        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvDesc = findViewById(R.id.tvDetailDesc);
        TextView tvCode = findViewById(R.id.tvDetailCode);
        tableHeader = findViewById(R.id.tableHeader);
        tvTable = findViewById(R.id.tvExampleTables);

        Button btnMenu = findViewById(R.id.btnMenu);
        Button btnTryIt = findViewById(R.id.btnTryIt);

        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("desc");
        String code = getIntent().getStringExtra("code");

        tvTitle.setText(title);
        tvDesc.setText(desc);
        tvCode.setText(code);

        tableHeader.removeAllViews();
        StringBuilder content = new StringBuilder();

        // ========================== LOGIC FOR EACH TOPIC ==========================
        if (title.contains("SELECT DISTINCT")) {
            addHeader("Country");
            content.append(row("London"));
            content.append(row("Tokyo"));
            content.append(row("Paris"));

        } else if (title.contains("SELECT")) {
            addHeader("ID", "Name", "City");
            content.append(row("1", "Alex", "London"));
            content.append(row("2", "Sarah", "Tokyo"));
            content.append(row("3", "John", "Paris"));

        } else if (title.contains("WHERE")) {
            addHeader("ID", "Name", "Status");
            content.append(row("5", "Mike", "Active"));
            content.append(row("7", "Anna", "Active"));

        } else if (title.contains("AND") || title.contains("AND, OR, NOT")) {
            addHeader("ID", "Name", "City");
            content.append(row("1", "Alex", "London"));
            content.append(row("3", "John", "London"));

        } else if (title.contains("OR")) {
            addHeader("ID", "Name", "City");
            content.append(row("2", "Sarah", "Tokyo"));
            content.append(row("4", "Emma", "Paris"));

        } else if (title.contains("NOT")) {
            addHeader("ID", "Name", "City");
            content.append(row("6", "Chris", "Berlin"));
            content.append(row("8", "Laura", "Rome"));

        } else if (title.contains("LIKE")) {
            addHeader("Customer");
            content.append(row("Alfreds"));
            content.append(row("Ana Trujillo"));

        } else if (title.contains("IN ")) {
            addHeader("Country");
            content.append(row("Germany"));
            content.append(row("France"));
            content.append(row("UK"));

        } else if (title.contains("INSERT")) {
            addHeader("ID", "Name", "City");
            content.append(row("1", "Tom", "-"));
            content.append(row("2", "Cardinal", "Stavanger"));

        } else if (title.contains("UPDATE")) {
            addHeader("ID", "Name", "City");
            content.append(row("1", "Alfred Schmidt", "Frankfurt"));

        } else if (title.contains("DELETE")) {
            addHeader("ID", "Name");
            content.append(row("2", "Maria"));

        } else if (title.contains("MIN")) {
            addHeader("Product", "Price");
            content.append(row("Milk", "1.10"));

        } else if (title.contains("MAX")) {
            addHeader("Product", "Price");
            content.append(row("Bread", "2.50"));

        } else if (title.contains("COUNT")) {
            addHeader("Metric", "Value");
            content.append(row("Total Orders", "2"));

        } else if (title.contains("SUM")) {
            addHeader("Metric", "Value");
            content.append(row("Total Amount", "300"));

        } else if (title.contains("JOIN")) {
            addHeader("OrderID", "Customer");
            content.append(row("101", "Alex"));
            content.append(row("102", "Sarah"));

        } else if (title.contains("LEFT JOIN")) {
            addHeader("Customer", "OrderID");
            content.append(row("Alex", "101"));
            content.append(row("Sarah", "102"));
            content.append(row("John", "NULL"));

        } else if (title.contains("GROUP")) {
            addHeader("Country", "Total");
            content.append(row("USA", "300"));
            content.append(row("UK", "300"));

        } else if (title.contains("CREATE TABLE")) {
            addHeader("Action", "Table");
            content.append(row("Created", "Persons"));

        } else if (title.contains("DROP TABLE")) {
            addHeader("Action", "Table");
            content.append(row("Dropped", "Persons"));

        } else {
            // DEFAULT FALLBACK
            addHeader("ID", "Name", "Status");
            content.append(row("1", "Sample", "Result"));
            content.append(row("2", "Example", "Output"));
        }

        // SET DATA TO TEXTVIEW
        tvTable.setText(content.toString());

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
            tv.setText(String.format("%-15s", title));
            tv.setTextColor(Color.WHITE);
            tv.setTypeface(Typeface.DEFAULT_BOLD);
            tv.setTextSize(14);
            tv.setPadding(0, 0, 0, 0);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1
            );
            tv.setLayoutParams(params);
            tableHeader.addView(tv);
        }
    }

    private String row(String... cols) {
        StringBuilder sb = new StringBuilder();
        for (String col : cols) {
            sb.append(String.format("%-15s", col));
        }
        sb.append("\n");
        return sb.toString();
    }
}


