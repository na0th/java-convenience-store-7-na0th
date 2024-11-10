package store.dto.request;

public class ProductOrderDto {
    private String productName;
    private int productQuantity;

    public ProductOrderDto(String productName, Integer productQuantity) {
        this.productName = productName;
        this.productQuantity = productQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "productName='" + productName + '\'' +
                ", productQuantity=" + productQuantity +
                '}';
    }

    public static ProductOrderDto create(String productName, Integer productQuantity) {
        return new ProductOrderDto(productName, productQuantity);
    }
}
