package main.java.db;

import java.sql.*;

public class DbConnection {
    private Connection connection = null;

    public DbConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:./catalog.sqlite");
            System.out.println("Connected to database successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
