package com.safaimitra.safaimitra.controller;

import com.safaimitra.safaimitra.model.Booking;
import com.safaimitra.safaimitra.model.User;
import com.safaimitra.safaimitra.repository.BookingRepository;
import com.safaimitra.safaimitra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    // ============================================================
    // SCHEDULE PICKUP
    // ============================================================

    @GetMapping("/schedule")
    public String showScheduleForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "user/schedule-pickup";
    }

    @PostMapping("/schedule")
    public String submitSchedule(Booking booking, Authentication auth, Model model) {
        try {
            String email = auth.getName();
            User user = userRepository.findByEmail(email).orElse(null);

            if (user == null) {
                model.addAttribute("error", "❌ User not found! Please login again.");
                return "user/schedule-pickup";
            }

            booking.setUser(user);
            booking.setStatus("PENDING");
            bookingRepository.save(booking);
            model.addAttribute("success", "✅ Pickup scheduled successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "❌ Something went wrong! Please try again.");
        }
        return "user/schedule-pickup";
    }

    // ============================================================
    // REQUEST CLEANER
    // ============================================================

    @GetMapping("/cleaner/request")
    public String showCleanerForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "user/request-cleaner";
    }

    @PostMapping("/cleaner/request")
    public String submitCleanerRequest(Booking booking, Authentication auth, Model model) {
        try {
            System.out.println("🔵=== CLEANER REQUEST START ===");
            System.out.println("🔵 Booking: " + booking);

            String email = auth.getName();
            System.out.println("🔵 User email: " + email);

            User user = userRepository.findByEmail(email).orElse(null);

            if (user == null) {
                System.out.println("🔴 User not found!");
                model.addAttribute("error", "❌ User not found! Please login again.");
                return "user/request-cleaner";
            }

            booking.setUser(user);
            booking.setStatus("PENDING");
            booking.setServiceType("CLEANER_SERVICE");

            System.out.println("🔵 Saving: " + booking);
            bookingRepository.save(booking);
            System.out.println("🟢 CLEANER REQUEST SAVED SUCCESSFULLY!");

            model.addAttribute("success", "✅ Cleaner requested successfully! We will contact you shortly.");
        } catch (Exception e) {
            System.err.println("🔴 ERROR: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "❌ Something went wrong! Please try again.");
        }
        return "user/request-cleaner";
    }

    // ============================================================
    // EMERGENCY
    // ============================================================

    @GetMapping("/emergency")
    public String showEmergencyForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "user/emergency";
    }

    @PostMapping("/emergency")
    public String submitEmergency(@RequestParam String emergencyType,
                                  @RequestParam String description,
                                  @RequestParam String address,
                                  @RequestParam String phone,
                                  Authentication auth,
                                  Model model) {
        try {
            String email = auth.getName();
            User user = userRepository.findByEmail(email).orElse(null);

            if (user == null) {
                model.addAttribute("error", "❌ User not found! Please login again.");
                return "user/emergency";
            }

            Booking emergency = new Booking();
            emergency.setUser(user);
            emergency.setServiceType("EMERGENCY");
            emergency.setEmergencyType(emergencyType);
            emergency.setDescription(description);
            emergency.setAddress(address);
            emergency.setPhone(phone);
            emergency.setStatus("PENDING");
            emergency.setPriority("URGENT");
            bookingRepository.save(emergency);

            model.addAttribute("success", "🚨 Emergency request submitted! Our team will respond immediately.");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "❌ Something went wrong! Please try again.");
        }
        return "user/emergency";
    }

    // ============================================================
    // SPECIAL DISPOSAL
    // ============================================================

    @GetMapping("/disposal/special")
    public String showSpecialDisposalForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "user/special-disposal";
    }

    @PostMapping("/disposal/special")
    public String submitSpecialDisposal(@RequestParam String wasteType,
                                        @RequestParam String description,
                                        @RequestParam String quantity,
                                        @RequestParam String address,
                                        @RequestParam String preferredDate,
                                        @RequestParam String preferredTime,
                                        Authentication auth,
                                        Model model) {
        try {
            String email = auth.getName();
            User user = userRepository.findByEmail(email).orElse(null);

            if (user == null) {
                model.addAttribute("error", "❌ User not found! Please login again.");
                return "user/special-disposal";
            }

            Booking disposal = new Booking();
            disposal.setUser(user);
            disposal.setServiceType("SPECIAL_DISPOSAL");
            disposal.setWasteType(wasteType);
            disposal.setDescription(description);
            disposal.setQuantity(quantity);
            disposal.setAddress(address);
            disposal.setPreferredDate(LocalDate.parse(preferredDate));
            disposal.setPreferredTime(LocalTime.parse(preferredTime));
            disposal.setStatus("PENDING");
            bookingRepository.save(disposal);

            model.addAttribute("success", "✅ Special disposal request submitted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "❌ Something went wrong! Please try again.");
        }
        return "user/special-disposal";
    }

    // ============================================================
    // SUBSCRIPTION
    // ============================================================

    @GetMapping("/subscription")
    public String showSubscriptionPage() {
        return "user/subscription";
    }

    // ============================================================
    // MY SCHEDULE - Category Wise with Stats
    // ============================================================

    @GetMapping("/my-schedule")
    public String mySchedule(Authentication auth, Model model) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {
            List<Booking> allBookings = bookingRepository.findByUser(user);

            // ✅ Categories Mein Split Karo
            List<Booking> scheduleBookings = allBookings.stream()
                    .filter(b -> b.getServiceType() != null &&
                            (b.getServiceType().equals("HOME_CLEANING") ||
                                    b.getServiceType().equals("DRAIN_CLEANING") ||
                                    b.getServiceType().equals("EVENT_CLEANING") ||
                                    b.getServiceType().equals("OFFICE_CLEANING")))
                    .collect(Collectors.toList());

            List<Booking> cleanerBookings = allBookings.stream()
                    .filter(b -> b.getServiceType() != null &&
                            b.getServiceType().equals("CLEANER_SERVICE"))
                    .collect(Collectors.toList());

            List<Booking> emergencyBookings = allBookings.stream()
                    .filter(b -> b.getServiceType() != null &&
                            b.getServiceType().equals("EMERGENCY"))
                    .collect(Collectors.toList());

            List<Booking> disposalBookings = allBookings.stream()
                    .filter(b -> b.getServiceType() != null &&
                            b.getServiceType().equals("SPECIAL_DISPOSAL"))
                    .collect(Collectors.toList());

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

            // ✅ Status Wise Lists
            List<Booking> pendingBookings = allBookings.stream()
                    .filter(b -> "PENDING".equals(b.getStatus()))
                    .collect(Collectors.toList());

            List<Booking> completedBookings = allBookings.stream()
                    .filter(b -> "COMPLETED".equals(b.getStatus()))
                    .collect(Collectors.toList());

            List<Booking> cancelledBookings = allBookings.stream()
                    .filter(b -> "CANCELLED".equals(b.getStatus()))
                    .collect(Collectors.toList());

            // ✅ Stats
            long totalBookings = allBookings.size();
            long pendingCount = pendingBookings.size();
            long completedCount = completedBookings.size();
            long cancelledCount = cancelledBookings.size();

            model.addAttribute("bookings", allBookings);
            model.addAttribute("scheduleBookings", scheduleBookings);
            model.addAttribute("cleanerBookings", cleanerBookings);
            model.addAttribute("emergencyBookings", emergencyBookings);
            model.addAttribute("disposalBookings", disposalBookings);
            model.addAttribute("otherBookings", otherBookings);

            // ✅ Status Wise Lists
            model.addAttribute("pendingBookings", pendingBookings);
            model.addAttribute("completedBookings", completedBookings);
            model.addAttribute("cancelledBookings", cancelledBookings);

            // ✅ Stats
            model.addAttribute("totalBookings", totalBookings);
            model.addAttribute("pendingCount", pendingCount);
            model.addAttribute("completedCount", completedCount);
            model.addAttribute("cancelledCount", cancelledCount);
        } else {
            model.addAttribute("bookings", List.of());
            model.addAttribute("scheduleBookings", List.of());
            model.addAttribute("cleanerBookings", List.of());
            model.addAttribute("emergencyBookings", List.of());
            model.addAttribute("disposalBookings", List.of());
            model.addAttribute("otherBookings", List.of());
            model.addAttribute("pendingBookings", List.of());
            model.addAttribute("completedBookings", List.of());
            model.addAttribute("cancelledBookings", List.of());
            model.addAttribute("totalBookings", 0);
            model.addAttribute("pendingCount", 0);
            model.addAttribute("completedCount", 0);
            model.addAttribute("cancelledCount", 0);
        }

        return "user/my-schedule";
    }

    // ============================================================
    // CANCEL BOOKING
    // ============================================================

    @GetMapping("/booking/cancel/{id}")
    public String cancelBooking(@PathVariable Long id, Authentication auth, RedirectAttributes redirectAttributes) {
        try {
            String email = auth.getName();
            User user = userRepository.findByEmail(email).orElse(null);

            Booking booking = bookingRepository.findById(id).orElse(null);

            if (booking == null) {
                redirectAttributes.addFlashAttribute("error", "❌ Booking not found!");
                return "redirect:/my-schedule";
            }

            if (user == null || !booking.getUser().getId().equals(user.getId())) {
                redirectAttributes.addFlashAttribute("error", "❌ You are not authorized!");
                return "redirect:/my-schedule";
            }

            if (!booking.getStatus().equals("PENDING")) {
                redirectAttributes.addFlashAttribute("error", "❌ Only pending bookings can be cancelled!");
                return "redirect:/my-schedule";
            }

            booking.setStatus("CANCELLED");
            bookingRepository.save(booking);

            redirectAttributes.addFlashAttribute("success", "✅ Booking cancelled successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "❌ Something went wrong!");
        }
        return "redirect:/my-schedule";
    }

    // ============================================================
    // DELETE BOOKING - Permanently Delete
    // ============================================================

    @GetMapping("/booking/delete/{id}")
    public String deleteBooking(@PathVariable Long id, Authentication auth, RedirectAttributes redirectAttributes) {
        try {
            String email = auth.getName();
            User user = userRepository.findByEmail(email).orElse(null);

            Booking booking = bookingRepository.findById(id).orElse(null);

            if (booking == null) {
                redirectAttributes.addFlashAttribute("error", "❌ Booking not found!");
                return "redirect:/my-schedule";
            }

            if (user == null || !booking.getUser().getId().equals(user.getId())) {
                redirectAttributes.addFlashAttribute("error", "❌ You are not authorized to delete this booking!");
                return "redirect:/my-schedule";
            }

            // ✅ Only COMPLETED or CANCELLED bookings can be deleted
            if (!booking.getStatus().equals("COMPLETED") && !booking.getStatus().equals("CANCELLED")) {
                redirectAttributes.addFlashAttribute("error", "❌ Only completed or cancelled bookings can be deleted!");
                return "redirect:/my-schedule";
            }

            bookingRepository.delete(booking);
            redirectAttributes.addFlashAttribute("success", "✅ Booking deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "❌ Something went wrong!");
        }
        return "redirect:/my-schedule";
    }

    // ============================================================
    // EDIT BOOKING - Show Form
    // ============================================================

    @GetMapping("/booking/edit/{id}")
    public String editBooking(@PathVariable Long id, Authentication auth, Model model) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        Booking booking = bookingRepository.findById(id).orElse(null);

        if (booking == null || user == null || !booking.getUser().getId().equals(user.getId())) {
            return "redirect:/my-schedule";
        }

        model.addAttribute("booking", booking);
        return "user/edit-booking";
    }

    // ============================================================
    // EDIT BOOKING - Update
    // ============================================================

    @PostMapping("/booking/edit/{id}")
    public String updateBooking(@PathVariable Long id,
                                @RequestParam String address,
                                @RequestParam(required = false) String phone,
                                @RequestParam(required = false) String instructions,
                                @RequestParam(required = false) String emergencyType,
                                @RequestParam(required = false) String description,
                                @RequestParam(required = false) String wasteType,
                                @RequestParam(required = false) String quantity,
                                @RequestParam(required = false) String gender,
                                Authentication auth,
                                RedirectAttributes redirectAttributes) {
        try {
            String email = auth.getName();
            User user = userRepository.findByEmail(email).orElse(null);

            Booking booking = bookingRepository.findById(id).orElse(null);

            if (booking == null || user == null || !booking.getUser().getId().equals(user.getId())) {
                redirectAttributes.addFlashAttribute("error", "❌ You are not authorized!");
                return "redirect:/my-schedule";
            }

            if (!booking.getStatus().equals("PENDING")) {
                redirectAttributes.addFlashAttribute("error", "❌ Only pending bookings can be edited!");
                return "redirect:/my-schedule";
            }

            booking.setAddress(address);
            if (phone != null && !phone.isEmpty()) {
                booking.setPhone(phone);
            }
            booking.setInstructions(instructions);

            if (emergencyType != null) {
                booking.setEmergencyType(emergencyType);
            }
            if (description != null) {
                booking.setDescription(description);
            }

            if (wasteType != null) {
                booking.setWasteType(wasteType);
            }
            if (quantity != null) {
                booking.setQuantity(quantity);
            }

            if (gender != null) {
                booking.setGender(gender);
            }

            bookingRepository.save(booking);

            redirectAttributes.addFlashAttribute("success", "✅ Booking updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "❌ Something went wrong!");
        }
        return "redirect:/my-schedule";
    }
}