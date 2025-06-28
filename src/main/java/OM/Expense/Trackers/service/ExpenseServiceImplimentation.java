package OM.Expense.Trackers.service;

import OM.Expense.Trackers.model.Expense;
import OM.Expense.Trackers.model.User;
import OM.Expense.Trackers.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImplimentation implements ExpenseService {

    private final ExpenseRepository expenseRepo;

    @Autowired
    public ExpenseServiceImplimentation(ExpenseRepository expenseRepo) {
        this.expenseRepo = expenseRepo;
    }

    @Override
    public void saveExpense(Expense expense) {
        expenseRepo.save(expense);
    }

    @Override
    public List<Expense> getExpensesByUser(User user) {
        return expenseRepo.findByUser(user); // ✅ This will work if method exists in repository
    }

    @Override
    public void deleteExpense(Long id) {
        expenseRepo.deleteById(id);
    }

    @Override
    public Expense getExpenseById(Long id) {
        return expenseRepo.findById(id).orElse(null);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }

    @Override
    public void updateExpense(Expense expense) {
        expenseRepo.save(expense); // ✅ Works for update too
    }
}