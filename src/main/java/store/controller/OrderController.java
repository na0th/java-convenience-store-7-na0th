package store.controller;

import store.dto.request.OrderRequest;
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
        while (true) {
            outputView.printProducts(orderService.getAllProducts());
            String products;
            Map<String, Integer> parsedProducts;
            OrderRequest orderRequest;
            while (true) {
                try {
                    products = inputView.getProducts();
                    parsedProducts = Parser.parse(products);
                    orderRequest = OrderRequest.from(parsedProducts);
                    orderService.validOrderStock(orderRequest);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }


            boolean applyDiscount;
            while (true) {
                try {
                    applyDiscount = inputView.getMembershipDiscount();
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            while (true) {
                try{
                    processOrder(applyDiscount, orderRequest);
                    break;
                }catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            Boolean wantAnotherProducts;
            while (true) {
                try {
                    wantAnotherProducts = inputView.getWantAnotherProducts();
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (!wantAnotherProducts) {
                break;
            }

        }
    }

    private void processOrder(Boolean applyDiscount, OrderRequest orderRequest) {
        String message = orderService.generatePromotionAvailabilityMessage(orderRequest);
        if (!message.isEmpty()) {
            outputView.showMessage(message);
            boolean acceptExtra = inputView.getConfirmAddFreeProducts();
            orderService.finalPurchase(orderRequest, applyDiscount, acceptExtra);
        } else {
            orderService.finalPurchase(orderRequest, applyDiscount, false);
        }
    }

}
