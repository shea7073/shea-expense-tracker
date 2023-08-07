package com.expensetracker.sheaexpensetracker.logic;

public class NegativeState extends AccountState {

    String mainColor = "red";

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

    String secondaryColor = "rgba(252, 0, 0 ,.36)";


}
