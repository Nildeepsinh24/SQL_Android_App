package com.example.sql_masterclass;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PlaygroundActivity extends AppCompatActivity {

    EditText etQuery;
    Button btnRun, btnClear;
    TextView tvDbStructure, tvDbTitle;
    TableLayout tableResult;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);

        etQuery = findViewById(R.id.etQuery);
        btnRun = findViewById(R.id.btnRun);
        btnClear = findViewById(R.id.btnClear);
        tableResult = findViewById(R.id.tableResult);
        tvDbStructure = findViewById(R.id.tvDbStructure);
        tvDbTitle = findViewById(R.id.tvDbTitle);

        dbHelper = new DatabaseHelper(this);

        // RECEIVE QUERY FROM LearnDetailActivity (No auto-run)
        String preFilledQuery = getIntent().getStringExtra("PRE_FILLED_QUERY");
        if (preFilledQuery != null && !preFilledQuery.trim().isEmpty()) {
            etQuery.setText(preFilledQuery);
        }

        // LOAD DB STRUCTURE
        new Thread(() -> {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            StringBuilder builder = new StringBuilder();

            Cursor tables = db.rawQuery(
                    "SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'android_%'",
                    null
            );

            while (tables.moveToNext()) {
                String tableName = tables.getString(0);
                builder.append("📄 ").append(tableName).append("\n");

                Cursor columns = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
                while (columns.moveToNext()) {
                    builder.append("   • ")
                            .append(columns.getString(1))
                            .append(" (")
                            .append(columns.getString(2))
                            .append(")\n");
                }
                columns.close();
                builder.append("\n");
            }
            tables.close();

            final String structure = builder.length() == 0
                    ? "⚠ No tables found"
                    : builder.toString();

            runOnUiThread(() -> tvDbStructure.setText(structure));
        }).start();

        // TOGGLE DB STRUCTURE PANEL
        tvDbTitle.setOnClickListener(v -> {
            if (tvDbStructure.getVisibility() == View.GONE) {
                tvDbStructure.setVisibility(View.VISIBLE);
                tvDbTitle.setText("📦 Database Structure ▲");
            } else {
                tvDbStructure.setVisibility(View.GONE);
                tvDbTitle.setText("📦 Database Structure ▼");
            }
        });

        btnClear.setOnClickListener(v -> {
            etQuery.setText("");
            tableResult.removeAllViews();
        });

        btnRun.setOnClickListener(v -> runQuery());
    }

    private void runQuery() {
        String rawQuery = etQuery.getText().toString().trim();
        tableResult.removeAllViews();

        if (rawQuery.isEmpty()) {
            Toast.makeText(this, "Please enter a query", Toast.LENGTH_SHORT).show();
            return;
        }

        // REMOVE SQL COMMENTS (important!)
        rawQuery = rawQuery.replaceAll("(?m)^--.*$", "");  // remove lines starting with --
        rawQuery = rawQuery.replaceAll("(?m)^//.*$", "");  // remove lines starting with //
        rawQuery = rawQuery.trim();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] statements = rawQuery.split(";");

        for (String clean : statements) {
            clean = clean.trim();
            if (clean.isEmpty()) continue;

            try {
                if (clean.toLowerCase().startsWith("select")) {
                    processSelectQuery(db, clean);
                } else {
                    db.execSQL(clean);
                    showMessage("✅ Executed: " + clean);
                }
            } catch (Exception e) {
                showMessage("❌ " + e.getMessage());
            }
        }
    }

    private void processSelectQuery(SQLiteDatabase db, String query) {
        Cursor cursor = db.rawQuery(query, null);
        if (cursor == null) return;

        TableRow header = new TableRow(this);
        header.setBackgroundColor(Color.parseColor("#1565C0"));

        for (String col : cursor.getColumnNames()) {
            TextView tv = new TextView(this);
            tv.setText(col);
            tv.setTextColor(Color.WHITE);
            tv.setTypeface(null, Typeface.BOLD);
            tv.setPadding(20, 20, 20, 20);
            header.addView(tv);
        }
        tableResult.addView(header);

        while (cursor.moveToNext()) {
            TableRow row = new TableRow(this);
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                TextView tv = new TextView(this);
                tv.setText(cursor.getString(i));
                tv.setPadding(20, 20, 20, 20);
                row.addView(tv);
            }
            tableResult.addView(row);
        }
        cursor.close();
    }

    private void showMessage(String msg) {
        TableRow row = new TableRow(this);
        TextView tv = new TextView(this);
        tv.setText(msg);
        tv.setPadding(20, 20, 20, 20);
        row.addView(tv);
        tableResult.addView(row);
    }
}