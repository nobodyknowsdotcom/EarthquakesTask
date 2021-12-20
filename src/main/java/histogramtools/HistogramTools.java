package histogramtools;

import java.io.*;
import java.util.Random;
import org.jfree.chart.*;
import org.jfree.data.statistics.*;
import org.jfree.chart.plot.PlotOrientation;

public class HistogramTools {
    public static void main(String ... args) {
        CreateHistogram("TestHistogram.png", "Name", "Price");
    }


    public static void CreateHistogram(String name, String XAxis, String YAxis) {
        double[] value = new double[100];
        Random generator = new Random();

        for (int i=1; i < 100; i++) {
            value[i] = generator.nextDouble();
            int number = 10;
            HistogramDataset dataset = new HistogramDataset();
            dataset.setType(HistogramType.RELATIVE_FREQUENCY);
            dataset.addSeries("Histogram",value,number);
            String plotTitle = name.split("\\.")[0];
            PlotOrientation orientation = PlotOrientation.VERTICAL;
            boolean show = false;
            boolean toolTips = false;
            boolean urls = false;
            JFreeChart chart = ChartFactory.createHistogram(plotTitle, XAxis, YAxis,
                    dataset, orientation, show, toolTips, urls);
            int width = 800;
            int height = 500;
            SaveHistogram(chart, name, width, height);
        }
    }

    public static void SaveHistogram(JFreeChart chart, String name, int width, int height) {
        try {
            ChartUtilities.saveChartAsPNG(new File(name), chart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}