package OM.Expense.Trackers.model;

import jakarta.persistence.*;
import jakarta.websocket.OnMessage;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ✅ Use lowercase 'id' to follow Java naming conventions

    private String title;


    private double amount;

    @Enumerated(EnumType.STRING)
    private Category category;


    public enum Category {
        FOOD,
        TRANSPORT,
        UTILITIES,
        ENTERTAINMENT,
        HEALTH,
        OTHER
    }

    private LocalDate date;



    // ✅ Define the relationship to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Creates the foreign key in DB
    private User user;

    // ======== Getters and Setters ========

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    // ✅ This was missing: actually assign the user!
    public void setUser(User user) {
        this.user = user;
    }
}
