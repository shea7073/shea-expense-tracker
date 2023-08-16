package com.expensetracker.sheaexpensetracker.controller;

import com.expensetracker.sheaexpensetracker.entity.GoalEvent;
import com.expensetracker.sheaexpensetracker.entity.ReminderEvent;
import com.expensetracker.sheaexpensetracker.entity.TrackerEvent;
import com.expensetracker.sheaexpensetracker.entity.Transaction;
import com.expensetracker.sheaexpensetracker.logic.*;
import com.expensetracker.sheaexpensetracker.logic.Builder.*;
import com.expensetracker.sheaexpensetracker.logic.Composite.ActivityComponent;
import com.expensetracker.sheaexpensetracker.logic.Composite.DailyActivity;
import com.expensetracker.sheaexpensetracker.logic.Composite.MonthlyActivity;
import com.expensetracker.sheaexpensetracker.logic.Composite.WeeklyActivity;
import com.expensetracker.sheaexpensetracker.logic.Decorator.GoalStyleDecorator;
import com.expensetracker.sheaexpensetracker.logic.Decorator.ReminderStyleDecorator;
import com.expensetracker.sheaexpensetracker.logic.Factory.GoalFactory;
import com.expensetracker.sheaexpensetracker.logic.Factory.ReminderFactory;
import com.expensetracker.sheaexpensetracker.logic.Strategy.DeleteEvent;
import com.expensetracker.sheaexpensetracker.logic.Strategy.DismissEvent;
import com.expensetracker.sheaexpensetracker.logic.Strategy.RemoveEvent;
import com.expensetracker.sheaexpensetracker.repository.EventRepository;
import com.expensetracker.sheaexpensetracker.repository.TransactionRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    EventRepository eventRepository;

    @GetMapping("/{period}")
    public String index(Model model, @PathVariable("period") String period) {
        //List<Transaction> transactionList = transactionRepository.findAll();
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
        trans.setCategory(parameterMap.get("category")[0]);
        if (parameterMap.get("recurring") != null) {
            String frequency = parameterMap.get("frequency")[0];
            LocalDate nextCharge = LocalDate.parse(parameterMap.get("nextCharge")[0]);
            trans.setRecurring(true);
            trans.setFrequency(frequency);
            trans.setNextCharge(nextCharge);
        }

        transactionRepository.save(trans);

        return "redirect:/";
    }

    @GetMapping("/goalsReminders")
    public String goalsAndReminders(Model model){
        GoalFactory factory1 = new GoalFactory();
        GoalEvent goal = factory1.createEvent();
        model.addAttribute("goal", goal);

        ReminderFactory factory2 = new ReminderFactory();
        ReminderEvent reminder = factory2.createEvent();
        model.addAttribute("reminder", reminder);

        List<ReminderStyleDecorator> decoratedReminders = new ArrayList<>();
        List<ReminderEvent> reminders = eventRepository.getReminders("sean");
        if (!reminders.isEmpty()){
            for (int i = 0; i < reminders.size(); i++) {
                decoratedReminders.add(new ReminderStyleDecorator(reminders.get(i)));
            }
        }

        List<GoalStyleDecorator> decoratedGoals = new ArrayList<>();
        List<GoalEvent> goals = eventRepository.getGoals("sean");
        if (!goals.isEmpty()) {
            for (int i = 0; i < goals.size(); i++) {
                decoratedGoals.add(new GoalStyleDecorator(goals.get(i), transactionRepository));
            }
        }

        List<TrackerEvent> inactives = eventRepository.getInactiveEvents("sean");
        model.addAttribute("goals", decoratedGoals);
        model.addAttribute("reminders", decoratedReminders);
        model.addAttribute("inactives", inactives);
        model.addAttribute("repo", transactionRepository);
        return "goalsReminders";
    }

    @PostMapping("submitGoal")
    public String submitGoal(@ModelAttribute("goal") GoalEvent goal) {
        goal.setUser("sean");
        goal.setActive(true);
        eventRepository.save(goal);
        return "redirect:/goalsReminders";
    }

    @PostMapping("submitReminder")
    public String submitReminder(@ModelAttribute("reminder") ReminderEvent reminder){
        reminder.setUser("sean");
        reminder.setActive(true);
        eventRepository.save(reminder);
        return "redirect:/goalsReminders";
    }

    @PostMapping("/deleteEvent/{id}")
    public String deleteEvent(@PathVariable("id") long id){
        eventRepository.deleteById(id);
        return "redirect:/goalsReminders";
    }

    @PostMapping("/removeEvent/{id}")
    public String dismissEvent(HttpServletRequest request, @PathVariable("id") long id){
        Map<String, String[]> parameterMap = request.getParameterMap();
        TrackerEvent trackerEvent = eventRepository.getReferenceById(id);
        RemoveEvent removal = setDismissalContext(parameterMap.get("context")[0]);
        removal.removeEvent(trackerEvent, eventRepository);
        return "redirect:/goalsReminders";
    }

    public RemoveEvent setDismissalContext(String context){
        if (Objects.equals(context, "dismiss")){
            return new DismissEvent();
        }
        else if (Objects.equals(context, "delete")){
            return new DeleteEvent();
        }
        else  {
            throw new IllegalArgumentException();
        }
    }


}
