package com.safaimitra.safaimitra.controller;

import com.safaimitra.safaimitra.model.Booking;
import com.safaimitra.safaimitra.model.User;
import com.safaimitra.safaimitra.repository.BookingRepository;
import com.safaimitra.safaimitra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    // ============================================================
    // DASHBOARD - Category Wise
    // ============================================================

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Stats
        long totalUsers = userRepository.count();
        long totalBookings = bookingRepository.count();
        long pendingBookings = bookingRepository.findByStatus("PENDING").size();

        // All Bookings
        List<Booking> allBookings = bookingRepository.findAll();

        // Recent Bookings (Last 10)
        List<Booking> recentBookings = allBookings.stream()
                .limit(10)
                .collect(Collectors.toList());

        // ✅ Category Wise Bookings
        List<Booking> homeCleaningBookings = bookingRepository.findByServiceType("HOME_CLEANING");
        List<Booking> drainCleaningBookings = bookingRepository.findByServiceType("DRAIN_CLEANING");
        List<Booking> eventCleaningBookings = bookingRepository.findByServiceType("EVENT_CLEANING");
        List<Booking> officeCleaningBookings = bookingRepository.findByServiceType("OFFICE_CLEANING");
        List<Booking> cleanerServiceBookings = bookingRepository.findByServiceType("CLEANER_SERVICE");
        List<Booking> emergencyBookings = bookingRepository.findByServiceType("EMERGENCY");
        List<Booking> disposalBookings = bookingRepository.findByServiceType("SPECIAL_DISPOSAL");

        // Other bookings (if any)
        List<Booking> otherBookings = allBookings.stream()
                .filter(b -> b.getServiceType() != null &&
                        !b.getServiceType().equals("HOME_CLEANING") &&
                        !b.getServiceType().equals("DRAIN_CLEANING") &&
                        !b.getServiceType().equals("EVENT_CLEANING") &&
                        !b.getServiceType().equals("OFFICE_CLEANING") &&
                        !b.getServiceType().equals("CLEANER_SERVICE") &&
                        !b.getServiceType().equals("EMERGENCY") &&
                        !b.getServiceType().equals("SPECIAL_DISPOSAL"))
                .collect(Collectors.toList());

        // Add to model
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalBookings", totalBookings);
        model.addAttribute("pendingBookings", pendingBookings);
        model.addAttribute("activeTrucks", 3);
        model.addAttribute("allBookings", allBookings);
        model.addAttribute("recentBookings", recentBookings);

        // Category wise
        model.addAttribute("homeCleaningBookings", homeCleaningBookings);
        model.addAttribute("drainCleaningBookings", drainCleaningBookings);
        model.addAttribute("eventCleaningBookings", eventCleaningBookings);
        model.addAttribute("officeCleaningBookings", officeCleaningBookings);
        model.addAttribute("cleanerServiceBookings", cleanerServiceBookings);
        model.addAttribute("emergencyBookings", emergencyBookings);
        model.addAttribute("disposalBookings", disposalBookings);
        model.addAttribute("otherBookings", otherBookings);

        return "admin/dashboard";
    }

    // ============================================================
    // USERS PAGE
    // ============================================================

    @GetMapping("/users")
    public String users(Model model) {
        List<User> users = userRepository.findAll();

        long totalUsers = users.size();
        long totalAdmins = users.stream().filter(u -> "ADMIN".equals(u.getRole())).count();
        long totalNormalUsers = users.stream().filter(u -> "USER".equals(u.getRole())).count();
        long activeUsers = users.stream().filter(User::isActive).count();

        model.addAttribute("users", users);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalAdmins", totalAdmins);
        model.addAttribute("totalNormalUsers", totalNormalUsers);
        model.addAttribute("activeUsers", activeUsers);

        return "admin/users";
    }

    // ============================================================
    // GET USER BY ID (AJAX - For View Profile)
    // ============================================================

    @GetMapping("/user/{id}")
    @ResponseBody
    public Map<String, Object> getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        Map<String, Object> response = new HashMap<>();

        if (user != null) {
            response.put("id", user.getId());
            response.put("fullName", user.getFullName());
            response.put("email", user.getEmail());
            response.put("phone", user.getPhone());
            response.put("role", user.getRole());
            response.put("active", user.isActive());
            response.put("address", user.getAddress());
            response.put("createdAt", user.getCreatedAt() != null ? user.getCreatedAt().toString() : "N/A");

            // Get user's bookings
            List<Booking> userBookings = bookingRepository.findByUser(user);
            List<Map<String, Object>> bookingsList = userBookings.stream().map(b -> {
                Map<String, Object> bMap = new HashMap<>();
                bMap.put("serviceType", b.getServiceType());
                bMap.put("preferredDate", b.getPreferredDate() != null ? b.getPreferredDate().toString() : "N/A");
                bMap.put("status", b.getStatus());
                bMap.put("address", b.getAddress());
                return bMap;
            }).collect(Collectors.toList());

            response.put("bookings", bookingsList);
        }

        return response;
    }

    // ============================================================
    // BOOKINGS PAGE
    // ============================================================

    @GetMapping("/bookings")
    public String bookings(Model model) {
        List<Booking> allBookings = bookingRepository.findAll();

        List<Booking> pendingBookings = allBookings.stream()
                .filter(b -> "PENDING".equals(b.getStatus()))
                .collect(Collectors.toList());

        List<Booking> completedBookings = allBookings.stream()
                .filter(b -> "COMPLETED".equals(b.getStatus()))
                .collect(Collectors.toList());

        List<Booking> cancelledBookings = allBookings.stream()
                .filter(b -> "CANCELLED".equals(b.getStatus()))
                .collect(Collectors.toList());

        model.addAttribute("bookings", allBookings);
        model.addAttribute("pendingBookings", pendingBookings);
        model.addAttribute("completedBookings", completedBookings);
        model.addAttribute("cancelledBookings", cancelledBookings);
        model.addAttribute("totalBookings", allBookings.size());
        model.addAttribute("pendingCount", pendingBookings.size());
        model.addAttribute("completedCount", completedBookings.size());
        model.addAttribute("cancelledCount", cancelledBookings.size());

        return "admin/bookings";
    }

    // ============================================================
    // APPROVE BOOKING
    // ============================================================

    @GetMapping("/approve/{id}")
    public String approveBooking(@PathVariable Long id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setStatus("COMPLETED");
            bookingRepository.save(booking);
        }
        return "redirect:/admin/bookings";
    }

    // ============================================================
    // REJECT BOOKING
    // ============================================================

    @GetMapping("/reject/{id}")
    public String rejectBooking(@PathVariable Long id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setStatus("CANCELLED");
            bookingRepository.save(booking);
        }
        return "redirect:/admin/bookings";
    }

    // ============================================================
    // DELETE USER
    // ============================================================

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }
}