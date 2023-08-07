package com.expensetracker.sheaexpensetracker.logic;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {

    public static String formatCurrency(double number) {
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        String formattedTotal = dollarFormat.format(number);
        return formattedTotal;
    }


}
