package store.model;

import java.time.LocalDate;
import java.util.List;

public class PromotionChecker {
    private List<Promotion> promotions;
    public static final LocalDate FIXED_DATE = LocalDate.of(2025, 11, 11); // 원하는 날짜로 고정

    public PromotionChecker(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    //이게 굳이 필요한가..
//    public boolean isPromotionAvailable(String promotionName) {
//        for (Promotion promotion : promotions) {
//            if (promotion.getPromotionName().equals(promotionName)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public boolean isPromotionValid(String promotionName) {
        for (Promotion promotion : promotions) {
            if (promotion.getPromotionName().equals(promotionName) && promotion.isWithinValidDate(LocalDate.now())) {
                return true;
            }
        }
        return false;
    }


}
