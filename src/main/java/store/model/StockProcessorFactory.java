package store.model;

import store.model.stockProcessStrategy.PromotionStockStrategy;
import store.model.stockProcessStrategy.RegularStockStrategy;

public class StockProcessorFactory {
//    private PromotionChecker promotionChecker;
//
//    public StockProcessorFactory(PromotionChecker promotionChecker) {
//        this.promotionChecker = promotionChecker;
//    }

    public StockProcessor getProcessor(Boolean promotionValid) {
        if (promotionValid) {
            return new PromotionStockStrategy();
        }
        return new RegularStockStrategy();
    }
}
