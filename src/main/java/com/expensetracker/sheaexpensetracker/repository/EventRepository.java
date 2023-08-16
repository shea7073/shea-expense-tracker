package com.expensetracker.sheaexpensetracker.repository;

import com.expensetracker.sheaexpensetracker.entity.GoalEvent;
import com.expensetracker.sheaexpensetracker.entity.ReminderEvent;
import com.expensetracker.sheaexpensetracker.entity.TrackerEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<TrackerEvent, Long> {

    @Query(value = "SELECT * FROM tracker_event WHERE dtype = 'ReminderEvent' AND user = :user AND active = 1 ORDER BY due_date", nativeQuery = true)
    public List<ReminderEvent> getReminders(String user);

    @Query(value = "SELECT * FROM tracker_event WHERE dtype = 'GoalEvent' AND user = :user AND active = 1 ORDER BY due_date", nativeQuery = true)
    public List<GoalEvent> getGoals(String user);

    @Query(value = "SELECT * FROM tracker_event WHERE user = :user AND active = 0 ORDER BY due_date", nativeQuery = true)
    public List<TrackerEvent> getInactiveEvents(String user);

}
