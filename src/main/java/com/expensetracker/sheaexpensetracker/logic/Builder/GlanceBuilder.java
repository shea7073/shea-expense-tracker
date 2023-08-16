package com.expensetracker.sheaexpensetracker.logic.Builder;

import com.expensetracker.sheaexpensetracker.logic.Builder.Glance;

public interface GlanceBuilder {

    void getTotal(String user);

    void getStartDate();

    void getEndDate();

    //ArrayList<HashMap<String, String>> getGraphData();

    void getDailySpending(String user);

    void getHighestCategory(String user);

    void getState();

    Glance getGlance();


}
