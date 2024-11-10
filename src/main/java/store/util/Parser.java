package store.util;

import java.util.HashMap;
import java.util.Map;

public class Parser {
    private static final String DELIMITER = ",";

    public static Map<String, Integer> parse(String input) {
        Map<String, Integer> productMap = new HashMap<>();

        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("빈 값은 들어올 수 없습니다.");
        }
        String[] items = input.split(DELIMITER);
        parseAllItems(items, productMap);

        return productMap;
    }

    private static void parseAllItems(String[] items, Map<String, Integer> productMap) {
        for (String item : items) {
            parseItem(item, productMap);
        }
    }

    private static void parseItem(String item, Map<String, Integer> productMap) {
        String[] parts = item.replaceAll("[\\[\\]]", "").split("-");
        if (parts.length == 2) {
            String productName = parts[0].trim();
            int quantity = parseQuantity(parts[1].trim(), productName);
            productMap.put(productName, quantity);
        } else {
            throw new IllegalArgumentException("올바르지 않은 포맷" + item);
        }
    }

    private static int parseQuantity(String quantityStr, String productName) {
        try {
            return Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("올바르지 않은 수량" + productName);
        }
    }
}
