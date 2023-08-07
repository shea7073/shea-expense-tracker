package com.expensetracker.sheaexpensetracker.controller;

import com.expensetracker.sheaexpensetracker.entity.Recurring;
import com.expensetracker.sheaexpensetracker.entity.Transaction;
import com.expensetracker.sheaexpensetracker.logic.*;
import com.expensetracker.sheaexpensetracker.repository.TransactionRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("/{period}")
    public String index(Model model, @PathVariable("period") String period) {
        List<Transaction> transactionList = transactionRepository.findAll();
        GlanceBuilder builder = null;
        ActivityComponent activity = null;
        Director director = new Director();
        if (Objects.equals(period, "daily")) {
            builder = new DailyBuilder(transactionRepository);
            activity = new DailyActivity(transactionRepository);
        }
        else if (Objects.equals(period, "weekly")){
            builder = new WeeklyBuilder(transactionRepository);
            activity = new WeeklyActivity(transactionRepository);
        }
        else if (Objects.equals(period, "monthly")) {
            builder = new MonthlyBuilder(transactionRepository);
            activity = new MonthlyActivity(transactionRepository);
        }
        else {
            return "redirect:/";
        }

        Glance glance = director.buildGlance(builder, "sean");
        model.addAttribute("glance", glance);

        String formattedTotal = CurrencyFormatter.formatCurrency(glance.getTotal());
        model.addAttribute("formattedTotal", formattedTotal);

        LocalDate start = DateCalculator.calculateStart(period);
        LocalDate end = DateCalculator.calculateEnd(period);
        activity.build(start, end);
        model.addAttribute("transactions", activity.getData());
        return "index";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/daily";
    }


    @GetMapping("/addTransaction")
    public String addTransaction(Model model){
        model.addAttribute("trans", new Transaction());
        model.addAttribute("type", "");
        return "transactionForm";
    }

    @PostMapping("/addTransaction")
    public String submitTransaction(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        Transaction trans = new Transaction();
        trans.setUser("sean");
        trans.setDate(java.time.LocalDate.now());
        trans.setTime(java.time.LocalTime.now());
        trans.setDatetime(java.time.LocalDateTime.now());
        trans.setType(parameterMap.get("type")[0]);
        trans.setDescription(parameterMap.get("description")[0]);
        trans.setAmount(Double.parseDouble(parameterMap.get("amount")[0]));
        if (Boolean.parseBoolean(parameterMap.get("recurring")[0])) {
            trans.setRecurring(true);
            String frequency = parameterMap.get("frequency")[0];
            String category = parameterMap.get("category")[0];
            LocalDate nextCharge = LocalDate.parse(parameterMap.get("nextCharge")[0]);
            trans = new Recurring(trans, frequency, category, nextCharge);
        }
        transactionRepository.save(trans);
        return "redirect:/";
    }

//    @PostMapping("/addTransaction")
//    public String submitTransaction(@ModelAttribute("trans") Transaction trans){
//        trans.setUser("sean");
//        trans.setDate(java.time.LocalDate.now());
//        trans.setTime(java.time.LocalTime.now());
//        trans.setDatetime(java.time.LocalDateTime.now());
//        transactionRepository.save(trans);
//        return "redirect:/";
//    }


}
