package main.java;

import main.java.csvtools.csvTools;
import main.java.db.dbTools;
import main.java.db.dbConnection;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        /*
          Подключается к базе данных catalog и создает таблицу Products, если она еще не создана.
          Заполняет таблицу данными из catalog.csv.
         */
        dbConnection connect = new dbConnection();
        var connection = connect.getConnection();
        csvTools csv = new csvTools(connection);
        try {
            var data = csvTools.ParseProductCsv("./catalog.csv");
            dbTools.updateProducts(connection, data);
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
