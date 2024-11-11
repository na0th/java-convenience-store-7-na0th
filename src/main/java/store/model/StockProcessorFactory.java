package store.model;

import store.model.stockProcessStrategy.PromotionStockStrategy;
import store.model.stockProcessStrategy.RegularStockStrategy;

public class StockProcessorFactory {

    public StockProcessor getProcessor(Boolean promotionValid) {
        if (promotionValid) {
            return new PromotionStockStrategy();
        }
        return new RegularStockStrategy();
    }
}
