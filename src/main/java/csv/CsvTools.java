package csv;

import model.Earthquake;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvTools {
    public static List<Earthquake> ParseProductCsv(String filePath) throws IOException {
        List<Earthquake> earthquakes = new ArrayList<>();
        List<String> fileLines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        fileLines.remove(0);
        for (String line : fileLines) {
            String[] split = line.split(",");
            earthquakes.add(getEarthquakeFromLine(split));
        }
        return earthquakes;
    }

    private static Earthquake getEarthquakeFromLine(String[] split) {
        ArrayList<String> parsedLine = new ArrayList<>();

        for (String s : split) {
            if (IsColumn(s)) {
                String lastText = parsedLine.get(parsedLine.size() - 1);
                parsedLine.set(parsedLine.size() - 1, lastText + "," + s);
            } else {
                parsedLine.add(s);
            }
        }
        Earthquake earthquake = new Earthquake();
        earthquake.setId(parsedLine.get(0));
        earthquake.setDepth(Integer.parseInt(parsedLine.get(1)));
        earthquake.setType(parsedLine.get(2));
        earthquake.setMagnitude(Float.parseFloat(parsedLine.get(3)));
        earthquake.setState(parsedLine.get(4));
        earthquake.setYear(Integer.parseInt(parsedLine.get(5).split("-")[0]));
        return earthquake;
    }

    private static boolean IsColumn(String text) {
        String trimText = text.trim();
        return trimText.indexOf("\"") == trimText.lastIndexOf("\"") && trimText.endsWith("\"");
    }
}