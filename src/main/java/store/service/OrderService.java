package store.service;

import store.dto.request.OrderRequest;
import store.dto.request.ProductOrderDto;
import store.dto.response.ReceiptDto;
import store.dto.response.ReceiptSingleDto;
import store.model.*;
import store.model.stockProcessStrategy.PromotionStockStrategy;
import store.view.InputView;

import java.util.Map;

public class OrderService {
    private WareHouse wareHouse;
    private PromotionChecker promotionChecker;
    private StockProcessorFactory stockProcessorFactory;
    private InputView inputView;

    public OrderService(WareHouse wareHouse, PromotionChecker promotionChecker, StockProcessorFactory stockProcessorFactory, InputView inputView) {
        this.wareHouse = wareHouse;
        this.promotionChecker = promotionChecker;
        this.stockProcessorFactory = stockProcessorFactory;
        this.inputView = inputView;
    }

    public String generatePromotionAvailabilityMessage (OrderRequest orderRequest) {
        StringBuilder message = new StringBuilder();

        for (ProductOrderDto productOrder : orderRequest.getProducts()) {
            Product product = wareHouse.findByProductName(productOrder.getProductName());
            int quantity = productOrder.getProductQuantity();

            boolean promotionValid = promotionChecker.isPromotionValid(product.getPromotionName());
            StockProcessor processor = stockProcessorFactory.getStrategy(promotionValid);

            if (promotionValid && product.canProvideFreeItem() && processor instanceof PromotionStockStrategy) {
                int freeItemCount = promotionChecker.calculateFreeItems(product.getPromotionName(), quantity);
                if (freeItemCount > 0) {
                    message.append(String.format("%s 상품에 대해 %d개의 추가 무료 아이템을 받을 수 있습니다. 추가 받으시겠습니까? (Y/N)\n", product.getName(), freeItemCount));
                }
            }
        }
        return message.toString();
    }

    public ReceiptDto finalPurchase(OrderRequest orderRequest,Boolean acceptFreeItem) {
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
                return null;
            }
            boolean promotionValid = promotionChecker.isPromotionValid(product.getPromotionName());
            StockProcessor processor = stockProcessorFactory.getStrategy(promotionValid);

            int promotionGroupSize = calculatePromotionGroupSize(promotionValid, product);

            quantity = adjustQuantityBasedOnNonPromotionAcceptance(product, quantity, processor, productName, promotionValid);
            ReceiptSingleDto receiptSingleDto = wareHouse.processStock(product, quantity, processor, promotionGroupSize);

            receiptDto.addSingleReceipt(receiptSingleDto);

        }
        return receiptDto;

    }

    private int calculatePromotionGroupSize(boolean promotionValid, Product product) {
        int promotionGroupSize = 1;
        if (promotionValid) {
            Promotion foundPromotion = promotionChecker.findByPromotionName(product.getPromotionName());
            promotionGroupSize = foundPromotion.calculatePromotionGroupSize();
        }
        return promotionGroupSize;
    }

    private int adjustQuantityBasedOnNonPromotionAcceptance(Product product, int quantity, StockProcessor processor, String productName, Boolean promotionValid) {
        if (promotionValid) {
            Promotion foundPromotion = promotionChecker.findByPromotionName(product.getPromotionName());
            int promotionGroupSize = foundPromotion.calculatePromotionGroupSize();

            int nonPromotionQuantity = wareHouse.checkNonPromotionQuantity(product, quantity, promotionGroupSize, processor);
            if (nonPromotionQuantity > 0) {
                Boolean acceptNonPromotionPurchase = false;
                try {
                    acceptNonPromotionPurchase = inputView.getConfirmPurchaseWithoutPromotion(productName, nonPromotionQuantity);
                } catch (IllegalArgumentException e) {
                    throw e;
                }
                quantity = removeNonPromotionStock(acceptNonPromotionPurchase, quantity, nonPromotionQuantity);
            }
        }

        return quantity;
    }

    private int removeNonPromotionStock(Boolean acceptNonPromotionPurchase, int quantity, int nonPromotionQuantity) {
        if (!acceptNonPromotionPurchase) {
            quantity -= nonPromotionQuantity;
        }
        return quantity;
    }

    private int conditionallyAddFreeItem(Boolean acceptFreeItem, int quantity) {
        if (acceptFreeItem) {
            quantity += 1;
        }
        return quantity;
    }

    public Map<String, Product> getAllProducts() {
        return wareHouse.getAllProducts();
    }

    public void validOrderStock(OrderRequest orderRequest) {
        for (ProductOrderDto productOrder : orderRequest.getProducts()) {
            Product product = wareHouse.findByProductName(productOrder.getProductName());
            int quantity = productOrder.getProductQuantity();

            if (!canPurchaseProduct(product, quantity)) {
                throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        }
    }

    public Boolean canPurchaseProduct(Product product, int quantity) {
        return product.sumRegularAndPromotionQuantity() >= quantity;
    }


}
