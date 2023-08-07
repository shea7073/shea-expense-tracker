package com.expensetracker.sheaexpensetracker.logic;

import com.expensetracker.sheaexpensetracker.entity.Transaction;
import com.expensetracker.sheaexpensetracker.repository.TransactionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeeklyActivity extends ActivityComponent {

    List<ActivityComponent> activityComponents = new ArrayList<>();

    TransactionRepository transactionRepository;

    public WeeklyActivity(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void addActivity() {

    }

    @Override
    public void build(LocalDate startDate, LocalDate endDate){
        DailyActivity dailyActivity;
        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)){
            dailyActivity = new DailyActivity(transactionRepository);
            dailyActivity.build(date, date);
            activityComponents.add(dailyActivity);
        }
    }

    public List<Transaction> getData() {
        List<Transaction> trans = new ArrayList<>();
        for (ActivityComponent activityComponent : activityComponents) {
            trans.addAll(activityComponent.getData());
        }
        return trans;
    }

}
