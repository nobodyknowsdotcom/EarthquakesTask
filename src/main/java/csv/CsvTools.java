package csv;

import main.java.product.Product;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class CsvTools {

    public CsvTools(Connection connection) {
    }

    public static List<Product> ParseProductCsv(String filePath) throws IOException {
        List<Product> products = new ArrayList<>();
        List<String> fileLines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        fileLines.remove(0);

        for (String fileLine : fileLines) {
            String[] splitedText = fileLine.split(";");
            ArrayList<String> columnList = new ArrayList<>();

            for (String s : splitedText) {
                if (IsColumnPart(s)) {
                    String lastText = columnList.get(columnList.size() - 1);
                    columnList.set(columnList.size() - 1, lastText + "," + s);
                } else {
                    columnList.add(s);
                }
            }
            Product product = new Product();
            product.Name = columnList.get(0);
            product.Id = columnList.get(1);
            product.Price = Double.parseDouble(columnList.get(2));
            product.Amount = Integer.parseInt(columnList.get(3));
            products.add(product);
        }
        return products;
    }

    private static boolean IsColumnPart(String text) {
        String trimText = text.trim();
        return trimText.indexOf("\"") == trimText.lastIndexOf("\"") && trimText.endsWith("\"");
    }
}