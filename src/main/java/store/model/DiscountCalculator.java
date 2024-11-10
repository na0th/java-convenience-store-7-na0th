package store.model;

import store.dto.response.ReceiptDto;
import store.dto.response.ReceiptSingleDto;

import java.util.List;

import static store.model.DiscountConstants.FIXED_POLICY;

public class DiscountCalculator {
    private static final int NO_DISCOUNT = 0;

    public void generateReceipt(boolean isMembershipUser, ReceiptDto receipt) {
        int totalPromotionAmount = getTotalPromotionAmount(receipt);
        int totalRegularAmount = getTotalRegularAmount(receipt);
        int discountAmount = calculateDiscountAmount(isMembershipUser, totalRegularAmount);
        int finalAmount = totalPromotionAmount + (totalRegularAmount - discountAmount);

        StringBuilder receiptBuilder = new StringBuilder();
        receiptBuilder.append("==== 영수증 ====\n");

        for (ReceiptSingleDto item : receipt.getReceiptSingleDtos()) {
            receiptBuilder.append("상품명: ").append(item.getProductName()).append("\n");
            receiptBuilder.append(" - 가격: ").append(item.getProductPrice()).append("원\n");
            receiptBuilder.append(" - 프로모션 차감 수량: ").append(item.getPromotionStockDeducted()).append("\n");
            receiptBuilder.append(" - 일반 차감 수량: ").append(item.getRegularStockDeducted()).append("\n");
        }

        receiptBuilder.append("\n총 프로모션 금액: ").append(totalPromotionAmount).append("원\n");
        receiptBuilder.append("총 일반 금액: ").append(totalRegularAmount).append("원\n");
        receiptBuilder.append("할인 금액: ").append(discountAmount).append("원\n");
        receiptBuilder.append("최종 결제 금액: ").append(finalAmount).append("원\n");
        receiptBuilder.append("=================\n");

        System.out.println(receiptBuilder.toString());

    }

    private int getTotalPromotionAmount(ReceiptDto receipt) {
        return receipt.getReceiptSingleDtos().stream()
                .mapToInt(singleDto -> singleDto.getPromotionStockDeducted() * singleDto.getProductPrice())
                .sum();
    }

    private int getTotalRegularAmount(ReceiptDto receipt) {
        return receipt.getReceiptSingleDtos().stream()
                .mapToInt(singleDto -> singleDto.getRegularStockDeducted() * singleDto.getProductPrice())
                .sum();
    }

    private int calculateDiscountAmount(Boolean isMembershipUser, int regularAmount) {
        if (isMembershipUser) {
            return FIXED_POLICY.adjustDiscountWithMaxLimit(regularAmount);
        }
        return NO_DISCOUNT;
    }
}
