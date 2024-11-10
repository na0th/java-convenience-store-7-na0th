package store.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.create("testProduct", 1000, 10, 0, "testPromotionName");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 10})
    @DisplayName("decrementRegularStock_메서드_테스트 : 정상적인 케이스")
    void 재고_미만의_주문은_성공한다(int quantity) {
        //given
        //when
        product.decrementRegularStock(quantity);
        //then
        assertEquals(product.getRegularStock(), 10 - quantity);
    }

    @ParameterizedTest
    @ValueSource(ints = {30, 40, 50, 60, 100})
    @DisplayName("예외 케이스 (재고보다 많은 주문)")
    void 재고_초과_주문은_예외가_발생한다(int quantity) {
        //given
        //when & then
        assertThatThrownBy(() -> product.decrementRegularStock(quantity))
                .isInstanceOf(IllegalArgumentException.class);
    }

}