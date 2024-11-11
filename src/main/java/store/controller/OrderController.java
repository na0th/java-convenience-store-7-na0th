package store.controller;

import store.dto.request.OrderRequest;
import store.dto.response.ReceiptDto;
import store.service.DiscountCalculator;
import store.service.OrderService;
import store.util.Parser;
import store.view.InputView;
import store.view.OutputView;

import java.util.Map;

public class OrderController {
    private final OrderService orderService;
    private final InputView inputView;
    private final OutputView outputView;
    private final DiscountCalculator discountCalculator;

    public OrderController(OrderService orderService, InputView inputView, OutputView outputView, DiscountCalculator discountCalculator) {
        this.orderService = orderService;
        this.inputView = inputView;
        this.outputView = outputView;
        this.discountCalculator = discountCalculator;
    }


    public void run() {
        while (true) {
            outputView.printProducts(orderService.getAllProducts());

            OrderRequest order = createRequestOrder();
            ReceiptDto receipt = processingOrder(order);
            displayReceipt(receipt);


            if (!askWhatAnotherProducts()) {
                break;
            }

        }

    }

    private void displayReceipt(ReceiptDto receiptDto) {
        String receipt = applyDiscountAndGenerateReceipt(receiptDto);
        outputView.displayReceipt(receipt);
    }

    private boolean askWhatAnotherProducts() {
        while (true) {
            try {
                return inputView.getWantAnotherProducts();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private OrderRequest createRequestOrder() {
        OrderRequest orderRequest;
        while (true) {
            try {
                String products = inputView.getProducts();
                Map<String, Integer> parsedProducts = Parser.parse(products);
                orderRequest = OrderRequest.from(parsedProducts);
                orderService.validOrderStock(orderRequest);
                return orderRequest;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private ReceiptDto processingOrder(OrderRequest orderRequest) {
        String message = orderService.generatePromotionAvailabilityMessage(orderRequest);
        boolean acceptExtra = false;

        if (!message.isEmpty()) {
            while (true) {
                try {
                    outputView.showMessage(message);
                    acceptExtra = inputView.getConfirmAddFreeProducts();
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return orderService.finalPurchase(orderRequest, acceptExtra);
    }

    private String applyDiscountAndGenerateReceipt(ReceiptDto receiptDto) {
        boolean applyDiscount;
        while (true) {
            try {
                applyDiscount = inputView.getMembershipDiscount();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        return discountCalculator.generateReceipt(applyDiscount, receiptDto);
    }


}
