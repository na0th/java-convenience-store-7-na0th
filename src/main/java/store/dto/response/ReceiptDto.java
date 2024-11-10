package store.dto.response;

import java.util.ArrayList;
import java.util.List;

public class ReceiptDto {
    private List<ReceiptSingleDto> receiptSingleDtos = new ArrayList<ReceiptSingleDto>();

    public List<ReceiptSingleDto> getReceiptSingleDtos() {
        return receiptSingleDtos;
    }

    public void addReceipt(ReceiptSingleDto receiptSingleDto) {
        receiptSingleDtos.add(receiptSingleDto);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== 영수증 ===\n");

        for (ReceiptSingleDto dto : receiptSingleDtos) {
            sb.append("상품명: ").append(dto.getProductName()).append("\n");
            sb.append("일반 재고 차감: ").append(dto.getRegularStockDeducted()).append("개\n");
            sb.append("프로모션 재고 차감: ").append(dto.getPromotionStockDeducted()).append("개\n");
            sb.append("가격: ").append(dto.getProductPrice()).append("원\n");
            sb.append("--------------------\n");
        }

        return sb.toString();
    }
}
