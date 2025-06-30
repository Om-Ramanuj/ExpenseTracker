package OM.Expense.Trackers.controller;

import OM.Expense.Trackers.model.Expense;
import OM.Expense.Trackers.model.User;
import OM.Expense.Trackers.repository.UserRepository;
import OM.Expense.Trackers.service.ExpenseService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    private final ExpenseService expenseService;
    private final UserRepository userRepository;

    public DashboardController(ExpenseService expenseService, UserRepository userRepository) {
        this.expenseService = expenseService;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Expense> expenses = expenseService.getExpensesByUser(Optional.ofNullable(user));

        // Pie chart data by category
        Map<String, Double> categorySummary = expenses.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getCategory().toString(),
                        Collectors.summingDouble(Expense::getAmount)
                ));

        // Monthly chart data
        Map<Month, Double> monthlyMap = expenses.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getDate().getMonth(),
                        TreeMap::new,
                        Collectors.summingDouble(Expense::getAmount)
                ));

        Locale locale = Locale.ENGLISH;
        List<String> months = monthlyMap.keySet().stream()
                .map(m -> m.getDisplayName(TextStyle.SHORT, locale))
                .collect(Collectors.toList());
        List<Double> amounts = new ArrayList<>(monthlyMap.values());

        model.addAttribute("categorySummary", categorySummary);
        model.addAttribute("months", months);
        model.addAttribute("amounts", amounts);
        model.addAttribute("username", user.getUsername());

        return "dashboard";
    }
}
