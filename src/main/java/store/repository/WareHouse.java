package store.repository;


import store.dto.response.ReceiptSingleDto;
import store.model.Product;
import store.model.processStrategy.StockProcessor;
import store.model.processStrategy.PromotionStockStrategy;
import store.model.processStrategy.RegularStockStrategy;

import java.util.Map;


public class WareHouse {

    private Map<String, Product> products;

    public WareHouse(Map<String, Product> products) {
        this.products = products;
    }

    public Product findByProductName(String productName) {
        Product product = products.get(productName);
        if (product == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."+ productName);
        }
        return product;
    }

    public ReceiptSingleDto processStock(Product product, int quantity, StockProcessor processor, int promotionGroupSize) {
        if (processor instanceof PromotionStockStrategy) {
            return decrementPromotionStock(product, quantity,promotionGroupSize);
        }
        if (processor instanceof RegularStockStrategy) {
            return decrementRegularStock(product, quantity);
        }
        throw new IllegalArgumentException("존재하지 않는 재고 처리 전략입니다 : " + processor.getClass().getName());
    }

    private ReceiptSingleDto decrementRegularStock(Product product, int quantity) {
        Product foundProduct = findByProductName(product.getName());
        foundProduct.decrementRegularStock(quantity);
        ReceiptSingleDto receiptSingleDto = ReceiptSingleDto.create(product, quantity, 0,1);
        return receiptSingleDto;
    }

    private ReceiptSingleDto decrementPromotionStock(Product product, int quantity, int promotionGroupSize) {
        Product foundProduct = findByProductName(product.getName());

        int AllStocks = foundProduct.sumRegularAndPromotionQuantity();
        int deductedRegularStock = 0;
        int deductedPromotionStock = 0;

        if (quantity > AllStocks) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }

        if (hasEnoughPromotionStock(quantity, foundProduct)) {
            deductedPromotionStock += quantity;
            foundProduct.decrementPromotionStock(quantity);
        } else {
            quantity -= foundProduct.getPromotionStock();
            deductedPromotionStock += foundProduct.getPromotionStock();
            foundProduct.decrementAllPromotionStock();
            foundProduct.decrementRegularStock(quantity);
            deductedRegularStock += quantity;
        }

        ReceiptSingleDto receiptSingleDto = ReceiptSingleDto.create(product, deductedRegularStock, deductedPromotionStock, promotionGroupSize);
        return receiptSingleDto;
    }

    private boolean hasEnoughPromotionStock(int quantity, Product foundProduct) {
        return foundProduct.getPromotionStock() >= quantity;
    }

    public Map<String,Product> getAllProducts() {
        products.values().forEach(System.out::println);
        return products;
    }

    public int checkNonPromotionQuantity(Product product, int quantity, int promotionGroupSize, StockProcessor processor) {
        if (processor instanceof PromotionStockStrategy) {
            return product.getNonPromotionQuantity(quantity, promotionGroupSize);
        }
        //나머지(일반 재고 처리기)
        return 0;
    }


}
