package com.example.demo;

public enum YearLevel {
    FIRST_YEAR(1, "1st Year"),
    SECOND_YEAR(2, "2nd Year"),
    THIRD_YEAR(3, "3rd Year"),
    FOURTH_YEAR(4, "4th Year");

    private final int value;
    private final String display;

    YearLevel(int value, String display) {
        this.value = value;
        this.display = display;
    }

    public int getValue() {
        return value; // ← THIS IS THE NUMBER WE WILL SAVE
    }

    public String getDisplay() {
        return display;
    }

    @Override
    public String toString() {
        return display; // ← What shows in dropdown
    }
}