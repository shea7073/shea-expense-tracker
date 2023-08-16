package com.expensetracker.sheaexpensetracker.logic.Builder;

import com.expensetracker.sheaexpensetracker.entity.Transaction;
import com.expensetracker.sheaexpensetracker.logic.Builder.Glance;
import com.expensetracker.sheaexpensetracker.logic.Builder.GlanceBuilder;
import com.expensetracker.sheaexpensetracker.logic.State.EvenState;
import com.expensetracker.sheaexpensetracker.logic.State.NegativeState;
import com.expensetracker.sheaexpensetracker.logic.State.PositiveState;
import com.expensetracker.sheaexpensetracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Component
public class WeeklyBuilder implements GlanceBuilder {

    private Glance glance;

    private TransactionRepository transactionRepository;

    @Autowired
    public WeeklyBuilder(TransactionRepository repository) {
        this.transactionRepository = repository;
        this.glance = new Glance();
        this.glance.description = "This Week";
    }

    @Override
    public void getTotal(String user){
        LocalDate startDate = this.glance.startDate;
        LocalDate endDate = this.glance.endDate;
        double total = 0;
        List<Transaction> weeksTrans = transactionRepository.getTransactionsByDates(user, startDate, endDate);
        for (int i = 0; i < weeksTrans.size(); i++) {
            if (Objects.equals(weeksTrans.get(i).getType(), "expense")){
                total -= weeksTrans.get(i).getAmount();
            }
            else {
                total += weeksTrans.get(i).getAmount();
            }
        }
        this.glance.total = total;
    }

    public void getStartDate(){
        LocalDate startDate;
        if (!Objects.equals(LocalDate.now().getDayOfWeek().toString(), "SUNDAY")) {
            startDate = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        }
        else {
            startDate = java.time.LocalDate.now();
        }

        this.glance.startDate = startDate;
    }

    public void getEndDate(){
        this.glance.endDate = this.glance.startDate.plusDays(6);
    };


    public void getDailySpending(String user){
        LocalDate startDate = this.glance.startDate;
        LocalDate endDate = this.glance.endDate;
        double total = 0;
        List<Transaction> todaysTrans = transactionRepository.getTransactionsByDates(user, startDate, endDate);
        for (int i = 0; i < todaysTrans.size(); i++) {
            if (Objects.equals(todaysTrans.get(i).getType(), "expense")){
                total += todaysTrans.get(i).getAmount();
            }
        }
        this.glance.dailySpending = total / (LocalDate.now().getDayOfWeek().getValue() + 1);
    };

    public void getHighestCategory(String user) {
        LocalDate startDate = this.glance.startDate;
        LocalDate endDate = this.glance.endDate;
        String highestCategory = "";
        List<Transaction> weeksTrans = transactionRepository.getTransactionsByDates(user, startDate, endDate);
        if (weeksTrans.isEmpty()) {
            this.glance.highestCategory = "None";
            return;
        }
        HashMap<String, Integer> countMap = new HashMap<String, Integer>();
        for (Transaction trans : weeksTrans) {
            String cat = trans.getCategory();
            if (countMap.get(cat) != null){
                countMap.put(cat, countMap.get(cat) + 1);
            }
            else {
                countMap.put(cat, 1);
            }
        }
        double highestTotal = Collections.max(countMap.values());
        for (Map.Entry<String, Integer> entry : countMap.entrySet()){
            if (entry.getValue() == highestTotal) {
                highestCategory = entry.getKey();
                break;
            }
        }
        this.glance.highestCategory = highestCategory;
    }

    public void getState() {
        if (this.glance.total < 0) {
            this.glance.state = new NegativeState();
        } else if (this.glance.total > 0) {
            this.glance.state = new PositiveState();
        }
        else {
            this.glance.state = new EvenState();
        }
    }

    public Glance getGlance() {
        return this.glance;
    }

}
