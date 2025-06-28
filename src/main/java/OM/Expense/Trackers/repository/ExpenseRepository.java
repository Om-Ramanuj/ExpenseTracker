package OM.Expense.Trackers.repository;

import OM.Expense.Trackers.model.Expense;
import OM.Expense.Trackers.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    List<Expense> findByUser(User user);
    List<Expense> findByUserId(Long userId);
}
