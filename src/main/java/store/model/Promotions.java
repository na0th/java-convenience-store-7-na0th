package store.model;

import camp.nextstep.edu.missionutils.DateTimes;

import java.util.List;

public class Promotions {
    private List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public int calculateFreeItems(String promotionName, int quantity) {
        Promotion foundPromotion = findByPromotionName(promotionName);
        int freeItem = foundPromotion.calculateFreeItemCount(quantity);
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
                return true;
            }
        }
        return false;
    }


}
