package me.chonchol.andropos.enums;

public enum ReportType {

    STOCK_ALERT_REPORT(1),
    PRODUCT_WISE_REPORT(2),
    WEEKLY_REPORT(3),
    MONTHLY_REPORT(4),
    CUSTOM_REPORT(5);


    private final int value;

    ReportType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
