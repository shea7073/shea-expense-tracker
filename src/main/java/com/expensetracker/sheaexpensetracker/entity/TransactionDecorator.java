package com.expensetracker.sheaexpensetracker.entity;

import com.expensetracker.sheaexpensetracker.entity.Transaction;

import java.time.LocalDate;

public abstract class TransactionDecorator extends Transaction {

    Transaction transaction;

}

