package store.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PromotionTest {

    private Promotion promotion;

    @BeforeEach
    void setUp() {
        promotion = new Promotion(
                "testPromotion",
                "1",
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

}