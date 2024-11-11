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
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private static int parseQuantity(String quantityStr, String productName) {
        try {
            return Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }
}
