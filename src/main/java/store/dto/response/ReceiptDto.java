package store.dto.response;

import java.util.ArrayList;
import java.util.List;

public class ReceiptDto {
    private List<ReceiptSingleDto> receiptSingleDtos = new ArrayList<ReceiptSingleDto>();

    public List<ReceiptSingleDto> getReceiptSingleDtos() {
        return receiptSingleDtos;
    }

    public void addSingleReceipt(ReceiptSingleDto receiptSingleDto) {
        receiptSingleDtos.add(receiptSingleDto);
    }
    public void addAllReceipts(List<ReceiptSingleDto> receipts) {
        receiptSingleDtos.addAll(receipts);
    }
}
