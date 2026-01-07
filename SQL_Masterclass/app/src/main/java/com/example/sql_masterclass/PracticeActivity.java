package com.example.sql_masterclass;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;
import java.util.List;

public class PracticeActivity extends AppCompatActivity {

    static class Question {
        String type; // "MCQ" or "CODE"
        String text;
        String answer; // This is the DIRECT ANSWER
        String[] options; // Only for MCQ

        public Question(String text, String answer) {
            this.type = "CODE";
            this.text = text;
            this.answer = answer;
        }

        public Question(String text, String answer, String[] options) {
            this.type = "MCQ";
            this.text = text;
            this.answer = answer;
            this.options = options;
        }
    }

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    TextView tvTopicTitle, tvQuestion, tvResult, tvProgress, tvHintText;
    EditText etQuery;
    Button btnRun;
    LinearLayout layoutCoding;
    RadioGroup radioGroup;
    RadioButton rb1, rb2, rb3, rb4;
    ProgressBar progressBar;
    CardView cvHint;

    List<Question> questionList = new ArrayList<>();
    int currentQuestionIndex = 0;
    String currentTopic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        currentTopic = getIntent().getStringExtra("TOPIC_NAME");
        if (currentTopic == null) currentTopic = "SELECT Statement";

        tvTopicTitle = findViewById(R.id.tvLevel);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvResult = findViewById(R.id.tvResult);
        etQuery = findViewById(R.id.etQuery);
        btnRun = findViewById(R.id.btnRun);
        layoutCoding = findViewById(R.id.layoutCoding);
        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rbOption1);
        rb2 = findViewById(R.id.rbOption2);
        rb3 = findViewById(R.id.rbOption3);
        rb4 = findViewById(R.id.rbOption4);
        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tvProgress);
        cvHint = findViewById(R.id.cvHint);
        tvHintText = findViewById(R.id.tvHintText);

        tvTopicTitle.setText("Topic: " + currentTopic);

        dbHelper = new DatabaseHelper(this);
        try {
            db = dbHelper.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadQuestions(currentTopic);
        showQuestion();
    }

    private void loadQuestions(String topic) {
        questionList.clear();

        // ================= 1. SELECT & BASICS =================
        if (topic.contains("SELECT") && !topic.contains("DISTINCT")) {
            questionList.add(new Question("What does SQL stand for?", "Structured Query Language", new String[]{"Structured Query Language", "Strong Question Language", "Structured Question List", "Simple Query Logic"}));
            questionList.add(new Question("Which SQL statement extracts data?", "SELECT", new String[]{"GET", "OPEN", "EXTRACT", "SELECT"}));
            questionList.add(new Question("Select all columns from 'Customers'.", "SELECT * FROM Customers;"));
            questionList.add(new Question("Select only the 'City' column from 'Customers'.", "SELECT City FROM Customers;"));
            questionList.add(new Question("True or False: SQL keywords are case-sensitive.", "False", new String[]{"True", "False", "Depends on OS", "Only in Linux"}));
            questionList.add(new Question("Select 'CustomerName' and 'City' from 'Customers'.", "SELECT CustomerName, City FROM Customers;"));
        }

        // ================= 2. SELECT DISTINCT =================
        else if (topic.contains("DISTINCT")) {
            questionList.add(new Question("What does DISTINCT do?", "Removes duplicates", new String[]{"Sorts data", "Removes duplicates", "Deletes rows", "Counts rows"}));
            questionList.add(new Question("Select distinct Countries from Customers.", "SELECT DISTINCT Country FROM Customers;"));
            questionList.add(new Question("Can DISTINCT work on multiple columns?", "Yes", new String[]{"Yes", "No", "MySQL only", "Never"}));
            questionList.add(new Question("Select distinct Cities from Customers.", "SELECT DISTINCT City FROM Customers;"));
            questionList.add(new Question("Does DISTINCT change the actual table data?", "No", new String[]{"Yes", "No", "Sometimes", "Only on Tuesday"}));
            questionList.add(new Question("Select distinct CustomerNames.", "SELECT DISTINCT CustomerName FROM Customers;"));
        }

        // ================= 3. WHERE CLAUSE =================
        else if (topic.contains("WHERE") && !topic.contains("LIKE") && !topic.contains("IN")) {
            questionList.add(new Question("Which clause filters records?", "WHERE", new String[]{"FILTER", "WHERE", "SEARCH", "WHEN"}));
            questionList.add(new Question("Select customers from 'Mexico'.", "SELECT * FROM Customers WHERE Country = 'Mexico';"));
            questionList.add(new Question("Which operator means 'Not Equal'?", "<>", new String[]{"<>", "><", "==", "!"}));
            questionList.add(new Question("Select products with Price > 20.", "SELECT * FROM Products WHERE Price > 20;"));
            questionList.add(new Question("SQL text strings use which quotes?", "Single Quotes", new String[]{"Double Quotes", "Single Quotes", "Brackets", "No Quotes"}));
            questionList.add(new Question("Find customer with CustomerID 1.", "SELECT * FROM Customers WHERE CustomerID = 1;"));
        }

        // ================= 4. ORDER BY =================
        else if (topic.contains("ORDER BY")) {
            questionList.add(new Question("Which keyword sorts results?", "ORDER BY", new String[]{"SORT BY", "ORDER BY", "ORGANIZE", "ALIGN"}));
            questionList.add(new Question("Sort Customers by Country.", "SELECT * FROM Customers ORDER BY Country;"));
            questionList.add(new Question("Default sort order is?", "Ascending", new String[]{"Ascending", "Descending", "Random", "None"}));
            questionList.add(new Question("Sort Products by Price High to Low.", "SELECT * FROM Products ORDER BY Price DESC;"));
            questionList.add(new Question("Keyword for Descending order?", "DESC", new String[]{"DESC", "DSC", "DOWN", "MINUS"}));
            questionList.add(new Question("Sort Customers by City (A-Z).", "SELECT * FROM Customers ORDER BY City;"));
        }

        // ================= 5. AND, OR, NOT =================
        else if (topic.contains("AND") || topic.contains("OR")) {
            questionList.add(new Question("Operator for ALL conditions true?", "AND", new String[]{"AND", "OR", "NOT", "ALL"}));
            questionList.add(new Question("Select Customers in 'Germany' AND 'Berlin'.", "SELECT * FROM Customers WHERE Country='Germany' AND City='Berlin';"));
            questionList.add(new Question("Operator for ANY condition true?", "OR", new String[]{"AND", "OR", "NOT", "EITHER"}));
            questionList.add(new Question("Select Customers in 'Germany' OR 'France'.", "SELECT * FROM Customers WHERE Country='Germany' OR Country='France';"));
            questionList.add(new Question("Operator to invert a condition?", "NOT", new String[]{"INVERT", "NOT", "FLIP", "OPPOSITE"}));
            questionList.add(new Question("Select Customers NOT from 'Germany'.", "SELECT * FROM Customers WHERE NOT Country='Germany';"));
        }

        // ================= 6. LIKE (WILDCARDS) =================
        else if (topic.contains("LIKE")) {
            questionList.add(new Question("Which operator searches for a pattern?", "LIKE", new String[]{"MATCH", "LIKE", "SAME", "LOOK"}));
            questionList.add(new Question("Select customers starting with 'a'.", "SELECT * FROM Customers WHERE CustomerName LIKE 'a%';"));
            questionList.add(new Question("What does '%' represent?", "Multiple chars", new String[]{"One char", "Multiple chars", "Numbers", "Spaces"}));
            questionList.add(new Question("Select customers ending with 'a'.", "SELECT * FROM Customers WHERE CustomerName LIKE '%a';"));
            questionList.add(new Question("What does '_' represent?", "Single char", new String[]{"Single char", "Multiple chars", "Digit", "None"}));
            questionList.add(new Question("Select customers containing 'or'.", "SELECT * FROM Customers WHERE CustomerName LIKE '%or%';"));
        }

        // ================= 7. IN OPERATOR =================
        else if (topic.contains("IN")) {
            questionList.add(new Question("IN is shorthand for multiple?", "OR", new String[]{"AND", "OR", "NOT", "IF"}));
            questionList.add(new Question("Select customers in Germany or UK.", "SELECT * FROM Customers WHERE Country IN ('Germany', 'UK');"));
            questionList.add(new Question("Can IN use numbers?", "Yes", new String[]{"Yes", "No", "Only Integers", "Never"}));
            questionList.add(new Question("Select products with Price 10 or 20.", "SELECT * FROM Products WHERE Price IN (10, 20);"));
            questionList.add(new Question("Syntax: WHERE Col IN ...", "(...)", new String[]{"[...]", "{...}", "(...)", "<...>"}));
            questionList.add(new Question("Select customers in 'Mexico' or 'Sweden'.", "SELECT * FROM Customers WHERE Country IN ('Mexico', 'Sweden');"));
        }

        // ================= 8. INSERT =================
        else if (topic.contains("INSERT")) {
            questionList.add(new Question("Statement to add data?", "INSERT INTO", new String[]{"ADD NEW", "INSERT INTO", "ADD RECORD", "UPDATE"}));
            questionList.add(new Question("Insert Shipper 'Speedy'.", "INSERT INTO Shippers (ShipperName) VALUES ('Speedy');"));
            questionList.add(new Question("Keyword before values?", "VALUES", new String[]{"DATA", "SET", "VALUES", "INPUT"}));
            questionList.add(new Question("Can you insert partial columns?", "Yes", new String[]{"Yes", "No", "MySQL only", "Oracle only"}));
            questionList.add(new Question("Syntax: INSERT ___ table...", "INTO", new String[]{"INTO", "TO", "IN", "VALUES"}));
            questionList.add(new Question("Insert Category 'Tech', Desc 'PCs'.", "INSERT INTO Categories (CategoryName, Description) VALUES ('Tech', 'PCs');"));
        }

        // ================= 9. UPDATE =================
        else if (topic.contains("UPDATE")) {
            questionList.add(new Question("Statement to modify data?", "UPDATE", new String[]{"SAVE", "MODIFY", "UPDATE", "CHANGE"}));
            questionList.add(new Question("Set City 'Oslo' for ID 1.", "UPDATE Customers SET City='Oslo' WHERE CustomerID=1;"));
            questionList.add(new Question("Omitted WHERE causes?", "Update all", new String[]{"Nothing", "Update all", "Error", "First row only"}));
            questionList.add(new Question("Keyword for new value?", "SET", new String[]{"TO", "SET", "EQUALS", "VALUE"}));
            questionList.add(new Question("Set Contact 'Joe' for ID 2.", "UPDATE Customers SET ContactName='Joe' WHERE CustomerID=2;"));
            questionList.add(new Question("Can you update multiple cols?", "Yes", new String[]{"Yes", "No", "Pro version", "Never"}));
        }

        // ================= 10. DELETE =================
        else if (topic.contains("DELETE")) {
            questionList.add(new Question("Statement to remove rows?", "DELETE", new String[]{"REMOVE", "DELETE", "ERASE", "DROP"}));
            questionList.add(new Question("Delete customer ID 1.", "DELETE FROM Customers WHERE CustomerID=1;"));
            questionList.add(new Question("Omitted WHERE causes?", "Delete all", new String[]{"Delete one", "Drop table", "Delete all", "Nothing"}));
            questionList.add(new Question("Keyword after DELETE?", "FROM", new String[]{"TABLE", "FROM", "*", "SET"}));
            questionList.add(new Question("Delete products with Price 0.", "DELETE FROM Products WHERE Price=0;"));
            questionList.add(new Question("Does DELETE drop table?", "No", new String[]{"Yes", "No", "Sometimes", "Depends"}));
        }

        // ================= 11. DATABASE STRUCTURE =================
        else if (topic.contains("TABLE") || topic.contains("CREATE") || topic.contains("DROP")) {
            questionList.add(new Question("Command to make a table?", "CREATE TABLE", new String[]{"MAKE TABLE", "NEW TABLE", "CREATE TABLE", "ADD TABLE"}));
            questionList.add(new Question("Command to delete a table?", "DROP TABLE", new String[]{"DELETE TABLE", "REMOVE TABLE", "DROP TABLE", "STOP TABLE"}));
            questionList.add(new Question("Does DROP TABLE save data?", "No", new String[]{"Yes", "No", "Archive", "Backup"}));
            questionList.add(new Question("Create table 'Test' with ID int.", "CREATE TABLE Test (ID int);"));
            questionList.add(new Question("Drop table 'Shippers'.", "DROP TABLE Shippers;"));
            questionList.add(new Question("DataType for text?", "VARCHAR/TEXT", new String[]{"NUMBER", "INT", "VARCHAR/TEXT", "BOOL"}));
        }

        // ================= 12. AGGREGATES & JOINS =================
        else {
            questionList.add(new Question("Function to count rows?", "COUNT()", new String[]{"SUM()", "TOTAL()", "COUNT()", "NUM()"}));
            questionList.add(new Question("Count all Products.", "SELECT COUNT(ProductID) FROM Products;"));
            questionList.add(new Question("Function for average?", "AVG()", new String[]{"AVERAGE()", "AVG()", "MEAN()", "MEDIAN()"}));
            questionList.add(new Question("Max Price of Products?", "SELECT MAX(Price) FROM Products;"));
            questionList.add(new Question("JOIN for matching rows?", "INNER JOIN", new String[]{"INNER JOIN", "LEFT JOIN", "RIGHT JOIN", "OUTER JOIN"}));
            questionList.add(new Question("Join Orders and Customers.", "SELECT * FROM Orders INNER JOIN Customers ON Orders.CustomerID = Customers.CustomerID;"));
        }
    }

    private void showQuestion() {
        int totalQuestions = questionList.size();

        cvHint.setVisibility(View.GONE);
        tvResult.setText("");
        btnRun.setVisibility(View.VISIBLE);

        progressBar.setMax(totalQuestions);
        progressBar.setProgress(currentQuestionIndex);
        tvProgress.setText("Question " + (currentQuestionIndex + 1) + " of " + totalQuestions);

        if (currentQuestionIndex >= totalQuestions) {
            handleCompletion(totalQuestions);
            return;
        }

        btnRun.setText("SUBMIT ANSWER");
        btnRun.setOnClickListener(v -> checkAnswer());

        Question q = questionList.get(currentQuestionIndex);
        tvQuestion.setText("Q" + (currentQuestionIndex + 1) + ": " + q.text);

        if (q.type.equals("MCQ")) {
            layoutCoding.setVisibility(View.GONE);
            radioGroup.setVisibility(View.VISIBLE);
            radioGroup.clearCheck();
            if (q.options.length >= 4) {
                rb1.setText(q.options[0]);
                rb2.setText(q.options[1]);
                rb3.setText(q.options[2]);
                rb4.setText(q.options[3]);
            }
        } else {
            layoutCoding.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.GONE);
            etQuery.setText(getStarterCode(currentTopic));
        }
    }

    private void checkAnswer() {
        Question q = questionList.get(currentQuestionIndex);
        boolean isCorrect = false;

        if (q.type.equals("MCQ")) {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton selectedRb = findViewById(selectedId);
            isCorrect = selectedRb.getText().toString().equals(q.answer);
        } else {
            String userQuery = etQuery.getText().toString().trim();
            if (userQuery.isEmpty()) {
                Toast.makeText(this, "Please write a query", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                if (q.text.toLowerCase().contains("select")) {
                    Cursor c = db.rawQuery(userQuery, null);
                    if (c != null) {
                        c.close();
                        isCorrect = true;
                    }
                } else {
                    db.execSQL(userQuery);
                    isCorrect = true;
                }
            } catch (Exception e) {
                isCorrect = false;
            }
        }

        if (isCorrect) {
            showSuccess();
        } else {
            // DIRECT ANSWER REVEALED
            showError(q.answer);
        }
    }

    private void handleCompletion(int total) {
        tvQuestion.setText("🎉 Topic Completed!");
        progressBar.setProgress(total);
        tvProgress.setText("Completed!");

        SharedPreferences prefs = getSharedPreferences("SQL_PROGRESS", MODE_PRIVATE);
        prefs.edit().putInt(currentTopic, 100).apply();

        layoutCoding.setVisibility(View.GONE);
        radioGroup.setVisibility(View.GONE);
        btnRun.setVisibility(View.GONE);
        cvHint.setVisibility(View.GONE);

        tvResult.setText("Great job! You finished " + currentTopic + ".");
        tvResult.setTextColor(Color.parseColor("#04AA6D"));

        new Handler().postDelayed(this::finish, 2000);
    }

    private String getStarterCode(String topic) {
        if (topic.contains("INSERT")) return "INSERT INTO ";
        if (topic.contains("UPDATE")) return "UPDATE ";
        if (topic.contains("DELETE")) return "DELETE FROM ";
        if (topic.contains("CREATE")) return "CREATE TABLE ";
        if (topic.contains("DROP")) return "DROP TABLE ";
        return "SELECT ";
    }

    private void showSuccess() {
        cvHint.setVisibility(View.GONE);
        tvResult.setTextColor(Color.parseColor("#04AA6D"));
        tvResult.setText("✅ Correct!");
        new Handler().postDelayed(() -> {
            currentQuestionIndex++;
            showQuestion();
        }, 1500);
    }

    // CHANGED: Displays Direct Answer
    private void showError(String correctAnswer) {
        tvResult.setTextColor(Color.RED);
        tvResult.setText("❌ Wrong Answer.");

        // Show the Correct Answer immediately
        tvHintText.setText("✅ Correct Answer:\n" + correctAnswer);
        cvHint.setVisibility(View.VISIBLE);
    }
}