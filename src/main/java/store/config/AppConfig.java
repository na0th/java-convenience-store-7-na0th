package store.config;

import store.controller.OrderController;
import store.model.*;
import store.model.processStrategy.StockProcessorFactory;
import store.repository.WareHouse;
import store.service.DiscountCalculator;
import store.service.OrderService;
import store.view.InputView;
import store.view.OutputView;

import static store.Application.productFileHandle;
import static store.Application.promotionFileHandle;

public class AppConfig {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    private final WareHouse wareHouse = new WareHouse(productFileHandle());
    private final Promotions promotions = new Promotions(promotionFileHandle());
    private final DiscountCalculator discountCalculator = new DiscountCalculator();
    private final StockProcessorFactory stockProcessorFactory = new StockProcessorFactory();

    private final OrderService orderService = new OrderService(wareHouse, promotions, stockProcessorFactory, inputView);
    private final OrderController orderController = new OrderController(orderService, inputView, outputView,discountCalculator);


    public OrderController orderController() {
        return orderController;
    }

    public OrderService orderService() {
        return orderService;
    }
}
