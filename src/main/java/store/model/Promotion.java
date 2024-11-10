package store.model;

import java.time.LocalDate;

public class Promotion {
    //TODO 여기서 프로모션에 있는지 확인할 수 있지 않을까?
    private static final int ONE_FREE_ITEM = 1;
    private static final int NO_FREE_ITEM = 0;


    private String promotionName;
    //(buy,get) , (startDate,endDate)는 묶는 걸 고려
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

    public boolean isWithinValidDate(LocalDate currentDate) {
        return (currentDate.isEqual(startDate) || currentDate.isAfter(startDate)) &&
                (currentDate.isEqual(endDate) || currentDate.isBefore(endDate));
    }
    //테스트 필수
    public int calculateFreeItem(int quantity) {
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

    public boolean hasPromotionName(String name) {
        return promotionName.equals(name);
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "promotionName='" + promotionName + '\'' +
                ", buy=" + buy +
                ", get=" + get +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
