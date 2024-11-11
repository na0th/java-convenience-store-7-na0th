package store.service;

import store.dto.response.ReceiptDto;
import store.dto.response.ReceiptSingleDto;

import java.text.NumberFormat;
import java.util.Locale;

import static store.model.DiscountConstants.FIXED_POLICY;

public class DiscountCalculator {
    private static final int NO_DISCOUNT = 0;

    public String generateReceipt(boolean isMembershipUser, ReceiptDto receipt) {

        int totalPurchaseAmount = getTotalPromotionAmount(receipt) + getTotalRegularAmount(receipt);
        int totalPromotionAmount = getTotalPromotionAmount(receipt);
        int totalRegularAmount = getTotalRegularAmount(receipt);
        int memberShipDiscountAmount = calculateDiscountAmount(isMembershipUser, totalRegularAmount);
        int finalAmount = totalRegularAmount + totalPromotionAmount - memberShipDiscountAmount;

        StringBuilder receiptBuilder = new StringBuilder();
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA);

        int promotionDiscountAmount = 0;
        int totalPromotionItems = 0;
        int totalItems = 0 ;
        receiptBuilder.append("==============W 편의점 ==============\n");
        receiptBuilder.append("상품명\t\t\t\t수량\t\t금액\n");

        for (ReceiptSingleDto item : receipt.getReceiptSingleDtos()) {
            int totalQuantity = item.getRegularStockDeducted() + item.getPromotionStockDeducted();
            int sumDeductedPrice = totalQuantity * item.getProductPrice();
            totalItems += totalQuantity;

            receiptBuilder.append(item.getProductName())
                    .append("\t\t")
                    .append(totalQuantity)
                    .append("\t")
                    .append(numberFormat.format(sumDeductedPrice))
                    .append("\n");

            // 프로모션 할인 계산
            int promotionGroupSize = item.getPromotionGroupSize();
            int promotionItems = item.getPromotionStockDeducted() / promotionGroupSize;
            int promotionDiscount = promotionItems * item.getProductPrice();
            promotionDiscountAmount += promotionDiscount;
            totalPromotionItems += promotionItems;
        }
        receiptBuilder.append("=============증   정===============\n");
        for (ReceiptSingleDto item : receipt.getReceiptSingleDtos()) {
            if (item.getPromotionStockDeducted() > 0) {
                receiptBuilder.append(item.getProductName())
                        .append("\t\t")
                        .append(item.getPromotionStockDeducted()/item.getPromotionGroupSize())
                        .append("\n");
            }
        }
        // 최종 금액 계산 및 출력
        finalAmount -= promotionDiscountAmount;
        receiptBuilder.append("====================================\n");
        receiptBuilder.append("총구매액\t\t").append(totalItems).append("\t\t").append(numberFormat.format(totalRegularAmount + totalPromotionAmount)).append("\n");
        receiptBuilder.append("행사할인\t\t-").append(numberFormat.format(promotionDiscountAmount)).append("\n");
        receiptBuilder.append("멤버십할인\t\t-").append(numberFormat.format(memberShipDiscountAmount)).append("\n");
        receiptBuilder.append("내실돈\t\t").append(numberFormat.format(finalAmount)).append("\n");
        receiptBuilder.append("====================================\n");

        String receiptMessage = receiptBuilder.toString();
        return receiptMessage;
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
