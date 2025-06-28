package OM.Expense.Trackers.controller;

import OM.Expense.Trackers.model.Expense;
import OM.Expense.Trackers.model.User;
import OM.Expense.Trackers.repository.UserRepository;
import OM.Expense.Trackers.service.ExpenseService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserRepository userRepository;

    public ExpenseController(ExpenseService expenseService, UserRepository userRepository) {
        this.expenseService = expenseService;
        this.userRepository = userRepository;
    }

    // ✅ View all expenses
    @GetMapping
    public String viewExpenses(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Expense> expenses = expenseService.getExpensesByUser(user);
        model.addAttribute("expenses", expenses);
        return "expenses"; // Your expenses.html view
    }

    // ✅ Show form for adding/editing
    @GetMapping("/form")
    public String showExpenseForm(@RequestParam(required = false) Long id,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {
        Expense expense;
        if (id != null) {
            expense = expenseService.getExpenseById(id);
        } else {
            expense = new Expense();
            User user = userRepository.findByUsername(userDetails.getUsername());
            expense.setUser(user);
            expense.setDate(LocalDate.now());
        }

        model.addAttribute("expense", expense);
        model.addAttribute("categories", Expense.Category.values()); // ✅ for dropdown options
        return "expense-form"; // Your expense-form.html
    }

    // ✅ Save expense (POST)
    @PostMapping("/add")
    public String saveExpense(@ModelAttribute Expense expense,
                              @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        expense.setUser(user);
        if (expense.getDate() == null) {
            expense.setDate(LocalDate.now());
        }
        expenseService.saveExpense(expense);
        return "redirect:/expenses";
    }

    // ✅ Delete expense
    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return "redirect:/expenses";
    }
}
