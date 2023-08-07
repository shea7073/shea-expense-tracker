package com.expensetracker.sheaexpensetracker.entity;

import com.expensetracker.sheaexpensetracker.logic.CurrencyFormatter;
import jakarta.persistence.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String description;

    @Column
    private double amount;

    @Column
    private String type;

    @Column
    private String user;

    @Column
    private LocalDateTime datetime;

    @Column
    private LocalDate date;

    @Column
    private LocalTime time;

    @Column
    private boolean recurring;

    @Column
    private String frequency;

    @Column
    private String category;

    @Column
    private LocalDate nextCharge;


    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }


    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String format() {
        return CurrencyFormatter.formatCurrency(this.getAmount());
    }


    public LocalDate getNextCharge() {
        return nextCharge;
    }

    public void setNextCharge(LocalDate nextCharge) {
        this.nextCharge = nextCharge;
    }
}
