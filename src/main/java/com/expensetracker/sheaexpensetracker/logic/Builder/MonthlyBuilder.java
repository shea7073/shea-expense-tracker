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

import java.time.LocalDate;
import java.util.*;

@Component
public class MonthlyBuilder implements GlanceBuilder {

    private Glance glance;

    private TransactionRepository transactionRepository;

    @Autowired
    public MonthlyBuilder(TransactionRepository repository) {
        this.transactionRepository = repository;
        this.glance = new Glance();
        this.glance.description = "This Month";
    }

    @Override
    public void getTotal(String user){
        LocalDate startDate = this.glance.startDate;
        LocalDate endDate = this.glance.endDate;
        double total = 0;
        List<Transaction> monthsTrans = transactionRepository.getTransactionsByDates(user, startDate, endDate);
        for (int i = 0; i < monthsTrans.size(); i++) {
            if (Objects.equals(monthsTrans.get(i).getType(), "expense")){
                total -= monthsTrans.get(i).getAmount();
            }
            else {
                total += monthsTrans.get(i).getAmount();
            }
        }
        this.glance.total =  total;
    }

    public void getStartDate(){
        String stringDate = LocalDate.now().toString();
        String[] splitStringDate = stringDate.split("-", 3);
        splitStringDate[2] = "01";
        stringDate = String.join("-", splitStringDate);
        this.glance.startDate = LocalDate.parse(stringDate);
    };

    public void getEndDate(){
        Calendar cal = Calendar.getInstance();
        int endOfMonth = cal.getActualMaximum(Calendar.DATE);
        String stringDate = LocalDate.now().toString();
        String[] splitStringDate = stringDate.split("-", 3);
        splitStringDate[2] = Integer.toString(endOfMonth);
        stringDate = String.join("-", splitStringDate);
        this.glance.endDate = LocalDate.parse(stringDate);
    };

    //ArrayList<HashMap<String, String>> getGraphData();

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
        Calendar cal = Calendar.getInstance();
        int monthLength = cal.getActualMaximum(Calendar.DATE);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        this.glance.dailySpending = total / dayOfMonth;
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
