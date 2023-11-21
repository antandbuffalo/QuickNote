package com.antandbuffalo.quicknote.utilities;

public enum Theme {
    DEFAULT("default"),
    LIGHT("light"),
    DARK("dark");

    private String value;

    Theme(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
