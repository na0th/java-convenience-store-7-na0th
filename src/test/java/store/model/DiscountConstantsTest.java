package store.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DiscountConstantsTest {

    @ParameterizedTest
    @CsvSource({
            "1000, 300",
            "5000, 1500",
            "10000, 3000",
            "26000, 7800",
            "26666, 7999",
            "26667, 8000",
            "26700, 8000",
            "27000, 8000",
            "30000, 8000",
            "50000, 8000",
            "100000, 8000"
    })
    @DisplayName("adjustDiscountWithMaxLimit 메서드 테스트")
    void 최대_할인_금액은_8000원_할인율은_30퍼센트이면_성공한다(int currentAmount, int expectedDiscountedAmount) {
        //given
        int actualDiscountedAmount = DiscountConstants.FIXED_POLICY.adjustDiscountWithMaxLimit(currentAmount);
        //when & then
        assertEquals(expectedDiscountedAmount, actualDiscountedAmount);

    }

    @ParameterizedTest
    @CsvSource({
            "1000, 300",
            "5000, 1500",
            "10000, 3000",
            "50000, 15000"
    })
    @DisplayName("calculateDiscountAmount 메서드 테스트")
    void 일반_재고_총_합의_30퍼를_할인한_금액을_리턴하면_성공한다(int currentAmount, int expectedDiscountedAmount) {
        //given
        int actualDiscountedAmount = DiscountConstants.FIXED_POLICY.calculateDiscountAmount(currentAmount);
        //when & then
        assertEquals(expectedDiscountedAmount, actualDiscountedAmount);

    }
}