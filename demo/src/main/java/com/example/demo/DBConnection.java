package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/studentdb",
                    "postgres",
                    "YOUR_PASSWORD_HERE"
            );

        } catch (Exception e) {
            System.err.println("❌ Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        if (connect() != null) {
            System.out.println("✅ SUCCESS: Connected to PostgreSQL!");
        }
    }
}
