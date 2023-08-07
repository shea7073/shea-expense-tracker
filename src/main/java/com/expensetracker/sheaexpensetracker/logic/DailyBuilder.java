package com.expensetracker.sheaexpensetracker.logic;

import com.expensetracker.sheaexpensetracker.entity.Transaction;
import com.expensetracker.sheaexpensetracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class DailyBuilder implements GlanceBuilder {

    private Glance glance;

    private TransactionRepository transactionRepository;

    @Autowired
    public DailyBuilder(TransactionRepository repository) {
        this.transactionRepository = repository;
        this.glance = new Glance();
        this.glance.description = "Today";
    }

    @Override
    public void getTotal(String user){
        LocalDate startDate = this.glance.startDate;
        double total = 0;
        List<Transaction> todaysTrans = transactionRepository.getTodaysTransactions(user, startDate);
        for (int i = 0; i < todaysTrans.size(); i++) {
            if (Objects.equals(todaysTrans.get(i).getType(), "expense")){
                total -= todaysTrans.get(i).getAmount();
            }
            else {
                total += todaysTrans.get(i).getAmount();
            }
        }
        this.glance.total = total;
    }

    public void getStartDate(){
        this.glance.startDate = java.time.LocalDate.now();
    };

    public void getEndDate(){
        this.glance.endDate = java.time.LocalDate.now();
    };

    //ArrayList<HashMap<String, String>> getGraphData();

    public void getDailySpending(String user){
        LocalDate date = LocalDate.now();
        double total = 0;
        List<Transaction> todaysTrans = transactionRepository.getTodaysTransactions(user, date);
        for (int i = 0; i < todaysTrans.size(); i++) {
            if (Objects.equals(todaysTrans.get(i).getType(), "expense")){
                total += todaysTrans.get(i).getAmount();
            }
        }
        this.glance.dailySpending = total;
    };

    public void getHighestCategory(String user) {
        LocalDate startDate = this.glance.startDate;
        LocalDate endDate = this.glance.endDate;
        String highestCategory = "";
        List<Transaction> todaysTrans = transactionRepository.getTodaysTransactions(user, startDate);
        HashMap<String, Integer> countMap = new HashMap<>();
        if (todaysTrans.isEmpty()) {
            this.glance.highestCategory = "None";
            return;
        }
        for (Transaction trans : todaysTrans) {
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
        this.glance.highestCategory =  highestCategory;
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



