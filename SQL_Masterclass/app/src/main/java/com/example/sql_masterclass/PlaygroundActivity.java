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

        dbHelper = new DatabaseHelper(this);

        // 1. Initialize Views
        etQuery = findViewById(R.id.etQuery);
        btnRun = findViewById(R.id.btnRun);
        btnClear = findViewById(R.id.btnClear);
        tableResult = findViewById(R.id.tableResult);
        tvDbStructure = findViewById(R.id.tvDbStructure);
        tvDbTitle = findViewById(R.id.tvDbTitle);

        loadDatabaseStructure();

        // Handle Pre-filled queries
        String preFilledQuery = getIntent().getStringExtra("PRE_FILLED_QUERY");
        if (preFilledQuery != null) {
            etQuery.setText(preFilledQuery);
        }

        // Toggle Database Structure
        tvDbTitle.setOnClickListener(v -> {
            if (tvDbStructure.getVisibility() == View.GONE) {
                tvDbStructure.setVisibility(View.VISIBLE);
                tvDbTitle.setText("📦 Database Structure ▲");
            } else {
                tvDbStructure.setVisibility(View.GONE);
                tvDbTitle.setText("📦 Database Structure ▼");
            }
        });

        // CLEAR BUTTON
        btnClear.setOnClickListener(v -> {
            etQuery.setText("");
            tableResult.removeAllViews();
        });

        // RUN BUTTON
        btnRun.setOnClickListener(v -> runQuery());
    }

    private void runQuery() {
        // 1. Get raw text
        String rawQuery = etQuery.getText().toString().trim();
        tableResult.removeAllViews(); // Clear previous results

        if (rawQuery.isEmpty()) {
            Toast.makeText(this, "Please enter a query", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. Split by semicolon
        String[] statements = rawQuery.split(";");

        // Use Writable Database to allow INSERT/UPDATE/DELETE
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 3. LOOP THROUGH ALL STATEMENTS (Forward loop)
        for (int i = 0; i < statements.length; i++) {

            String clean = statements[i].trim();

            // Clean comments
            while (clean.startsWith("--")) {
                int nextLine = clean.indexOf("\n");
                if (nextLine != -1) {
                    clean = clean.substring(nextLine + 1).trim();
                } else {
                    clean = "";
                }
            }

            // If query is empty after cleaning, skip it
            if (clean.isEmpty()) {
                continue;
            }

            // --- ADD A VISUAL DIVIDER BEFORE RESULT ---
            if (tableResult.getChildCount() > 0) {
                addDividerRow();
            }

            // --- EXECUTE THIS SPECIFIC QUERY ---
            try {
                if (clean.toLowerCase().startsWith("select")) {
                    processSelectQuery(db, clean);
                } else {
                    processActionQuery(db, clean);
                }
            } catch (Exception e) {
                showSimpleMessage("❌ Error in Query " + (i+1) + ": " + e.getMessage());
            }
        }
    }

    // --- HELPER METHOD TO PROCESS SELECT QUERIES ---
    private void processSelectQuery(SQLiteDatabase db, String query) {
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            // Header Row
            TableRow headerRow = new TableRow(this);
            headerRow.setBackgroundColor(Color.parseColor("#1565C0"));
            headerRow.setPadding(10, 10, 10, 10);

            String[] columnNames = cursor.getColumnNames();
            for (String colName : columnNames) {
                TextView tvHeader = new TextView(this);
                tvHeader.setText(colName);
                tvHeader.setTextColor(Color.WHITE);
                tvHeader.setTypeface(null, Typeface.BOLD);
                tvHeader.setPadding(20, 20, 20, 20);
                headerRow.addView(tvHeader);

                View vLine = new View(this);
                vLine.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
                vLine.setBackgroundColor(Color.WHITE);
                headerRow.addView(vLine);
            }
            tableResult.addView(headerRow);

            // Data Rows
            while (cursor.moveToNext()) {
                TableRow dataRow = new TableRow(this);
                dataRow.setPadding(10, 10, 10, 10);

                if (cursor.getPosition() % 2 == 0) {
                    dataRow.setBackgroundColor(Color.parseColor("#F5F9FF"));
                } else {
                    dataRow.setBackgroundColor(Color.WHITE);
                }

                for (int col = 0; col < cursor.getColumnCount(); col++) {
                    TextView tvData = new TextView(this);
                    String value = cursor.getString(col);
                    if (value == null) value = "NULL";

                    tvData.setText(value);
                    tvData.setTextColor(Color.parseColor("#37474F"));
                    tvData.setPadding(20, 20, 20, 20);
                    dataRow.addView(tvData);

                    View vLine = new View(this);
                    vLine.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
                    vLine.setBackgroundColor(Color.parseColor("#E0E0E0"));
                    dataRow.addView(vLine);
                }
                tableResult.addView(dataRow);

                // Horizontal Line
                View hLine = new View(this);
                hLine.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 1));
                hLine.setBackgroundColor(Color.parseColor("#E0E0E0"));
                tableResult.addView(hLine);
            }
            cursor.close();

            if (tableResult.getChildCount() == 0 || (tableResult.getChildCount() == 1 && tableResult.getChildAt(0) instanceof TableRow)) {
                // Logic ensures we displayed something, if not, showing 0 rows might be handled by cursor check
            }
        }
    }

    // --- HELPER METHOD TO PROCESS ACTION QUERIES ---
    private void processActionQuery(SQLiteDatabase db, String query) {
        db.execSQL(query);
        loadDatabaseStructure(); // Refresh structure panel
        showSimpleMessage("✅ Executed: " + query);
    }

    private void addDividerRow() {
        TableRow row = new TableRow(this);
        TextView tv = new TextView(this);
        tv.setText("------------------------------------------------");
        tv.setTextColor(Color.LTGRAY);
        tv.setPadding(10, 20, 10, 20);
        row.addView(tv);
        tableResult.addView(row);
    }

    private void showSimpleMessage(String message) {
        TableRow row = new TableRow(this);
        TextView tv = new TextView(this);
        tv.setText(message);
        tv.setPadding(30, 30, 30, 30);
        tv.setTextSize(16);

        if (message.startsWith("❌")) tv.setTextColor(Color.RED);
        else if (message.startsWith("✅")) tv.setTextColor(Color.parseColor("#04AA6D"));
        else tv.setTextColor(Color.GRAY);

        row.addView(tv);
        tableResult.addView(row);
    }

    private void loadDatabaseStructure() {
        try {
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

            tvDbStructure.setText(builder.toString());

        } catch (Exception e) {
            tvDbStructure.setText("Unable to load database structure");
        }
    }
}