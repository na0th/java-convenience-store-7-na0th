package store.controller;

import org.junit.jupiter.api.Order;
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
        String products = inputView.getProducts();
        Map<String, Integer> parsedProducts = Parser.parse(products);
        OrderRequest orderRequest = OrderRequest.from(parsedProducts);

        orderService.purchase(orderRequest);
    }
}
