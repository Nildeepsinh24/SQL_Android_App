package com.project_vn.sql_masterclass;

public class LearnContent {

    public static class Page {
        String title;
        String intro;
        String exampleTable;
        String code;
        String description;

        public Page(String title, String intro, String exampleTable, String code, String description) {
            this.title = title;
            this.intro = intro;
            this.exampleTable = exampleTable;
            this.code = code;
            this.description = description;
        }
    }

    public static Page getPage(String topic) {

        switch (topic) {

            // --- BASICS ---
            case "SELECT":
                return new Page(
                        "SQL SELECT Statement",
                        "The SELECT statement is used to select data from a database.",
                        "Customers Table:\nID | Name           | City\n1  | Alfreds        | Berlin\n2  | Ana Trujillo   | Mexico",
                        "SELECT Name, City FROM Customers;",
                        "The data returned is stored in a result table, called the result-set.\n\n• SELECT * selects ALL columns.\n• SELECT Column1, Column2 selects specific columns."
                );

            case "SELECT DISTINCT":
                return new Page(
                        "SQL SELECT DISTINCT",
                        "The SELECT DISTINCT statement is used to return only distinct (different) values.",
                        "Customers Table:\nID | Country\n1  | Mexico\n2  | Mexico\n3  | UK",
                        "SELECT DISTINCT Country FROM Customers;",
                        "Inside a table, a column often contains many duplicate values; and sometimes you only want to list the different (distinct) values."
                );

            case "WHERE":
                return new Page(
                        "SQL WHERE Clause",
                        "The WHERE clause is used to filter records.",
                        "Products Table:\nID | Product | Price\n1  | Apple   | 10\n2  | Banana  | 20",
                        "SELECT * FROM Products WHERE Price > 15;",
                        "The WHERE clause is not only used in SELECT statements, but also in UPDATE, DELETE, etc.!\n\nOperators:\n• =  Equal\n• >  Greater than\n• <  Less than\n• >= Greater or equal"
                );

            case "ORDER BY":
                return new Page(
                        "SQL ORDER BY",
                        "The ORDER BY keyword is used to sort the result-set in ascending or descending order.",
                        "Customers Table:\nName      | Country\nBerglunds | Sweden\nAlfreds   | Germany",
                        "SELECT * FROM Customers ORDER BY Country;",
                        "• ORDER BY sorts in Ascending order by default.\n• Use DESC keyword to sort in Descending order.\n\nExample:\nSELECT * FROM Customers ORDER BY Country DESC;"
                );

            case "AND, OR, NOT":
                return new Page(
                        "SQL AND, OR, NOT",
                        "The WHERE clause can be combined with AND, OR, and NOT operators.",
                        "Customers Table:\nName   | Country | City\nAlfred | Germany | Berlin\nFrank  | Germany | Munich",
                        "SELECT * FROM Customers \nWHERE Country='Germany' AND City='Berlin';",
                        "• AND: Displays a record if ALL conditions are TRUE.\n• OR: Displays a record if ANY condition is TRUE.\n• NOT: Displays a record if the condition is NOT TRUE."
                );

            // --- MODIFYING DATA ---
            case "INSERT INTO":
                return new Page(
                        "SQL INSERT INTO",
                        "The INSERT INTO statement is used to insert new records in a table.",
                        "Current Table:\nID | Name\n1  | Tom",
                        "INSERT INTO Customers (Name, City) \nVALUES ('Cardinal', 'Stavanger');",
                        "You can specify the column names and values to be inserted.\n\nIf you are adding values for all columns, you do not need to specify column names in the query."
                );

            case "UPDATE":
                return new Page(
                        "SQL UPDATE",
                        "The UPDATE statement is used to modify the existing records in a table.",
                        "Before Update:\nID | Name   | City\n1  | Alfred | Berlin",
                        "UPDATE Customers \nSET Name = 'Alfred Schmidt', City= 'Frankfurt'\nWHERE ID = 1;",
                        "⚠️ Be careful when updating records! If you omit the WHERE clause, ALL records will be updated!"
                );

            case "DELETE":
                return new Page(
                        "SQL DELETE",
                        "The DELETE statement is used to delete existing records in a table.",
                        "Customers Table:\nID | Name\n1  | Alfred\n2  | Maria",
                        "DELETE FROM Customers WHERE Name='Alfred';",
                        "⚠️ Be careful! If you omit the WHERE clause, ALL records in the table will be deleted!"
                );

            // --- ADVANCED ---
            case "MIN and MAX":
                return new Page(
                        "SQL MIN() and MAX()",
                        "MIN() returns the smallest value. MAX() returns the largest value.",
                        "Products Table:\nItem  | Price\nBread | 2.50\nMilk  | 1.10",
                        "SELECT MIN(Price) FROM Products;",
                        "Syntax:\nSELECT MAX(column_name) FROM table_name;\nSELECT MIN(column_name) FROM table_name;"
                );

            case "COUNT, AVG, SUM":
                return new Page(
                        "SQL COUNT(), AVG(), SUM()",
                        "Aggregate functions perform a calculation on a set of values.",
                        "Orders Table:\nID | Amount\n1  | 100\n2  | 200",
                        "SELECT SUM(Amount) FROM Orders;",
                        "• COUNT(): Returns the number of rows.\n• AVG(): Returns the average value.\n• SUM(): Returns the total sum of a numeric column."
                );

            case "LIKE (Wildcards)":
                return new Page(
                        "SQL LIKE Operator",
                        "The LIKE operator is used in a WHERE clause to search for a specified pattern.",
                        "Customers Table:\nName\nAlfreds\nAna Trujillo",
                        "SELECT * FROM Customers WHERE Name LIKE 'a%';",
                        "Wildcards:\n• % represents zero, one, or multiple characters.\n• _ represents a single character.\n\n'a%' -> Starts with 'a'\n'%a' -> Ends with 'a'\n'%or%' -> Contains 'or'"
                );

            case "JOINS":
                return new Page(
                        "SQL JOINS",
                        "A JOIN clause is used to combine rows from two or more tables, based on a related column between them.",
                        "Orders: (OrderID, CustID)\nCustomers: (CustID, Name)",
                        "SELECT Orders.OrderID, Customers.Name\nFROM Orders\nINNER JOIN Customers ON Orders.CustID=Customers.CustID;",
                        "Different Types of SQL JOINs:\n\n• (INNER) JOIN: Returns records that have matching values in both tables\n• LEFT JOIN: Returns all records from the left table\n• RIGHT JOIN: Returns all records from the right table\n• FULL OUTER JOIN: Returns all records when there is a match in either left or right table"
                );

            case "GROUP BY":
                return new Page(
                        "SQL GROUP BY",
                        "The GROUP BY statement groups rows that have the same values into summary rows.",
                        "Sales Table:\nID | Country | Amount\n1  | USA     | 100\n2  | USA     | 200\n3  | UK      | 300",
                        "SELECT Country, SUM(Amount) \nFROM Sales \nGROUP BY Country;",
                        "GROUP BY is often used with aggregate functions (COUNT, MAX, MIN, SUM, AVG) to group the result-set by one or more columns."
                );

            // --- DATABASE STRUCTURE ---
            case "CREATE TABLE":
                return new Page(
                        "SQL CREATE TABLE",
                        "The CREATE TABLE statement is used to create a new table in a database.",
                        "No visual table yet (Creating one...)",
                        "CREATE TABLE Persons (\n    PersonID int,\n    LastName varchar(255),\n    FirstName varchar(255),\n    Address varchar(255)\n);",
                        "The column parameters specify the names of the columns of the table.\nThe datatype parameter specifies the type of data the column can hold (e.g., varchar, integer, date, etc.)."
                );

            case "DROP TABLE":
                return new Page(
                        "SQL DROP TABLE",
                        "The DROP TABLE statement is used to drop an existing table in a database.",
                        "Existing Table: Persons",
                        "DROP TABLE Persons;",
                        "⚠️ Be careful! dropping a table will result in loss of complete information stored in the table!"
                );

            default:
                return new Page("Topic Not Found", "Content is under construction.", "", "", "");
        }
    }
}