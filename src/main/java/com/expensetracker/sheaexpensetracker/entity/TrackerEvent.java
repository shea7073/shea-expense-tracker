package com.expensetracker.sheaexpensetracker.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class TrackerEvent {

    @Column
    String title;
    @Column
    String description;

    @Column
    LocalDate dueDate;

    @Column
    Boolean active;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    public void setId(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
