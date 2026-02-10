package com.project_vn.sql_masterclass;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class LearnActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LearnAdapter adapter;
    List<TopicItem> topicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        recyclerView = findViewById(R.id.rvTopics);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        topicList = new ArrayList<>();

        // ==========================================
        // SECTION 1: CORE CONCEPTS
        // ==========================================
        topicList.add(new TopicItem("1. Core Concepts"));

        topicList.add(new TopicItem("SELECT Statement",
                "The SELECT statement is the most basic and frequently used command in SQL. It is used to retrieve data from a database. The data returned is stored in a result table, called the result-set.\n\n" +
                        "Key Concepts:\n" +
                        "• Specific Columns: You can list the exact column names you want (e.g., Name, City) to make your query faster.\n" +
                        "• Wildcard (*): Using the asterisk symbol (*) selects EVERY column in the table. This is useful for browsing data but can be slower on large tables.\n" +
                        "• Case Sensitivity: SQL keywords (like SELECT, FROM) are not case sensitive, but it is a professional standard to write them in UPPERCASE.",
                "SELECT CustomerName, City FROM Customers;\n\n-- Or select everything:\nSELECT * FROM Customers;"));

        topicList.add(new TopicItem("SELECT DISTINCT",
                "Inside a table, a column often contains many duplicate values. For example, a 'Country' column might list 'India' 50 times.\n\n" +
                        "The SELECT DISTINCT statement is used to return only distinct (different) values. It removes duplicates from the result set, showing you a unique list.",
                "-- This shows every country (including duplicates)\nSELECT Country FROM Customers;\n\n-- This shows each country only once\nSELECT DISTINCT Country FROM Customers;"));

        topicList.add(new TopicItem("WHERE Clause",
                "The WHERE clause is used to filter records. It extracts only those records that fulfill a specified condition.\n\n" +
                        "Syntax Rules:\n" +
                        "• Text values must be enclosed in single quotes (e.g., 'India').\n" +
                        "• Numeric values should NOT be in quotes (e.g., 500).\n\n" +
                        "Common Operators:\n" +
                        "• =  (Equal)\n" +
                        "• >  (Greater than)\n" +
                        "• <  (Less than)\n" +
                        "• <> (Not equal)",
                "SELECT * FROM Customers \nWHERE Country = 'India';\n\nSELECT * FROM Products \nWHERE Price > 20;"));

        topicList.add(new TopicItem("ORDER BY",
                "The ORDER BY keyword is used to sort the result-set.\n\n" +
                        "• ASC (Ascending): This is the default. Numbers go 1-10, Text goes A-Z.\n" +
                        "• DESC (Descending): Numbers go 10-1, Text goes Z-A.\n\n" +
                        "You can also sort by multiple columns (e.g., sort by Country first, then by City).",
                "-- Sort by Price (High to Low)\nSELECT * FROM Products \nORDER BY Price DESC;\n\n-- Sort alphabetically\nSELECT * FROM Customers \nORDER BY Country;"));

        // ==========================================
        // SECTION 2: ADVANCED FILTERS
        // ==========================================
        topicList.add(new TopicItem("2. Advanced Filtering"));

        topicList.add(new TopicItem("AND, OR, NOT",
                "The WHERE clause can be very powerful when combined with logical operators:\n\n" +
                        "1. AND: displays a record if ALL the conditions separated by AND are TRUE.\n" +
                        "2. OR: displays a record if ANY of the conditions separated by OR is TRUE.\n" +
                        "3. NOT: displays a record if the condition(s) is NOT TRUE.\n\n" +
                        "Parentheses () are important when combining these to ensure the correct logic order.",
                "SELECT * FROM Customers \nWHERE Country='India' \nAND (City='Mumbai' OR City='Delhi');"));

        topicList.add(new TopicItem("LIKE (Wildcards)",
                "The LIKE operator is used in a WHERE clause to search for a specified pattern in a column, rather than an exact match.\n\n" +
                        "Wildcard Characters:\n" +
                        "• The percent sign (%) represents zero, one, or multiple characters.\n" +
                        "• The underscore sign (_) represents a single character.\n\n" +
                        "Examples:\n" +
                        "• 'S%' -> Starts with 'S'\n" +
                        "• '%a' -> Ends with 'a'\n" +
                        "• '%sh%' -> Contains 'sh' anywhere",
                "-- Find customers whose name starts with 'S'\nSELECT * FROM Customers \nWHERE CustomerName LIKE 'S%';"));

        topicList.add(new TopicItem("IN Operator",
                "The IN operator allows you to specify multiple values in a WHERE clause. It is essentially a shorthand for multiple 'OR' conditions.\n\n" +
                        "Instead of writing:\n" +
                        "WHERE Country='India' OR Country='UK' OR Country='Singapore'...\n\n" +
                        "You can simply write:\n" +
                        "WHERE Country IN ('India', 'UK', 'Singapore').",
                "SELECT * FROM Customers \nWHERE Country IN ('India', 'UK', 'Singapore');"));

        // ==========================================
        // SECTION 3: MODIFYING DATA
        // ==========================================
        topicList.add(new TopicItem("3. Modifying Data"));

        topicList.add(new TopicItem("INSERT INTO",
                "The INSERT INTO statement is used to add new rows of data to a table.\n\n" +
                        "There are two ways to write this:\n" +
                        "1. Specify both the column names and the values to be inserted (Recommended).\n" +
                        "2. If you are adding values for all columns of the table, you do not need to specify the column names in the SQL query.",
                "INSERT INTO Customers (CustomerName, City, Country) \nVALUES ('Reddy Solutions', 'Hyderabad', 'India');"));

        topicList.add(new TopicItem("UPDATE",
                "The UPDATE statement is used to modify the existing records in a table.\n\n" +
                        "⚠️ CRITICAL WARNING:\n" +
                        "Notice the WHERE clause in the example code. The WHERE clause specifies which record(s) that should be updated. If you omit the WHERE clause, ALL records in the table will be updated!",
                "UPDATE Customers \nSET ContactName='Amit Sharma', City='Pune' \nWHERE CustomerID=1;"));

        topicList.add(new TopicItem("DELETE",
                "The DELETE statement is used to delete existing records in a table.\n\n" +
                        "⚠️ CRITICAL WARNING:\n" +
                        "Be very careful when deleting records! If you omit the WHERE clause, ALL records in the table will be deleted permanently!",
                "DELETE FROM Customers \nWHERE CustomerName='Sharma Enterprises';"));

        // ==========================================
        // SECTION 4: AGGREGATES
        // ==========================================
        topicList.add(new TopicItem("4. Aggregates"));

        topicList.add(new TopicItem("MIN() and MAX()",
                "MIN() returns the smallest value of the selected column.\n" +
                        "MAX() returns the largest value of the selected column.\n\n" +
                        "These are useful for finding price ranges, oldest/newest dates, or highest scores.",
                "SELECT MIN(Price) FROM Products;\n\nSELECT MAX(Price) FROM Products;"));

        topicList.add(new TopicItem("COUNT, AVG, SUM",
                "Aggregate functions perform a calculation on a set of values and return a single value.\n\n" +
                        "• COUNT(): Returns the number of rows that match a specified criterion.\n" +
                        "• AVG(): Returns the average value of a numeric column.\n" +
                        "• SUM(): Returns the total sum of a numeric column.",
                "-- Count total products\nSELECT COUNT(ProductID) FROM Products;\n\n-- Calculate average price\nSELECT AVG(Price) FROM Products;"));

        topicList.add(new TopicItem("GROUP BY",
                "The GROUP BY statement groups rows that have the same values into summary rows, like 'find the number of customers in each country'.\n\n" +
                        "The GROUP BY statement is often used with aggregate functions (COUNT, MAX, MIN, SUM, AVG) to group the result-set by one or more columns.",
                "SELECT COUNT(CustomerID), Country \nFROM Customers \nGROUP BY Country;"));

        // ==========================================
        // SECTION 5: JOINING TABLES
        // ==========================================
        topicList.add(new TopicItem("5. Joining Tables"));

        topicList.add(new TopicItem("INNER JOIN",
                "A JOIN clause is used to combine rows from two or more tables, based on a related column between them.\n\n" +
                        "The INNER JOIN keyword selects records that have matching values in BOTH tables. If there are records in the 'Orders' table that do not have matches in 'Customers', these orders will NOT be shown.",
                "SELECT Orders.OrderID, Customers.CustomerName \nFROM Orders \nINNER JOIN Customers \nON Orders.CustomerID = Customers.CustomerID;"));

        topicList.add(new TopicItem("LEFT JOIN",
                "The LEFT JOIN keyword returns all records from the left table (table1), and the matched records from the right table (table2).\n\n" +
                        "The result is NULL from the right side, if there is no match. This is useful if you want to list ALL customers, even those who haven't placed an order yet.",
                "SELECT Customers.CustomerName, Orders.OrderID \nFROM Customers \nLEFT JOIN Orders \nON Customers.CustomerID = Orders.CustomerID;"));

        // ==========================================
        // SECTION 6: DATABASE STRUCTURE
        // ==========================================
        topicList.add(new TopicItem("6. Database Structure"));

        topicList.add(new TopicItem("CREATE TABLE",
                "The CREATE TABLE statement is used to create a new table in a database.\n\n" +
                        "Syntax parameters:\n" +
                        "• The column parameters specify the names of the columns of the table.\n" +
                        "• The datatype parameter specifies the type of data the column can hold (e.g. varchar, integer, date, etc.).",
                "CREATE TABLE Persons (\n    PersonID int,\n    LastName varchar(255),\n    FirstName varchar(255),\n    Address varchar(255)\n);"));

        topicList.add(new TopicItem("DROP TABLE",
                "The DROP TABLE statement is used to drop (delete) an existing table in a database.\n\n" +
                        "⚠️ DANGER:\n" +
                        "Be careful before running this. Dropping a table will result in the loss of the complete table structure AND all information stored in it.",
                "DROP TABLE Shippers;"));

        adapter = new LearnAdapter(this, topicList);
        recyclerView.setAdapter(adapter);
    }
}