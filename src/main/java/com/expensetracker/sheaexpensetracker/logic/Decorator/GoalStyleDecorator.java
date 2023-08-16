package com.expensetracker.sheaexpensetracker.logic.Decorator;

import com.expensetracker.sheaexpensetracker.entity.GoalEvent;
import com.expensetracker.sheaexpensetracker.logic.Decorator.EventStyleDecorator;
import com.expensetracker.sheaexpensetracker.repository.TransactionRepository;
import java.util.Objects;

public class GoalStyleDecorator extends EventStyleDecorator {


        TransactionRepository transactionRepository;


        public GoalEvent goalEvent;

        String status;

        String color;

        String description;

        public GoalStyleDecorator(GoalEvent goalEvent, TransactionRepository transactionRepository) {
            this.goalEvent = goalEvent;
            this.transactionRepository = transactionRepository;
            this.description = goalEvent.notifyUser(transactionRepository);
            this.setStatus();

        }

        public void setStatus() {
            if (Objects.equals(goalEvent.getType(), "spending") && goalEvent.progress > Double.parseDouble(goalEvent.getAmount()) ||
                    Objects.equals(goalEvent.getType(), "saving") && goalEvent.progress < Double.parseDouble(goalEvent.getAmount())) {
                this.status = "NOT ON TRACK";
                this.color = "red";
            }
            if (Objects.equals(goalEvent.getType(), "spending") && goalEvent.progress == Double.parseDouble(goalEvent.getAmount()) ||
                    Objects.equals(goalEvent.getType(), "saving") && goalEvent.progress == Double.parseDouble(goalEvent.getAmount())) {
                this.status = "ON TRACK";
                this.color = "blue";
            }

            if (Objects.equals(goalEvent.getType(), "spending") && goalEvent.progress < Double.parseDouble(goalEvent.getAmount()) ||
                    Objects.equals(goalEvent.getType(), "saving") && goalEvent.progress > Double.parseDouble(goalEvent.getAmount())) {
                this.status = "AHEAD OF THE GAME";
                this.color = "green";
            }
        }

    public String getStatus() {
        return status;
    }

    @Override
    public long getId() {
        return goalEvent.getId();
    }

    public String getColor(){
            return this.color;
    }

    public String notifyUser() {
            return this.status + ": " + this.description;
        }



}
