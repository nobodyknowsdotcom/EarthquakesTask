import csv.CsvTools;
import db.*;
import histogram.Histogram;
import model.Earthquake;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        /*
         * Подключается к базе данных по пути ./catalog.sqlite и парсит в Earthquakes все из ./Earthquakes.csv
         * Если в БД уже существует таблица Earthquakes, то она удаляется и заполняется данными из ./Earthquakes.csv
         */
        var connection = Db.getConnection("jdbc:sqlite:./catalog.sqlite");
        try {
            List<Earthquake> data = CsvTools.ParseProductCsv("./Earthquakes.csv");
            Db.update(data);

            var histogram = Histogram.CreateHistogram(
                    "Среднее кол-во землятресений в год",
                    connection,
                    "Год",
                    "Количество землятресений");
            Histogram.SaveHistogram(histogram, "Earthquakes_By_Years.png", 1000, 600);
            if (connection != null) {
                connection.close();
            }
        } catch (IOException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
