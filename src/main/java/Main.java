import csvtools.CsvTools;
import db.*;
import histogramtools.HistogramTools;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        /*
         * Подключается к базе данных по пути ./catalog.sqlite и парсит в Products все из ./catalog.csv
         * Если в БД уже существует таблица Product, то она удаляется и заполняется данными из ./catalog.csv
         * CreateHistogram создает гистограмму со средними ценами товаров, представленных в БД несколькими видами
         */
        var connection = DbConnection.getConnection("jdbc:sqlite:./catalog.sqlite");
        CsvTools csv = new CsvTools(connection);
        try {
            var data = CsvTools.ParseProductCsv("./catalog.csv");
            DbTools.updateProducts(connection, data);

            HistogramTools.CreateHistogram("ProductsAveragePrice.png",
                    DbConnection.getConnection("jdbc:sqlite:./catalog.sqlite"),
                    "Товары",
                    "Средняя цена, р.",
                    800,
                    500);

            if (connection != null) {
                connection.close();
            }
        } catch (IOException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
