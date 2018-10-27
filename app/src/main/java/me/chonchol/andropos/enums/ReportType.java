package me.chonchol.andropos.enums;

public enum ReportType {

    STOCK_REPORT(1),
    SALE_REPORT(2),
    PROFIT_REPORT(3);


    private final int value;

    ReportType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
