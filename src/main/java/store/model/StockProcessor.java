package store.model;

public interface StockProcessor {
    void processStock(WareHouse wareHouse, Product product, int quantity);

}
