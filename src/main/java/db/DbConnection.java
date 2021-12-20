package db;

import java.sql.*;

public class DbConnection {
    public static Connection getConnection(String path){
        try {
            Class.forName("org.sqlite.JDBC");
            var conn = DriverManager.getConnection(path);
            System.out.println("Connected to database successfully");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
