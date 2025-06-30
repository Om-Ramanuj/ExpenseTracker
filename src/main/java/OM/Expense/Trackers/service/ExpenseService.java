package OM.Expense.Trackers.service;


import OM.Expense.Trackers.model.Expense;
import OM.Expense.Trackers.model.User;



import java.util.List;
import java.util.Optional;


public interface ExpenseService {

    void saveExpense(Expense expense);

    List<Expense> getExpensesByUser(Optional<User> user);

    void deleteExpense(Long id);

    Expense getExpenseById(Long id);

    List<Expense> getAllExpenses();

    void updateExpense(Expense expense);
}
