package com.expensetracker.sheaexpensetracker.logic.Composite;

import com.expensetracker.sheaexpensetracker.entity.Transaction;
import com.expensetracker.sheaexpensetracker.repository.TransactionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MonthlyActivity extends ActivityComponent {

   public List<ActivityComponent> monthly = new ArrayList<>();

    TransactionRepository transactionRepository;

    public MonthlyActivity(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void addActivity() {

    }

    @Override
    public void build(LocalDate startDate, LocalDate endDate) {
        int days = LocalDate.now().getDayOfMonth();
        while (days > 0) {
            if (days / 7 >= 1) {
                WeeklyActivity weeklyActivity = new WeeklyActivity(transactionRepository);
                weeklyActivity.build(startDate, startDate.plusDays(6));
                days -= 7;
                startDate = startDate.plusDays(7);
                monthly.add(weeklyActivity);
            }
            else {
                DailyActivity dailyActivity = new DailyActivity(transactionRepository);
                dailyActivity.build(startDate, startDate);
                monthly.add(dailyActivity);
                startDate = startDate.plusDays(1);
                days -= 1;
            }
        }
    }

    @Override
    public List<Transaction> getData() {
        List<Transaction> trans = new ArrayList<>();
        for (ActivityComponent activityComponent : monthly) {
            trans.addAll(activityComponent.getData());
        }
        return trans;
    }

}
