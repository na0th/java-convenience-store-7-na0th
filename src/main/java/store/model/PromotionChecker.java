package store.model;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDate;
import java.util.List;

public class PromotionChecker {
    private List<Promotion> promotions;
    public static final LocalDate FIXED_DATE = LocalDate.of(2025, 11, 11); // 원하는 날짜로 고정

    public PromotionChecker(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public int calculateFreeItems(String promotionName, int quantity) {
        Promotion foundPromotion = findByPromotionName(promotionName);
        int freeItem = foundPromotion.calculateFreeItem(quantity);
        return freeItem;
    }
    public Promotion findByPromotionName(String promotionName) {
        for (Promotion promotion : promotions) {
            if (promotion.getPromotionName().equals(promotionName)) {
                return promotion;
            }

        }
        throw new IllegalArgumentException("존재하지 않는 promotion: " + promotionName);
    }

    public boolean isPromotionValid(String promotionName) {
        for (Promotion promotion : promotions) {
            if (promotion.getPromotionName().equals(promotionName) && promotion.isWithinValidDate(DateTimes.now().toLocalDate())) {
//            if (promotion.getPromotionName().equals(promotionName) && promotion.isWithinValidDate(FIXED_DATE)) {
                return true;
            }
        }
        return false;
    }

    public void getAllPromotions() {
        for (Promotion promotion : promotions) {
            System.out.println(promotion);
        }
    }


}
