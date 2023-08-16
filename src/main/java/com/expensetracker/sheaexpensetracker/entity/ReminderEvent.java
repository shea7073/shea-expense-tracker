package com.expensetracker.sheaexpensetracker.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Objects;

@Entity
public class ReminderEvent extends TrackerEvent {

    @Column
    String title;

    @Column
    String description;

    @Column
    LocalDate dueDate;
    @Column
    Boolean active;

    @Column
    String user;

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

    public String getStatus(){
        if (this.getActive()) {
            return "pending";
        }
        else {
            return "closed";
        }
    }

    @Override
    public String toString(){
        return this.getDescription();
    }

    public String notifyUser() {
        if (this.dueDate.isBefore(LocalDate.now())) {
            long dayspast = ChronoUnit.DAYS.between(this.getDueDate(), LocalDate.now());
            return "Your reminder " + this.getTitle() + " is overdue by " + dayspast + " days! (DUE " + this.dueDate + ")";
        }
        if (Objects.equals(this.dueDate, LocalDate.now()) && this.getActive()) {
            this.active = false;
            return "Your reminder " + this.title +  " is due today! (DUE " + this.dueDate + ")";
        }
        else {
            long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), this.getDueDate());
            return "Your reminder " + this.getTitle() + " is due in " +  + daysRemaining + " days. (DUE " + this.dueDate + ")";
        }

    }

}
