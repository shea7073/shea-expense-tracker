package com.expensetracker.sheaexpensetracker.logic.State;

import com.expensetracker.sheaexpensetracker.logic.State.AccountState;

public class EvenState extends AccountState {

    String mainColor = "black";

    String secondaryColor = "#D0D0D0";

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
