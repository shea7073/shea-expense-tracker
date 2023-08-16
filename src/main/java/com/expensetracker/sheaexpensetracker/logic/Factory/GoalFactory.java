package com.expensetracker.sheaexpensetracker.logic.Factory;

import com.expensetracker.sheaexpensetracker.entity.GoalEvent;

public class GoalFactory extends TrackerEventFactory {

    @Override
    public GoalEvent createEvent() {
        return new GoalEvent();
    }


}
