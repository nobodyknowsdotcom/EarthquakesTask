package csv;

import main.java.product.Product;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvTools {

    public CsvTools() {}

    public static List<Product> ParseProductCsv(String filePath) throws IOException {
        List<Product> products = new ArrayList<>();
        List<String> fileLines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        fileLines.remove(0);
        for (String line : fileLines) {
            String[] split = line.split(";");
            products.add(getProductFromLine(split));
        }
        return products;
    }

    private static Product getProductFromLine(String[] split) {
        ArrayList<String> parsedLine = new ArrayList<>();

        for (String s : split) {
            if (IsColumnPart(s)) {
                String lastText = parsedLine.get(parsedLine.size() - 1);
                parsedLine.set(parsedLine.size() - 1, lastText + "," + s);
            } else {
                parsedLine.add(s);
            }
        }
        Product product = new Product();
        product.Name = parsedLine.get(0);
        product.Id = parsedLine.get(1);
        product.Price = Double.parseDouble(parsedLine.get(2));
        product.Amount = Integer.parseInt(parsedLine.get(3));
        return product;
    }

    private static boolean IsColumnPart(String text) {
        String trimText = text.trim();
        return trimText.indexOf("\"") == trimText.lastIndexOf("\"") && trimText.endsWith("\"");
    }
}