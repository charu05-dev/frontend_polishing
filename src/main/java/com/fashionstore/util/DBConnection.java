

package com.fashionstore.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/fashion_store";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "thequeen:05";

    public static Connection getConnection() {

        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(
                URL,
                USERNAME,
                PASSWORD
            );

            System.out.println("Database connected successfully!");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }

        return connection;
    }
}


