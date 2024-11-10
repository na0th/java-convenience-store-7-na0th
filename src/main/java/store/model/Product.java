package store.model;

public class Product {

    private String name;
    private int price;
    private int regularStock;
    private int promotionStock;
    private String promotionName;

    public Product(String name, int price, int regularStock, int promotionStock, String promotionName) {
        this.name = name;
        this.price = price;
        this.regularStock = regularStock;
        this.promotionStock = promotionStock;
        this.promotionName = promotionName;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getRegularStock() {
        return regularStock;
    }

    public int getPromotionStock() {
        return promotionStock;
    }

    public static Product create(String name, int price, int regularStock, int promotionStock, String promotionName) {
        return new Product(name, price, regularStock, promotionStock, promotionName);
    }
    public void decrementRegularStock(int quantity) {
        if (regularStock < quantity) {
            throw new IllegalArgumentException("재고 수량보다 많이 구매하셨습니다!: " + name);
        }
        regularStock -= quantity;
    }

    public void incrementRegularStock(int quantity) {
        regularStock += quantity;
    }

    public void incrementPromotionStock(int quantity) {
        promotionStock += quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", regularStock=" + regularStock +
                ", promotionStock=" + promotionStock +
                ", promotionName='" + promotionName + '\'' +
                '}';
    }



}
