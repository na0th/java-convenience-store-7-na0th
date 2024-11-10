package store.model;


import store.dto.response.ReceiptSingleDto;
import store.model.stockProcessStrategy.PromotionStockStrategy;
import store.model.stockProcessStrategy.RegularStockStrategy;

import java.util.Map;
import java.util.NoSuchElementException;

public class WareHouse {

    private Map<String, Product> products;

    public WareHouse(Map<String, Product> products) {
        this.products = products;
    }

    public Product findByProductName(String productName) {
        Product product = products.get(productName);
        if (product == null) {
            throw new NoSuchElementException("해당 제품을 찾을 수 없습니다: " + productName);
        }
        return product;
    }

    public ReceiptSingleDto processStock(Product product, int quantity, StockProcessor processor) {
        if (processor instanceof PromotionStockStrategy) {
            return decrementPromotionStock(product, quantity);
            //decrementPromotionStock(product, quantity);
        }
        if (processor instanceof RegularStockStrategy) {
            return decrementRegularStock(product, quantity);
            //decrementRegularStock(product, quantity)
        }
        throw new IllegalArgumentException("존재하지 않는 재고 처리 전략입니다 : " + processor.getClass().getName());
    }

    private ReceiptSingleDto decrementRegularStock(Product product, int quantity) {
        Product foundProduct = findByProductName(product.getName());
        foundProduct.decrementRegularStock(quantity);
        ReceiptSingleDto receiptSingleDto = ReceiptSingleDto.create(product, quantity, 0);
        return receiptSingleDto;
    }

    private ReceiptSingleDto decrementPromotionStock(Product product, int quantity) {
//        int group = product.sumRegularAndPromotionQuantity();
//        int nonPromotionQuantity = product.getNonPromotionQuantity(quantity, group);

//        if (unprocessedStock > ) {}
        System.out.println("프로모션 재고 처리 구현 해야 함");
//        return ReceiptSingleDto.create(product, quantity, 0);
        return null;
    }

    public void getAllProducts() {
        products.values().forEach(System.out::println); // 모든 Product 객체의 toString() 출력
    }


}
