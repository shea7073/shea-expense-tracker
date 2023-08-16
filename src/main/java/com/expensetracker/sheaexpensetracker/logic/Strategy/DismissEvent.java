package com.expensetracker.sheaexpensetracker.logic.Strategy;

import com.expensetracker.sheaexpensetracker.entity.TrackerEvent;
import com.expensetracker.sheaexpensetracker.repository.EventRepository;

public class DismissEvent extends RemoveEvent {

    @Override
    public void removeEvent(TrackerEvent event, EventRepository repo) {
        event.setActive(false);
        repo.save(event);
    }
}
