package store.model.processStrategy;

import store.model.Product;
import store.repository.WareHouse;

public interface StockProcessor {
    void processStock(WareHouse wareHouse, Product product, int quantity);

}
