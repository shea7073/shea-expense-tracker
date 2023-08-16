package com.expensetracker.sheaexpensetracker.logic.Decorator;

import com.expensetracker.sheaexpensetracker.entity.TrackerEvent;
import com.expensetracker.sheaexpensetracker.repository.TransactionRepository;

public abstract class EventStyleDecorator extends TrackerEvent {

    TrackerEvent trackerEvent;

    String status;

    String color;

    public void setStatus() {}

    public String notifyUser(){
        return "";
    }

    public String getColor(){
        return "";
    }
}
