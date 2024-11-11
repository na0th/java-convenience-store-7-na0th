package store.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PromotionCheckerTest {
    private Promotions promotions;
    List<Promotion> promotions;
    @BeforeEach
    void setUp() {
        Promotion promotion1 = new Promotion(
                "testPromotion1",
                "1",
                "1",
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 12, 31)
        );
        Promotion promotion2 = new Promotion(
                "testPromotion2",
                "2",
                "1",
                LocalDate.of(2025, 11, 1),
                LocalDate.of(2025, 12, 31)
        );

        List<Promotion> promotions = List.of(promotion1, promotion2);
        this.promotions = new Promotions(promotions);
    }

    @Test
    @DisplayName("주문 상품이 프로모션에 해당하고, 프로모션 기간이면 True를 반환")
    void 주문_상품이_프로모션_기간_안에_있을_시_True가_반환되면_성공한다() {
        //given
        //when & then
        // 주의할 점 promotionChecker에 fixedDate랑 LocalDate.now()중 하나 넣어놓음
        assertTrue(promotions.isPromotionValid("testPromotion1"));
    }
    @Test
    @DisplayName("주문 상품이 프로모션에 해당하나, 프로모션 기간에 해당하지 않으면 False 반환")
    void 주문_상품이_프로모션_기간_안에_없을_시_False가_반환되면_성공한다() {
        //given
        //when & then
        assertFalse(promotions.isPromotionValid("testPromotion2"));
    }
    @Test
    @DisplayName("주문 상품이 프로모션 상품에 해당하지 않으면 False 반환")
    void 주문_상품이_프로모션_상품에_해당하지_않으면_False가_반환되면_성공한다() {
        //given
        //when & then
        assertFalse(promotions.isPromotionValid("notPromotionProduct"));
    }

    @Test
    @DisplayName("calculateFreeItems 메서드 테스트")
    void 프로모션의_buy_get에_따라서_free_item이_제대로_계산되면_성공한다() {
        //given
        //when&then
        Assertions.assertThat(0).isEqualTo(promotions.calculateFreeItems("testPromotion1",8));
        Assertions.assertThat(1).isEqualTo(promotions.calculateFreeItems("testPromotion1",7));
        Assertions.assertThat(0).isEqualTo(promotions.calculateFreeItems("testPromotion1",6));
        Assertions.assertThat(0).isEqualTo(promotions.calculateFreeItems("testPromotion2",6));
        Assertions.assertThat(1).isEqualTo(promotions.calculateFreeItems("testPromotion2",5));
    }


}