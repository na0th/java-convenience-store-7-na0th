package store.view;

import store.model.Product;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class OutputView {
    public void printProducts(Map<String, Product> products) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA);
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            Product product = entry.getValue();
            String name = product.getName();
            int price = product.getPrice();
            String printPrice = numberFormat.format(price);
            int regularStockQuantity = product.getRegularStock();
            int promotionStockQuantity = product.getPromotionStock();
            String promotionLabel = product.getPromotionName() != null ? product.getPromotionName() : ""; // Null 확인

            // 프로모션 재고 출력
            if (promotionStockQuantity > 0) {
                System.out.println("- " + name + " " + printPrice + "원 " + promotionStockQuantity + "개 " + promotionLabel);
            } else if (!promotionLabel.isEmpty()) { // 프로모션 라벨이 있지만 재고가 없는 경우
                System.out.println("- " + name + " " + printPrice + "원 재고 없음 " + promotionLabel);
            }

            // 일반 재고 출력
            if (regularStockQuantity > 0) {
                System.out.println("- " + name + " " + printPrice + "원 " + regularStockQuantity + "개");
            } else {
                System.out.println("- " + name + " " + printPrice + "원 재고 없음");
            }
        }
    }

    public void printReceipt() {
        System.out.println("=====W 편의점=====");
        System.out.println("상품명\t\t수량\t금액");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

}
