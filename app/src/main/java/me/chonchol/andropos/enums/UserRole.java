package me.chonchol.andropos.enums;

public enum UserRole {

    SUPER_ADMIN(1),
    SALES_MAN(2);


    private final int value;

    UserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
