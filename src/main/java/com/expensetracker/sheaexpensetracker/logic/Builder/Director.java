package com.expensetracker.sheaexpensetracker.logic.Builder;

import com.expensetracker.sheaexpensetracker.logic.Builder.Glance;
import com.expensetracker.sheaexpensetracker.logic.Builder.GlanceBuilder;

public class Director {

    GlanceBuilder builder;

    public Glance buildGlance(GlanceBuilder builder, String user){
        builder.getStartDate();
        builder.getEndDate();
        builder.getTotal(user);
        builder.getDailySpending(user);
        builder.getHighestCategory(user);
        builder.getState();
        return builder.getGlance();
    }


}
