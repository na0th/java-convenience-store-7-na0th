package store.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PromotionTest {

    private Promotion promotion;
    private Promotion promotion2;
    private Promotion promotion3;

    @BeforeEach
    void setUp() {
        promotion = new Promotion(
                "testPromotion",
                "1",
                "1",
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 12, 31));

        promotion2 = new Promotion(
                "testPromotion2",
                "2",
                "1",
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 12, 31));
        promotion3 = new Promotion(
                "testPromotion3",
                "3",
                "1",
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 12, 31));
    }

    @Test
    @DisplayName("isWithinValidDate 메서드 테스트")
    void 프로모션_기간_내에_있으면_True를_반환한다() {
        //given
        //when & then
        assertEquals(Boolean.TRUE, promotion.isWithinValidDate(LocalDate.of(2024, 11, 10)));
    }
    @Test
    @DisplayName("isWithinValidDate 메서드 테스트")
    void 프로모션_기간_내에_없으면_False를_반환한다() {
        //given
        //when & then
        assertEquals(Boolean.FALSE, promotion.isWithinValidDate(LocalDate.of(2025, 11, 10)));
    }

    @Test
    @DisplayName("calculateFreeItem 메서드 테스트")
    void 빠뜨린_프로모션_재고를_제대로_리턴하면_성공한다() {
        //given
        int quantity = 7;
        //when

        //then
        Assertions.assertThat(1).isEqualTo(promotion.calculateFreeItem(quantity));
        Assertions.assertThat(0).isEqualTo(promotion2.calculateFreeItem(quantity));
        Assertions.assertThat(1).isEqualTo(promotion3.calculateFreeItem(quantity));
    }
    @Test
    @DisplayName("calculateFreeItem 메서드 테스트")
    void 빠뜨린_프로모션_재고를_제대로_리턴하면_성공한다2() {
        //given
        int quantity = 9;
        //when

        //then
        Assertions.assertThat(1).isEqualTo(promotion.calculateFreeItem(quantity));
        Assertions.assertThat(0).isEqualTo(promotion2.calculateFreeItem(quantity));
        Assertions.assertThat(0).isEqualTo(promotion3.calculateFreeItem(quantity));
    }
    @Test
    @DisplayName("calculateFreeItem 메서드 테스트")
    void 빠뜨린_프로모션_재고를_제대로_리턴하면_성공한다3() {
        //given
        int quantity = 11;
        //when

        //then
        Assertions.assertThat(1).isEqualTo(promotion.calculateFreeItem(quantity));
        Assertions.assertThat(1).isEqualTo(promotion2.calculateFreeItem(quantity));
        Assertions.assertThat(1).isEqualTo(promotion3.calculateFreeItem(quantity));
    }
}