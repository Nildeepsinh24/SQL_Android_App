package com.project_vn.sql_masterclass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ShopDB.db";

    // IMPORTANT: must be HIGHER than any previous version (you had 7)
    private static final int DATABASE_VERSION = 8;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createAllTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        createAllTables(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // prevent crash if downgrade ever happens
        createAllTables(db);
    }

    private void createAllTables(SQLiteDatabase db) {

        // CUSTOMERS
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Customers (" +
                        "CustomerID INTEGER PRIMARY KEY, " +
                        "CustomerName TEXT, " +
                        "ContactName TEXT, " +
                        "City TEXT, " +
                        "Country TEXT)"
        );

        db.execSQL("INSERT OR IGNORE INTO Customers VALUES (1,'Alfreds Futterkiste','Maria Anders','Berlin','Germany')");
        db.execSQL("INSERT OR IGNORE INTO Customers VALUES (2,'Ana Trujillo','Ana Trujillo','Mexico City','Mexico')");
        db.execSQL("INSERT OR IGNORE INTO Customers VALUES (3,'Antonio Moreno','Antonio Moreno','Mexico City','Mexico')");
        db.execSQL("INSERT OR IGNORE INTO Customers VALUES (4,'Around the Horn','Thomas Hardy','London','UK')");
        db.execSQL("INSERT OR IGNORE INTO Customers VALUES (5,'Berglunds snabbköp','Christina Berglund','Luleå','Sweden')");

        // ORDERS
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Orders (" +
                        "OrderID INTEGER PRIMARY KEY, " +
                        "CustomerID INTEGER, " +
                        "Amount INTEGER)"
        );

        db.execSQL("INSERT OR IGNORE INTO Orders VALUES (101,1,100)");
        db.execSQL("INSERT OR IGNORE INTO Orders VALUES (102,2,200)");
        db.execSQL("INSERT OR IGNORE INTO Orders VALUES (103,1,150)");

        // PRODUCTS
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Products (" +
                        "ProductID INTEGER PRIMARY KEY, " +
                        "ProductName TEXT, " +
                        "Price REAL)"
        );

        db.execSQL("INSERT OR IGNORE INTO Products VALUES (1,'Bread',2.5)");
        db.execSQL("INSERT OR IGNORE INTO Products VALUES (2,'Milk',1.1)");
        db.execSQL("INSERT OR IGNORE INTO Products VALUES (3,'Cheese',4.2)");

        // SALES
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Sales (" +
                        "SaleID INTEGER PRIMARY KEY, " +
                        "Country TEXT, " +
                        "Amount INTEGER)"
        );

        db.execSQL("INSERT OR IGNORE INTO Sales VALUES (1,'USA',100)");
        db.execSQL("INSERT OR IGNORE INTO Sales VALUES (2,'USA',200)");
        db.execSQL("INSERT OR IGNORE INTO Sales VALUES (3,'UK',300)");
    }
}
