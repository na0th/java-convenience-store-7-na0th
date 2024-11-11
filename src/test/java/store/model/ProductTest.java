package store.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product product;
    private Product product2;

    @BeforeEach
    void setUp() {
        product = Product.create("testProduct", 1000, 10, 10, "testPromotionName");
        product2 = Product.create("testProduct", 1000, 10, 5, "testPromotionName2");
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

    @ParameterizedTest
    @DisplayName("getNonPromotionQuantity 메서드 테스트")
    @CsvSource({ // 프로모션 재고는 10개
            "10, 3, 1",   // 첫 번째 케이스: 구매 수량이 10, promotionGroupSize가 3일 때
            "15, 5, 5",   // 두 번째 케이스: 구매 수량이 15, promotionGroupSize가 5일 때
            "8, 2, 0",    // 세 번째 케이스: 구매 수량이 8, promotionGroupSize가 2일 때
            "9, 4, 1",    // 네 번째 케이스: 구매 수량이 9, promotionGroupSize가 4일 때
            "11, 3, 2"    // 다섯 번째 케이스: 구매 수량이 11, promotionGroupSize가 3일 때
        })
    void getNonPromotionQuantity_메서드_테스트1(int purchaseQuantity, int promotionGroupSize, int expectedNonPromotionQuantity) {
        //given
        //when
        //then
        int nonPromotionQuantity = product.getNonPromotionQuantity(purchaseQuantity, promotionGroupSize);
        assertEquals(expectedNonPromotionQuantity, nonPromotionQuantity);
    }
    @ParameterizedTest
    @DisplayName("getNonPromotionQuantity 메서드 테스트")
    @CsvSource({ // 프로모션 재고는 5개
            "15, 3, 12",
            "18, 5, 13",
            "20, 2, 16",
            "23, 4, 19",
            "24, 3, 21"
        })
    void getNonPromotionQuantity_메서드_테스트2(int purchaseQuantity, int promotionGroupSize, int expectedNonPromotionQuantity) {
        //given
        //when
        //then
        int nonPromotionQuantity = product2.getNonPromotionQuantity(purchaseQuantity, promotionGroupSize);
        assertEquals(expectedNonPromotionQuantity, nonPromotionQuantity);
    }
}