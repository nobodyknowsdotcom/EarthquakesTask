package histogram;

import java.awt.*;
import java.io.*;
import java.sql.Array;
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
            for (int i : Db.getAllYears(conn)){
                int count = Db.getEarthquakesCountByYear(conn, i);
                dataset.addValue(count, Integer.toString(i), "");
            }
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

    public static void SaveHistogram(JFreeChart chart, String name, int width, int height) {
        try {
            ChartUtilities.saveChartAsPNG(new File("./pics/" + name), chart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Histogram \"" +name+ "\" saved successfully!");
    }
}