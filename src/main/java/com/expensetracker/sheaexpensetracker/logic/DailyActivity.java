package com.expensetracker.sheaexpensetracker.logic;

import com.expensetracker.sheaexpensetracker.entity.Transaction;
import com.expensetracker.sheaexpensetracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DailyActivity extends ActivityComponent {

    public List<Transaction> dailyTransactions;

    LocalDate date;

    private TransactionRepository transactionRepository;

    @Autowired
    public DailyActivity(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public void addActivity() {
        dailyTransactions = transactionRepository.getTransactionsByDates("sean", this.date, this.date);
    }

    @Override
    public void build(LocalDate startDate, LocalDate endDate){
        setDate(startDate);
        addActivity();
    }

    public List<Transaction> getData() {
        return dailyTransactions;
    }


}
