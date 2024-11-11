package store.model;

public enum DiscountConstants {
    FIXED_POLICY(30, 8000);

    private final int discountRate;
    private final int maxDiscountAmount;

    DiscountConstants(int discountRate, int maxDiscountAmount) {
        this.discountRate = discountRate;
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public int adjustDiscountWithMaxLimit(int currentAmount) {
        int discountedAmount = calculateDiscountAmount(currentAmount);
        if (discountedAmount > FIXED_POLICY.maxDiscountAmount) {
            return maxDiscountAmount;
        }
        return discountedAmount;
    }

    public int calculateDiscountAmount(int currentAmount) {
        return (int) (currentAmount * (discountRate / 100.0));
    }
}
