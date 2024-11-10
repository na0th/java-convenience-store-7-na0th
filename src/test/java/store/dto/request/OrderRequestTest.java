package store.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderRequestTest {

    @Test
    @DisplayName("from 메서드 테스트 : Map<String, Integer> -> OrderRequest로 반환")
    void parse된_orderItems_로_OrderRequest를_반환하면_성공한다() {
        //given
        Map<String, Integer> orderItems = new HashMap<>();
        orderItems.put("사이다", 2);
        orderItems.put("감자칩", 1);
        // when
        OrderRequest orderRequest = OrderRequest.from(orderItems);
        // then
        assertNotNull(orderRequest);
        assertEquals(2, orderRequest.getProducts().size());
        assertEquals("사이다", orderRequest.getProducts().get(0).getProductName());
        assertEquals(2, orderRequest.getProducts().get(0).getProductQuantity());
        assertEquals("감자칩", orderRequest.getProducts().get(1).getProductName());
        assertEquals(1, orderRequest.getProducts().get(1).getProductQuantity());
    }
}
