package com.expensetracker.sheaexpensetracker.logic.Builder;

import com.expensetracker.sheaexpensetracker.logic.State.AccountState;

import java.time.LocalDate;

public class Glance {

    double total;

    LocalDate startDate;

    LocalDate endDate;

    double dailySpending;

    String highestCategory;

    public String description;

    AccountState state;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccountState getState() {
        return state;
    }

    public void setState(AccountState state) {
        this.state = state;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getDailySpending() {
        return dailySpending;
    }

    public void setDailySpending(double dailySpending) {
        this.dailySpending = dailySpending;
    }

    public String getHighestCategory() {
        return highestCategory;
    }

    public void setHighestCategory(String highestCategory) {
        this.highestCategory = highestCategory;
    }

    public LocalDate getCurrentDate(){
        return LocalDate.now();
    }

}
