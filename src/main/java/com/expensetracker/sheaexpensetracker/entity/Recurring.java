package com.expensetracker.sheaexpensetracker.entity;

import com.expensetracker.sheaexpensetracker.entity.Transaction;
import com.expensetracker.sheaexpensetracker.entity.TransactionDecorator;

import java.time.LocalDate;

public class Recurring extends TransactionDecorator {

    Transaction transaction;

    public Recurring(Transaction transaction, String frequency, String category, LocalDate nextCharge) {
        this.transaction = transaction;
        this.transaction.setRecurring(true);
        this.transaction.setFrequency(frequency);
        this.transaction.setCategory(category);
        this.setNextCharge(nextCharge);
    }

}
