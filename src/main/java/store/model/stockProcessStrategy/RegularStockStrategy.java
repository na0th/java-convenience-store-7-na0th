package store.model.stockProcessStrategy;

import store.model.Product;
import store.model.StockProcessor;
import store.model.WareHouse;

public class RegularStockStrategy implements StockProcessor {

    @Override
    public void processStock(WareHouse wareHouse, Product product, int quantity) {
        // 일반 재고 처리 로직


    }
}
