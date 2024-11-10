package store.service;

import store.dto.request.OrderRequest;
import store.dto.request.ProductOrderDto;
import store.dto.response.ReceiptDto;
import store.dto.response.ReceiptSingleDto;
import store.model.*;
import store.model.stockProcessStrategy.PromotionStockStrategy;

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

    public String generatePromotionAvailabilityMessage (OrderRequest orderRequest) {
        StringBuilder message = new StringBuilder();

        for (ProductOrderDto productOrder : orderRequest.getProducts()) {
            Product product = wareHouse.findByProductName(productOrder.getProductName());
            int quantity = productOrder.getProductQuantity();

            boolean promotionValid = promotionChecker.isPromotionValid(product.getPromotionName());
            StockProcessor processor = stockProcessorFactory.getProcessor(promotionValid);

            if (promotionValid && product.canProvideFreeItem() && processor instanceof PromotionStockStrategy) {
                int freeItemCount = promotionChecker.calculateFreeItems(product.getPromotionName(), quantity);
                if (freeItemCount > 0) {
                    message.append(String.format("%s 상품에 대해 %d개의 추가 무료 아이템을 받을 수 있습니다. 추가 받으시겠습니까? (Y/N)\n", product.getName(), freeItemCount));
                }
            }
        }
        return message.toString();
    }

    public void purchase(OrderRequest orderRequest, Boolean applyDiscount, Boolean acceptFreeItem) {
        ReceiptDto receiptDto = new ReceiptDto();

        for (ProductOrderDto productOrder : orderRequest.getProducts()) {
            String productName = productOrder.getProductName();
            int quantity = productOrder.getProductQuantity();
            quantity = conditionallyAddFreeItem(acceptFreeItem, quantity);

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
        discountCalculator.generateReceipt(applyDiscount, receiptDto);

    }

    public ReceiptSingleDto processPurchase(Product product, int quantity, boolean proceedWithPurchase) {
        if (!proceedWithPurchase) {
            System.out.printf("%s 상품 구매가 취소되었습니다.\n", product.getName());
            return null;  // 구매를 취소하고 null 반환
        }

        // 프로모션 여부에 따라 StockProcessor 선택
        boolean promotionValid = promotionChecker.isPromotionValid(product.getPromotionName());
        StockProcessor processor = stockProcessorFactory.getProcessor(promotionValid);

        // 최종적으로 WareHouse에서 재고를 처리하고 영수증 생성
        return wareHouse.processStock(product, quantity, processor);
    }

    private int conditionallyAddFreeItem(Boolean acceptFreeItem, int quantity) {
        if (acceptFreeItem) {
            quantity += 1;
        }
        return quantity;
    }

    public int calculateNonPromotionQuantity(Product product, int quantity) {
        int group = product.sumRegularAndPromotionQuantity();
        return product.getNonPromotionQuantity(quantity, group);
    }
}
