package com.safaimitra.safaimitra.controller;

import com.safaimitra.safaimitra.model.User;
import com.safaimitra.safaimitra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ============================================================
    // USER REGISTRATION
    // ============================================================

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "❌ Email already registered!");
            return "auth/register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        model.addAttribute("success", "✅ Registration Successful! Please Login.");
        return "auth/login";
    }

    // ============================================================
    // ADMIN REGISTRATION
    // ============================================================

    @GetMapping("/register-admin")
    public String showAdminRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register-admin";
    }

    @PostMapping("/register-admin")
    public String registerAdmin(@RequestParam String fullName,
                                @RequestParam String email,
                                @RequestParam(required = false) String phone,
                                @RequestParam String password,
                                Model model) {
        // Check if email already exists
        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "❌ Email already registered!");
            return "auth/register-admin";
        }

        User admin = new User();
        admin.setFullName(fullName);
        admin.setEmail(email);
        admin.setPhone(phone);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole("ADMIN");
        admin.setActive(true);
        admin.setCreatedAt(LocalDateTime.now());
        userRepository.save(admin);

        model.addAttribute("success", "✅ Admin created successfully! Please Login.");
        return "auth/login";
    }

    // ============================================================
    // LOGIN (Common for User & Admin)
    // ============================================================

    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login";
    }

    // ============================================================
    // CREATE ADMIN - Quick Method (Optional)
    // ============================================================

    @GetMapping("/create-admin")
    public String createAdmin() {
        if (userRepository.findByEmail("admin@safaimitra.com").isPresent()) {
            return "Admin already exists! <a href='/login'>Login</a>";
        }
        User admin = new User();
        admin.setFullName("Admin");
        admin.setEmail("admin@safaimitra.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole("ADMIN");
        admin.setActive(true);
        admin.setCreatedAt(LocalDateTime.now());
        userRepository.save(admin);
        return "✅ Admin created! <br> Email: admin@safaimitra.com <br> Password: admin123 <br> <a href='/login'>Login</a>";
    }
}