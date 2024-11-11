package store.model;

import store.model.processStrategy.PromotionStockStrategy;
import store.model.processStrategy.RegularStockStrategy;

public class StockProcessorFactory {

    public StockProcessor getStrategy(Boolean promotionValid) {
        if (promotionValid) {
            return new PromotionStockStrategy();
        }
        return new RegularStockStrategy();
    }
}
