package com.safaimitra.safaimitra.controller;

import com.safaimitra.safaimitra.model.User;
import com.safaimitra.safaimitra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ============================================================
    // HOME PAGE - Role based redirect
    // ============================================================

    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        System.out.println("🔵 User email: " + email);
        System.out.println("🔵 User role: " + (user != null ? user.getRole() : "null"));

        if (user != null && "ADMIN".equals(user.getRole())) {
            System.out.println("🟢 Redirecting to Admin Dashboard");
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("user", user);
        return "user/index";
    }

    // ============================================================
    // PROFILE PAGE - GET (Show Form)
    // ============================================================

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        model.addAttribute("user", user);
        return "user/profile";
    }

    // ============================================================
    // PROFILE PAGE - POST (Update Profile with Photo)
    // ============================================================

    @PostMapping("/profile")
    public String updateProfile(@RequestParam String fullName,
                                @RequestParam(required = false) String phone,
                                @RequestParam(required = false) String address,
                                @RequestParam(required = false) String gender,
                                @RequestParam(required = false) String newPassword,
                                @RequestParam(required = false) String confirmPassword,
                                @RequestParam(required = false) MultipartFile profilePic,
                                Authentication auth,
                                RedirectAttributes redirectAttributes) {
        try {
            String email = auth.getName();
            User user = userRepository.findByEmail(email).orElse(null);

            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "❌ User not found!");
                return "redirect:/profile";
            }

            // ✅ Update Basic Info
            user.setFullName(fullName);
            if (phone != null && !phone.isEmpty()) {
                user.setPhone(phone);
            }
            if (address != null && !address.isEmpty()) {
                user.setAddress(address);
            }
            if (gender != null && !gender.isEmpty()) {
                user.setGender(gender);
            }

            // ✅ Update Password (if provided)
            if (newPassword != null && !newPassword.isEmpty()) {
                if (newPassword.equals(confirmPassword)) {
                    user.setPassword(passwordEncoder.encode(newPassword));
                } else {
                    redirectAttributes.addFlashAttribute("error", "❌ Passwords do not match!");
                    return "redirect:/profile";
                }
            }

            // ✅ Update Profile Picture - FIXED
            if (profilePic != null && !profilePic.isEmpty()) {
                try {
                    // 1. Upload folder create karo (absolute path)
                    String uploadDir = "src/main/resources/static/uploads/profile/";
                    File directory = new File(uploadDir);
                    if (!directory.exists()) {
                        boolean created = directory.mkdirs();
                        System.out.println("📁 Directory created: " + created);
                    }

                    // 2. File name generate karo
                    String originalFilename = profilePic.getOriginalFilename();
                    String fileExtension = "";
                    if (originalFilename != null && originalFilename.contains(".")) {
                        fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    }
                    String fileName = UUID.randomUUID().toString() + fileExtension;

                    // 3. File save karo
                    Path filePath = Paths.get(uploadDir + fileName);
                    Files.write(filePath, profilePic.getBytes());

                    // 4. Database mein path save karo
                    user.setProfilePic("/uploads/profile/" + fileName);
                    System.out.println("🖼️ Photo saved: " + fileName);

                } catch (IOException e) {
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("error", "❌ Failed to upload profile picture!");
                    return "redirect:/profile";
                }
            }

            userRepository.save(user);
            System.out.println("✅ User saved with photo: " + user.getProfilePic());

            redirectAttributes.addFlashAttribute("success", "✅ Profile updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "❌ Something went wrong!");
        }
        return "redirect:/profile";
    }
}