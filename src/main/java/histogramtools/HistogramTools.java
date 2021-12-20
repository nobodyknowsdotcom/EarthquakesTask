package histogramtools;

import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import db.DbTools;
import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;

public class HistogramTools {
    public static void CreateHistogram(String filename, Connection conn, String XAxis, String YAxis, int width, int height) {
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
            addMedianPrice(conn, dataset, "Фильтр для аквариума");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String title = filename.split("\\.")[0];
        JFreeChart chart = ChartFactory.createBarChart(
                title,
                XAxis,
                YAxis,
                dataset,
                PlotOrientation.VERTICAL,
                true,                     // include legend
                false,                     // tooltips
                false                     // URLs
        );

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(212, 212, 212));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint (Color.white);
        SaveHistogram(chart, filename, width, height);
    }

    private static void addMedianPrice(Connection conn, DefaultCategoryDataset dataset, String rowKey) throws SQLException {
        dataset.addValue(DbTools.getMedianPrice(conn, rowKey), rowKey, "");
    }

    private static void SaveHistogram(JFreeChart chart, String name, int width, int height) {
        try {
            ChartUtilities.saveChartAsPNG(new File(name), chart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Histogram \"" +name+ "\" saved successfully!");
    }
}