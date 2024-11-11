package store.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.response.ReceiptSingleDto;
import store.model.stockProcessStrategy.RegularStockStrategy;

import java.util.LinkedHashMap;
import java.util.Map;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class WareHouseTest {

    private WareHouse wareHouse;

    @BeforeEach
    void setUp() {
        Map<String, Product> products = new LinkedHashMap<>();

        Product product1 = Product.create(
                "testProduct1",
                1000,
                10,
                10,
                "testPromotionName1"
        );
        Product product2 = Product.create(
                "testProduct2",
                1000,
                10,
                10,
                "testPromotionName2"
        );

        products.put(product1.getName(), product1);
        products.put(product2.getName(), product2);

        wareHouse = new WareHouse(products);
    }

    @Test
    @DisplayName("findByProductName 메서드 테스트")
    void productName으로_Product를_찾으면_성공한다() {
        // Given
        String productName = "testProduct1";
        // When
        Product result = wareHouse.findByProductName(productName);
        // Then
        assertEquals(productName, result.getName());
    }

    @Test
    @DisplayName("findByProductName 메서드 테스트")
    void productName으로_Product가_없으면_예외_발생() {
        // given
        String productName = "testProduct3";
        // when & then
        assertThatThrownBy(() -> wareHouse.findByProductName(productName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("processStock 메서드 테스트")
    void 재고_전략으로_처리된_결과_DTO를_리턴받으면_성공한다() {
        // given
        Product product = wareHouse.findByProductName("testProduct1");
        int expectedDeductedQuantity = 6;
        int expectedRemainderQuantity = 4;
        RegularStockStrategy regularStockStrategy = new RegularStockStrategy();

        // when
        ReceiptSingleDto result = wareHouse.processStock(product, 6, regularStockStrategy,3);

        // then
        assertNotNull(result);
        assertEquals(expectedRemainderQuantity, product.getRegularStock());
        assertEquals(10,product.getPromotionStock());

        assertEquals("testProduct1", result.getProductName());
        assertEquals(expectedDeductedQuantity, result.getRegularStockDeducted());
        assertEquals(0, result.getPromotionStockDeducted());
    }
}