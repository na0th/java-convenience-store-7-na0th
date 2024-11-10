package store.dto.response;

import store.model.Product;

public class ReceiptSingleDto {
    private String productName;
    private int regularStockDeducted;
    private int promotionStockDeducted;
    private int productPrice;

    public ReceiptSingleDto(String productName, int productPrice, int regularStockDeducted, int promotionStockDeducted) {
        this.productName = productName;
        this.regularStockDeducted = regularStockDeducted;
        this.promotionStockDeducted = promotionStockDeducted;
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public int getRegularStockDeducted() {
        return regularStockDeducted;
    }

    public int getPromotionStockDeducted() {
        return promotionStockDeducted;
    }

    public int getProductPrice() {
        return productPrice;
    }
    public static ReceiptSingleDto create(Product product, int regularStockDeducted, int promotionStockDeducted) {
        return new ReceiptSingleDto(product.getName(), product.getPrice(), regularStockDeducted, promotionStockDeducted);
    }
}
