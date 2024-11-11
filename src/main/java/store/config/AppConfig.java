package store.config;

import store.controller.OrderController;
import store.model.*;
import store.service.OrderService;
import store.util.Parser;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;
import java.util.Map;

import static store.Application.productFileHandle;
import static store.Application.promotionFileHandle;

public class AppConfig {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    private final WareHouse wareHouse = new WareHouse(productFileHandle());
    private final PromotionChecker promotionChecker = new PromotionChecker(promotionFileHandle());
    private final DiscountCalculator discountCalculator = new DiscountCalculator();
    private final StockProcessorFactory stockProcessorFactory = new StockProcessorFactory();

    private final OrderService orderService = new OrderService(wareHouse, discountCalculator, promotionChecker, stockProcessorFactory, inputView);
    private final OrderController orderController = new OrderController(orderService, inputView, outputView);


    public OrderController orderController() {
        return orderController;
    }

    public OrderService orderService() {
        return orderService;
    }
}
