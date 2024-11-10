package store.controller;

import store.dto.request.OrderRequest;
import store.dto.response.ReceiptSingleDto;
import store.model.Product;
import store.service.OrderService;
import store.util.Parser;
import store.view.InputView;
import store.view.OutputView;

import java.util.Map;

public class OrderController {
    private final OrderService orderService;
    private final InputView inputView;
    private final OutputView outputView;

    public OrderController(OrderService orderService, InputView inputView, OutputView outputView) {
        this.orderService = orderService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        String products = inputView.getProducts();
        Map<String, Integer> parsedProducts = Parser.parse(products);
        OrderRequest orderRequest = OrderRequest.from(parsedProducts);

        boolean applyDiscount = inputView.getMembershipDiscount();
        processOrder(applyDiscount, orderRequest);
    }

    private void processOrder(Boolean applyDiscount, OrderRequest orderRequest) {
        String message = orderService.generatePromotionAvailabilityMessage(orderRequest);
        if (!message.isEmpty()) {
            outputView.showMessage(message);
            boolean acceptExtra = inputView.getConfirmAddFreeProducts();
            orderService.purchase(orderRequest, applyDiscount, acceptExtra);
        } else {
            orderService.purchase(orderRequest, applyDiscount, false);
        }
    }

    public ReceiptSingleDto handlePurchaseRequest(Product product, int quantity) {
        int nonPromotionQuantity = orderService.calculateNonPromotionQuantity(product, quantity);

        boolean proceedWithPurchase = true;
        if (nonPromotionQuantity > 0) {
            proceedWithPurchase = agreesToNonPromotion(product, nonPromotionQuantity);
        }

        return orderService.processPurchase(product, quantity, proceedWithPurchase);
    }

    private boolean agreesToNonPromotion(Product product, int nonPromotionQuantity) {
        Boolean acceptNonPromotion = inputView.getConfirmPurchaseWithoutPromotion(product.getName(), nonPromotionQuantity);
        return acceptNonPromotion;
    }


}
