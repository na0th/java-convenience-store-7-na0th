package store.service;

import store.dto.request.OrderRequest;
import store.dto.request.ProductOrderDto;
import store.dto.response.ReceiptDto;
import store.dto.response.ReceiptSingleDto;
import store.model.*;

public class OrderService {
    private WareHouse wareHouse;
    private DiscountCalculator discountCalculator;
    private PromotionChecker promotionChecker;
    private StockProcessorFactory stockProcessorFactory;

    public OrderService(WareHouse wareHouse, DiscountCalculator discountCalculator, PromotionChecker promotionChecker, StockProcessorFactory stockProcessorFactory) {
        this.wareHouse = wareHouse;
        this.discountCalculator = discountCalculator;
        this.promotionChecker = promotionChecker;
        this.stockProcessorFactory = stockProcessorFactory;
    }

    public void purchase(OrderRequest orderRequest) {
        ReceiptDto receiptDto = new ReceiptDto();
        for (ProductOrderDto productOrder : orderRequest.getProducts()) {
            String productName = productOrder.getProductName();
            int quantity = productOrder.getProductQuantity();
            Product product;

            try {
                product = wareHouse.findByProductName(productName);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
            boolean promotionValid = promotionChecker.isPromotionValid(product.getPromotionName());
            StockProcessor processor = stockProcessorFactory.getProcessor(promotionValid);

            ReceiptSingleDto receiptSingleDto = wareHouse.processStock(product, quantity, processor);
            receiptDto.addSingleReceipt(receiptSingleDto);

        }
        discountCalculator.generateReceipt(Boolean.TRUE, receiptDto);

    }
}
