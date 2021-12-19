package main;

import main.db.csvTools;
import main.db.dbTools;
import main.db.dbСonnection;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        /*
          Подключается к базе данных по указанному пути и создает таблицу Products, если она еще не создана.
          Заполняет таблицу данными из catalog.csv.
         */
        dbСonnection connect = new dbСonnection();
        var connection = connect.getConnection();
        csvTools csv = new csvTools(connection);
        try {
            var data = csvTools.ParseProductCsv("catalog.csv");
            dbTools.updateProducts(connection, data);
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
