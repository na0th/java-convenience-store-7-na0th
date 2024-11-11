package store.model;

import java.time.LocalDate;

public class Promotion {
    private static final int ONE_FREE_ITEM = 1;
    private static final int NO_FREE_ITEM = 0;

    private String promotionName;
    private String buy;
    private String get;
    private LocalDate startDate;
    private LocalDate endDate;

    public Promotion(String promotionName, String buy, String get, LocalDate startDate, LocalDate endDate) {
        this.promotionName = promotionName;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public int calculatePromotionGroupSize() {
        return Integer.parseInt(buy) + Integer.parseInt(get);
    }

    public boolean isWithinValidDate(LocalDate currentDate) {
        return (currentDate.isEqual(startDate) || currentDate.isAfter(startDate)) &&
                (currentDate.isEqual(endDate) || currentDate.isBefore(endDate));
    }
    public int calculateFreeItemCount(int quantity) {
        int buyCount = Integer.parseInt(buy);
        int getCount = Integer.parseInt(get);

        int group = buyCount + getCount;
        int remainder = quantity % group;

        if (buyCount <= 0 || getCount <= 0) {
            throw new IllegalArgumentException();
        }
        if ((remainder % buyCount == 0) && remainder > 0) {
            return ONE_FREE_ITEM;
        }

        return NO_FREE_ITEM;
    }
}
