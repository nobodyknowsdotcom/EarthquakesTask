package db;

import model.Earthquake;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Db {
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
    public static void update(List<Earthquake> earthquakes) {
        Connection connection;
        Statement statement;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:./catalog.sqlite");
            statement = connection.createStatement();
            String drop_sql = "DROP TABLE IF EXISTS Earthquakes";
            statement.executeUpdate(drop_sql);
            String create_sql = "CREATE TABLE Earthquakes " +
                    "(ID     CHAR(50) PRIMARY KEY NOT NULL, " +
                    "DEPTH INT NOT NULL," +
                    "TYPE   CHAR(30)  NOT NULL, " +
                    "MAGNITUDE      FLOAT  NOT NULL," +
                    "STATE      CHAR(30)  NOT NULL," +
                    "YEAR      INT  NOT NULL )";

            statement.executeUpdate(create_sql);
            int i = 0;

            while (i < earthquakes.size()){
                var earthquake = earthquakes.get(i);

                String query = "INSERT INTO Earthquakes VALUES (" +
                        "'" + earthquake.getId() + "', " +
                        "'" + earthquake.getDepth() + "', " +
                        "'" + earthquake.getType() +  "', " +
                        "'" + earthquake.getMagnitude() +  "', " +
                        "'" + earthquake.getState() +  "', " +
                        "'" + earthquake.getYear() + "')";
                statement.addBatch(query);
                i++;
            }
            statement.executeBatch();
            statement.close();
            connection.close();
            System.out.printf("Successfully updated %s lines%n", i);
        } catch ( Exception e ) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static int getEarthquakesCountByYear(Connection conn, int year) throws SQLException {
        String query = String.format("SELECT COUNT(*) FROM Earthquakes WHERE YEAR=%s", year);
        Statement statement = conn.createStatement();
        statement.execute(query);
        var result = statement.getResultSet();
        return result.getInt("COUNT(*)");
    }

    public static List<Integer> getAllYears(Connection conn) throws SQLException {
        List<Integer> result = new ArrayList<>();
        String query = "SELECT DISTINCT YEAR FROM Earthquakes ORDER BY YEAR";
        Statement statement = conn.createStatement();
        statement.execute(query);
        ResultSet rs = statement.getResultSet();

        while (rs.next()) {
            int i = rs.getInt("YEAR");
            result.add(i);
        }
        return result;
    }
}
