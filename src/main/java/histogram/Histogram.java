package histogram;

import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import db.Db;
import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;

public class Histogram {
    public static JFreeChart CreateHistogram(String title, Connection conn, String XAxis, String YAxis) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            addMedianPrice(conn, dataset, "Аквариум");
            addMedianPrice(conn, dataset, "Автокормушка");
            addMedianPrice(conn, dataset, "Фильтр для аквариума");
            addMedianPrice(conn, dataset, "Щетка Catidea");
            addMedianPrice(conn, dataset, "Туалет Catidea");
            addMedianPrice(conn, dataset, "Кормушка педальная");
            addMedianPrice(conn, dataset, "Термометр");
            addMedianPrice(conn, dataset, "Нагреватель для аквариума");
            addMedianPrice(conn, dataset, "Помпа для аквариума");
            addMedianPrice(conn, dataset, "Лоток для кормления цыплят");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JFreeChart chart = ChartFactory.createBarChart(
            title,
            XAxis,
            YAxis,
            dataset,
            PlotOrientation.VERTICAL,
            true,
            false,
            false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(212, 212, 212));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint (Color.white);
        return chart;
    }

    private static void addMedianPrice(Connection conn, DefaultCategoryDataset dataset, String rowKey) throws SQLException {
        dataset.addValue(Db.getMedianPrice(conn, rowKey), rowKey, "");
    }

    public static void SaveHistogram(JFreeChart chart, String name, int width, int height) {
        try {
            ChartUtilities.saveChartAsPNG(new File("./pics/" + name), chart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Histogram \"" +name+ "\" saved successfully!");
    }
}