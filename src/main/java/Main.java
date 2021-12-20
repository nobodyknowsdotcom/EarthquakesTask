import main.java.csvtools.CsvTools;
import main.java.db.DbTools;
import main.java.db.DbConnection;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        /*
          Подключается к базе данных catalog и создает таблицу Products, если она еще не создана.
          Заполняет таблицу данными из catalog.csv.
         */
        DbConnection connect = new DbConnection();
        var connection = connect.getConnection();
        CsvTools csv = new CsvTools(connection);
        try {
            var data = CsvTools.ParseProductCsv("./catalog.csv");
            DbTools.updateProducts(connection, data);
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
