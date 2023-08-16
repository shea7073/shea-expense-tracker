package com.expensetracker.sheaexpensetracker.logic.Composite;

import com.expensetracker.sheaexpensetracker.entity.Transaction;

import java.time.LocalDate;
import java.util.List;

public abstract class ActivityComponent {

    public void addActivity() {
        throw new UnsupportedOperationException();
    }


    public abstract void build(LocalDate startDate, LocalDate endDate);

    public List<Transaction> getData() {
        throw new UnsupportedOperationException();
    }
}
