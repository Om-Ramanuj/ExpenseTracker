package OM.Expense.Trackers.controller;

import OM.Expense.Trackers.model.User;
import OM.Expense.Trackers.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;

    public UserController(UserRepository repo, BCryptPasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    // Registration form
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Handle registration
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return "redirect:/login";
    }

    // Login page
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // Optional: Redirect root to login
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    // 🔴 Removed: duplicate /dashboard method to avoid conflict
}

