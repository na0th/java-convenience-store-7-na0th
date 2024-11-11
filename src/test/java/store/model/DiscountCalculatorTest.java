package store.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.response.ReceiptDto;
import store.dto.response.ReceiptSingleDto;
import store.service.DiscountCalculator;

import java.util.List;

class DiscountCalculatorTest {
    private DiscountCalculator discountCalculator;
    private ReceiptDto receiptDto;

    @BeforeEach
    void setUp() {
        discountCalculator = new DiscountCalculator();

        List<ReceiptSingleDto> receiptSingleDtos = List.of(
                new ReceiptSingleDto("상품 A", 1000, 14, 0,3),
                new ReceiptSingleDto("상품 B", 2000, 1, 0,3),
                new ReceiptSingleDto("상품 C", 1500, 0, 0,3)
        );
        receiptDto = new ReceiptDto();
        receiptDto.addAllReceipts(receiptSingleDtos);
    }

    @Test
    @DisplayName("generateReceipt 메서드 테스트")
    void 영수증이_제대로_만들어지면_성공한다() {
        discountCalculator.generateReceipt(Boolean.TRUE, receiptDto);
    }
}
