package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String getProducts() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1]");
        return Console.readLine();
    }

    public Boolean getMembershipDiscount() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        if (Console.readLine().equals("Y")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
//        return Console.readLine();
    }

    public Boolean getWantAnotherProducts() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        if (Console.readLine().equals("Y")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean getConfirmPurchaseWithoutPromotion(String productName, int nonPromotionQuantity) {
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N): ", productName, nonPromotionQuantity);
        if (Console.readLine().equals("Y")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean getConfirmAddFreeProducts() {
//        System.out.println("현재 오렌지주스은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        if (Console.readLine().equals("Y")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
