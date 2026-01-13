package com.myfirstproject;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {

    private static Connection connection;

    private DatabaseConnection() {
        // Private constructor to prevent instantiation
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load configurations using Dotenv
                // explicit directory to avoid classpath issues
                Dotenv dotenv = Dotenv.configure().directory("./").load();
                String url = dotenv.get("DB_URL");
                String userName = dotenv.get("DB_USER");
                String password = dotenv.get("DB_PASSWORD");
                String driver = null;

                // Load driver from properties file if available
                Properties props = new Properties();
                try (InputStream input = DatabaseConnection.class.getClassLoader()
                        .getResourceAsStream("db.properties")) {
                    if (input != null) {
                        props.load(input);
                        driver = props.getProperty("db.driver");
                    }
                }

                if (url == null || userName == null) {
                    // Fallback or error if env vars are missing
                    System.out.println("Warning: DB_URL or DB_USER not found in .env");
                }

                // Load driver class
                Class.forName(driver != null ? driver : "com.mysql.cj.jdbc.Driver");

                // Establish connection
                connection = DriverManager.getConnection(url, userName, password);
                System.out.println("Connected Successfully");

            } catch (Exception e) {
                System.out.println("Database Connection Failed: " + e.getMessage());
                throw new RuntimeException("Failed to connect to database", e);
            }
        }
        return connection;
    }
}
