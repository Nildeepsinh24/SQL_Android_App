package com.example.sql_masterclass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ShopDB.db";
    private static final int DATABASE_VERSION = 6; // ↑ bumped version

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // ================= CUSTOMERS =================
        db.execSQL(
                "CREATE TABLE Customers (" +
                        "CustomerID INTEGER PRIMARY KEY, " +
                        "CustomerName TEXT, " +
                        "ContactName TEXT, " +
                        "City TEXT, " +
                        "Country TEXT)"
        );

        db.execSQL("INSERT INTO Customers VALUES (1, 'Alfreds Futterkiste', 'Maria Anders', 'Berlin', 'Germany')");
        db.execSQL("INSERT INTO Customers VALUES (2, 'Ana Trujillo Emparedados', 'Ana Trujillo', 'Mexico City', 'Mexico')");
        db.execSQL("INSERT INTO Customers VALUES (3, 'Antonio Moreno Taquería', 'Antonio Moreno', 'Mexico City', 'Mexico')");
        db.execSQL("INSERT INTO Customers VALUES (4, 'Around the Horn', 'Thomas Hardy', 'London', 'UK')");
        db.execSQL("INSERT INTO Customers VALUES (5, 'Berglunds snabbköp', 'Christina Berglund', 'Luleå', 'Sweden')");

        // ================= ORDERS =================
        db.execSQL(
                "CREATE TABLE Orders (" +
                        "OrderID INTEGER PRIMARY KEY, " +
                        "CustomerID INTEGER, " +
                        "Amount INTEGER)"
        );

        db.execSQL("INSERT INTO Orders VALUES (101, 1, 100)");
        db.execSQL("INSERT INTO Orders VALUES (102, 2, 200)");
        db.execSQL("INSERT INTO Orders VALUES (103, 1, 150)");

        // ================= PRODUCTS =================
        db.execSQL(
                "CREATE TABLE Products (" +
                        "ProductID INTEGER PRIMARY KEY, " +
                        "ProductName TEXT, " +
                        "Price REAL)"
        );

        db.execSQL("INSERT INTO Products VALUES (1, 'Bread', 2.50)");
        db.execSQL("INSERT INTO Products VALUES (2, 'Milk', 1.10)");
        db.execSQL("INSERT INTO Products VALUES (3, 'Cheese', 4.20)");

        // ================= SALES =================
        db.execSQL(
                "CREATE TABLE Sales (" +
                        "SaleID INTEGER PRIMARY KEY, " +
                        "Country TEXT, " +
                        "Amount INTEGER)"
        );

        db.execSQL("INSERT INTO Sales VALUES (1, 'USA', 100)");
        db.execSQL("INSERT INTO Sales VALUES (2, 'USA', 200)");
        db.execSQL("INSERT INTO Sales VALUES (3, 'UK', 300)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Customers");
        db.execSQL("DROP TABLE IF EXISTS Orders");
        db.execSQL("DROP TABLE IF EXISTS Products");
        db.execSQL("DROP TABLE IF EXISTS Sales");
        onCreate(db);
    }
}
