package main;

import java.sql.*;

public class dbconnection {
    private Connection connection = null;

    public dbconnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:./src/catalog.sqlite");
            System.out.println("Connected to database successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
