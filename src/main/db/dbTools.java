package main.db;

import main.Product.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

public class dbTools {
    public static void updateProducts(Connection conn, List<Product> products) {
        Connection connection;
        Statement statement;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:./catalog.sqlite");
            statement = connection.createStatement();
            String drop_sql = "DROP TABLE IF EXISTS Products";
            statement.executeUpdate(drop_sql);
            String create_sql = "CREATE TABLE Products " +
                    "(NAME     CHAR(50) NOT NULL, " +
                    "PRODUCTID CHAR(10) PRIMARY KEY NOT NULL," +
                    "PRICE   FLOAT  NOT NULL, " +
                    "COUNT      INT  NOT NULL )";
            statement.executeUpdate(create_sql);
            int i = 0;

            while (i < products.size()){
                var product = products.get(i);
                String name = product.Name;
                String id = product.Id;
                double price = product.Price;
                int amount = product.Amount;

                String query = "INSERT INTO Products VALUES (" +
                        "'" + name + "', " +
                        "'" + id + "', " +
                        "'" + price +  "', " +
                        "'" + amount + "')";
                statement.addBatch(query);
                i++;
            }
            statement.executeBatch();
            statement.close();
            connection.close();
            System.out.println("Successfully updated " + i + " lines");
        } catch ( Exception e ) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
