package com.expensetracker.sheaexpensetracker.logic.Factory;

import com.expensetracker.sheaexpensetracker.entity.ReminderEvent;

public class ReminderFactory extends TrackerEventFactory {

    @Override
    public ReminderEvent createEvent() {
        return new ReminderEvent();
    }
}
