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
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
        regularStock -= quantity;
    }
    public void decrementPromotionStock(int quantity) {
        if (promotionStock < quantity) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
        promotionStock -= quantity;
    }

    public void decrementAllPromotionStock() {
        promotionStock = 0;
    }

    public void incrementRegularStock(int quantity) {
        regularStock += quantity;
    }

    public void incrementPromotionStock(int quantity) {
        promotionStock += quantity;
    }

    public Boolean canProvideFreeItem() {
        return promotionStock >= 1;
    }

    public int sumRegularAndPromotionQuantity() {
        return regularStock + promotionStock;
    }
    public int getNonPromotionQuantity(int quantity, int promotionGroupSize) {
        int maxPromotionQuantity = (promotionStock / promotionGroupSize) * promotionGroupSize;
        int processedQuantity = Math.min(quantity, maxPromotionQuantity);
        int nonPromotionStock = quantity - processedQuantity;

        return nonPromotionStock;
    }



}
