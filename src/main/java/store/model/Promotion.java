package store.model;

import java.time.LocalDate;

public class Promotion {
    //TODO 여기서 프로모션에 있는지 확인할 수 있지 않을까?

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
    public boolean hasPromotionName(String name) {
        return promotionName.equals(name);
    }
}
