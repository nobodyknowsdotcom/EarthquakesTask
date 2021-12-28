import csv.CsvTools;
import db.*;
import histogram.Histogram;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        /*
         * Подключается к базе данных по пути ./catalog.sqlite и парсит в Products все из ./catalog.csv
         * Если в БД уже существует таблица Product, то она удаляется и заполняется данными из ./catalog.csv
         * CreateHistogram создает гистограмму со средними ценами товаров, представленных в БД несколькими видами
         * SaveHistogram сохраняет изображение гистограммы в папку проекта.
         */
        var connection = Db.getConnection("jdbc:sqlite:./catalog.sqlite");
        try {
            var data = CsvTools.ParseProductCsv("./catalog.csv");
            Db.updateProducts(connection, data);

            var histogram = Histogram.CreateHistogram(
                    "Средняя цена товаров, имеющих несколько разновидностей",
                    connection,
                    "Товары",
                    "Средняя цена, р.");
            Histogram.SaveHistogram(histogram, "ProductsAveragePrice.png", 1000, 600);
            if (connection != null) {
                connection.close();
            }
        } catch (IOException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
