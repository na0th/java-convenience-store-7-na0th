package store.dto.request;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderRequest {
    List<ProductOrderDto> products;

    public OrderRequest(List<ProductOrderDto> products) {
        this.products = products;
    }

    public List<ProductOrderDto> getProducts() {
        return products;
    }

    public static OrderRequest from(Map<String, Integer> orderItems) {
        List<ProductOrderDto> products = orderItems.entrySet().stream()
                .map(entry -> ProductOrderDto.create(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return new OrderRequest(products);
    }

}
