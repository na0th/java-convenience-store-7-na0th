package store.model.processStrategy;

public class StockProcessorFactory {

    public StockProcessor getStrategy(Boolean promotionValid) {
        if (promotionValid) {
            return new PromotionStockStrategy();
        }
        return new RegularStockStrategy();
    }
}
