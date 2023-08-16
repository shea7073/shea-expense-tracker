package com.expensetracker.sheaexpensetracker.logic.Strategy;

import com.expensetracker.sheaexpensetracker.entity.TrackerEvent;
import com.expensetracker.sheaexpensetracker.repository.EventRepository;

public class DeleteEvent extends RemoveEvent {

    public void removeEvent(TrackerEvent event, EventRepository repo){
        long id = event.getId();
        repo.deleteById(id);
    }

}
