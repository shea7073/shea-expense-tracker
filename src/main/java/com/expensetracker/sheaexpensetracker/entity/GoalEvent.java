package com.expensetracker.sheaexpensetracker.entity;

import com.expensetracker.sheaexpensetracker.repository.TransactionRepository;
import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;

@Entity
public class GoalEvent extends TrackerEvent {

    @Column
    String title;
    @Column
    String description;
    @Column
    String user;

    @Column
    LocalDate dueDate;

    @Column
    Boolean active;

    @Column
    String type;

    @Column
    String period;

    @Column
    String amount;

    @Column
    LocalDate dismissed;

    public LocalDate getDismissed() {
        return dismissed;
    }

    public void setDismissed(LocalDate dismissed) {
        this.dismissed = dismissed;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    public String getStatus() {
        return "";
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    @Column
    public Double progress;

    public String notifyUser(TransactionRepository transactionRepository) {
        LocalDate startDate = null;
        LocalDate endDate = LocalDate.now();
        if (Objects.equals(this.getPeriod(), "weekly")) {
            if (!Objects.equals(LocalDate.now().getDayOfWeek().toString(), "SUNDAY")) {
                startDate = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
            } else {
                startDate = LocalDate.now();
            }
        }
        if (Objects.equals(this.getPeriod(), "monthly")) {
            String stringDate = LocalDate.now().toString();
            String[] splitStringDate = stringDate.split("-", 3);
            splitStringDate[2] = "01";
            stringDate = String.join("-", splitStringDate);
            startDate = LocalDate.parse(stringDate);
        }
        List<Transaction> trans = transactionRepository.getTransactionsByDates(this.getUser(), startDate, endDate);

        double progress = calculateProgress(trans);
        this.setProgress(progress);

        if (Objects.equals(this.getType(), "spending")) {

            String formattedPeriod = this.getPeriod() == "monthly" ? "month" : "week";
            return "You're spending so far this " + formattedPeriod + " is $" + progress + " and your goal was $" + this.getAmount();

        } else if (Objects.equals(this.getType(), "saving")) {

            String formattedPeriod = this.getPeriod() == "monthly" ? "month" : "week";
            return "You're savings so far this " + formattedPeriod + " is $" + progress + " and your goal was $" + this.getAmount();

        } else {
            throw new IllegalArgumentException();
        }
    }



    public double calculateProgress(List<Transaction> transactions){
        if (Objects.equals(this.getType(), "spending")) {
            double spending = 0;
            for (Transaction transaction : transactions) {
                if (Objects.equals(transaction.getType(), "expense")) {
                    spending += transaction.getAmount();
                }
            }
            return spending;
        } else if (Objects.equals(this.getType(), "saving")) {
            double total = 0;
            for (Transaction transaction : transactions) {
                if (Objects.equals(transaction.getType(), "expense")) {
                    total -= transaction.getAmount();
                }
                else {
                    total += transaction.getAmount();
                }
            }
            return total;
        } else {
            throw new IllegalArgumentException();
        }
    }

}

