package com.project_vn.sql_masterclass;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LearnDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_detail);

        // ===== LINK VIEWS =====
        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvDesc = findViewById(R.id.tvDetailDesc);
        TextView tvCode = findViewById(R.id.tvDetailCode);
        TextView tvTable = findViewById(R.id.tvExampleTables);
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

        // ===== TABLE OUTPUT FOR ALL TOPICS =====
        if (title != null) {

            if (title.contains("SELECT")) {
                tvTable.setText(
                        "1   Alex    London\n" +
                                "2   Sarah   Tokyo\n" +
                                "3   John    Paris"
                );

            } else if (title.contains("DISTINCT")) {
                tvTable.setText(
                        "London\n" +
                                "Tokyo\n" +
                                "Paris"
                );

            } else if (title.contains("WHERE")) {
                tvTable.setText(
                        "5   Mike    Active\n" +
                                "7   Anna    Active"
                );

            } else if (title.contains("AND")) {
                tvTable.setText(
                        "1   Alex    London\n" +
                                "3   John    London"
                );

            } else if (title.contains("OR")) {
                tvTable.setText(
                        "2   Sarah   Tokyo\n" +
                                "4   Emma    Paris"
                );

            } else if (title.contains("NOT")) {
                tvTable.setText(
                        "6   Chris   Berlin\n" +
                                "8   Laura   Rome"
                );

            } else if (title.contains("LIKE")) {
                tvTable.setText(
                        "Alfreds\n" +
                                "Ana Trujillo"
                );

            } else if (title.contains("INSERT")) {
                tvTable.setText(
                        "1   Tom\n" +
                                "2   Cardinal   Stavanger"
                );

            } else if (title.contains("UPDATE")) {
                tvTable.setText(
                        "1   Alfred Schmidt   Frankfurt"
                );

            } else if (title.contains("DELETE")) {
                tvTable.setText(
                        "2   Maria"
                );

            } else if (title.contains("MIN")) {
                tvTable.setText(
                        "Milk   1.10"
                );

            } else if (title.contains("MAX")) {
                tvTable.setText(
                        "Bread   2.50"
                );

            } else if (title.contains("COUNT")) {
                tvTable.setText(
                        "Total Orders   2"
                );

            } else if (title.contains("SUM")) {
                tvTable.setText(
                        "Total Amount   300"
                );

            } else if (title.contains("JOIN")) {
                tvTable.setText(
                        "101   Alex\n" +
                                "102   Sarah"
                );

            } else if (title.contains("GROUP")) {
                tvTable.setText(
                        "USA   300\n" +
                                "UK    300"
                );

            } else if (title.contains("CREATE")) {
                tvTable.setText(
                        "Table   Persons   Created"
                );

            } else if (title.contains("DROP")) {
                tvTable.setText(
                        "Table   Persons   Dropped"
                );

            } else {
                tvTable.setText(
                        "1   Sample   Result\n" +
                                "2   Example  Output"
                );
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
}
