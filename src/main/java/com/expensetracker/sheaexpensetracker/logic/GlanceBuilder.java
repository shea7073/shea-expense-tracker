package com.expensetracker.sheaexpensetracker.logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

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
