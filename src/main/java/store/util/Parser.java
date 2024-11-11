package store.util;

import java.util.HashMap;
import java.util.Map;

public class Parser {
    private static final String DELIMITER = ",";

    public static Map<String, Integer> parse(String input) {
        Map<String, Integer> productMap = new HashMap<>();

        validateEmptyInput(input);
        validateInputPattern(input);
        String[] items = input.split(DELIMITER);
        parseAllItems(items, productMap);

        return productMap;
    }

    private static void validateInputPattern(String input) {
        if (!input.matches("(\\[[^\\[\\]]+-\\d+])(,(\\[[^\\[\\]]+-\\d+]))*")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private static void validateEmptyInput(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private static void parseAllItems(String[] items, Map<String, Integer> productMap) {
        for (String item : items) {
            if (item.trim().isEmpty()) {
                throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
            }
            parseItem(item, productMap);
        }
    }

    private static void parseItem(String item, Map<String, Integer> productMap) {
        validateBrackets(item);
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
            int quantity = Integer.parseInt(quantityStr);
            validateIsPositiveQuantity(quantity);
            return quantity;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private static void validateIsPositiveQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private static void validateBrackets(String item) {
        // 대괄호가 정확히 한 쌍인지 확인
        long openingBracketCount = item.chars().filter(c -> c == '[').count();
        long closingBracketCount = item.chars().filter(c -> c == ']').count();

        if (openingBracketCount != 1 || closingBracketCount != 1 || !item.startsWith("[") || !item.endsWith("]")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public static Boolean parseYesOrNo(String input) {
        if ("Y".equalsIgnoreCase(input)) {
            return Boolean.TRUE;
        }
        if ("N".equalsIgnoreCase(input)) {
            return Boolean.FALSE;
        }
        throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }


}
