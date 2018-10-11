package me.chonchol.andropos.enums;

public enum RequestCode {

    ADDCATEGORY(101),
    ADDSUBCATEGORY(201);


    private final int value;

    RequestCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
