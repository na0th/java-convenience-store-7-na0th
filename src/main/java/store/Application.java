package store;

import store.config.AppConfig;
import store.controller.OrderController;
import store.dto.request.OrderRequest;
import store.model.Product;
import store.model.Promotion;
import store.model.PromotionChecker;
import store.model.WareHouse;
import store.util.Parser;
import store.view.InputView;
import store.view.OutputView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.LocalDate;
import java.util.*;

public class Application {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        OrderController orderController = appConfig.orderController();

        orderController.run();



//        Map<String, Product> products = productFileHandle();
//        System.out.println();
//        WareHouse wareHouse = new WareHouse(products);
//        wareHouse.getAllProducts();
//
//        System.out.println();
//        List<Promotion> promotions = promotionFileHandle();
//        PromotionChecker promotionChecker = new PromotionChecker(promotions);
//        promotionChecker.getAllPromotions();

    }

    public static Map<String, Product> productFileHandle() {
        Path filePath = Paths.get("src/main/resources/products.md");
        Map<String, Product> products = new LinkedHashMap<>();
        try {
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            lines.stream().skip(1).forEach(line -> {

                String[] values = line.split(",");
                String name = values[0].trim();
                int price = Integer.parseInt(values[1].trim());
                int quantity = Integer.parseInt(values[2].trim());
                String promotionName = values[3].trim().equals("null") ? null : values[3].trim();

                products.putIfAbsent(name, new Product(name, price, 0, 0, promotionName));
                Product product = products.get(name);

                if (promotionName == null) {
                    product.incrementRegularStock(quantity);
                } else {
                    product.incrementPromotionStock(quantity);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("파일 읽기 중 오류가 발생했습니다: " + e.getMessage());
        }
        products.values().forEach(System.out::println);

        return products;
    }

    public static List<Promotion> promotionFileHandle() {
        Path filePath = Paths.get("src/main/resources/promotions.md");
        Map<String, Promotion> promotions = new LinkedHashMap<>();

        try {
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            for (String line : lines.subList(1, lines.size())) { // 첫 줄 건너뛰기
                String[] values = line.split(",");
                String name = values[0].trim();
                String buy = values[1].trim();
                String get = values[2].trim();
                LocalDate startDate = LocalDate.parse(values[3].trim());
                LocalDate endDate = LocalDate.parse(values[4].trim());

                Promotion promotion = new Promotion(name, buy, get, startDate, endDate);
                promotions.put(name, promotion);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(promotions.values());
    }

}
