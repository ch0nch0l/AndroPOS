package me.chonchol.andropos.enums;

/**
 * Created by mehedi.chonchol on 18-Oct-18.
 */

public enum OrderStatus {
    PENDING(1),
    COMPLETED(2);


    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
