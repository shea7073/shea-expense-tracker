package com.expensetracker.sheaexpensetracker.logic;

public class PositiveState extends AccountState {

    String mainColor = "green";

    String secondaryColor = "rgba(0, 255, 0, 0.36)";

    public String getMainColor() {
        return mainColor;
    }

    public void setMainColor(String mainColor) {
        this.mainColor = mainColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }
}
