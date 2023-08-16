package com.expensetracker.sheaexpensetracker.logic.Decorator;

import com.expensetracker.sheaexpensetracker.entity.ReminderEvent;
import com.expensetracker.sheaexpensetracker.logic.Decorator.EventStyleDecorator;


import java.time.LocalDate;
import java.util.Objects;

public class ReminderStyleDecorator extends EventStyleDecorator {

    public ReminderEvent reminderEvent;

    String status;

    String color;

    public ReminderStyleDecorator(ReminderEvent reminderEvent) {
        this.reminderEvent = reminderEvent;
        this.setStatus();
    }

    public void setStatus() {
        if (reminderEvent.getDueDate().isBefore(LocalDate.now())) {
            this.status = "OVERDUE";
            this.color = "red";

        }
        else if (Objects.equals(reminderEvent.getDueDate(), LocalDate.now()) && reminderEvent.getActive()) {
            this.status = "DUE";
            this.color = "blue";
        }
        else if (reminderEvent.getDueDate().isAfter(LocalDate.now()) ) {
            this.status = "NOT DUE";
            this.color = "green";
        }
    }

    public String notifyUser() {
        String description = reminderEvent.notifyUser();
        return this.status + ": " + description;

    }

    public String getColor(){
        return this.color;
    }

    @Override
    public long getId() {
        return reminderEvent.getId();
    }
}
