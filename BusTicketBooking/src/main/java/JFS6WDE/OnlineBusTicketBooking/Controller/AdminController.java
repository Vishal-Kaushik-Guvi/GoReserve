package JFS6WDE.OnlineBusTicketBooking.Controller;

import JFS6WDE.OnlineBusTicketBooking.Entities.Bus;
import JFS6WDE.OnlineBusTicketBooking.Dto.UserDto;
import JFS6WDE.OnlineBusTicketBooking.Entities.Booking;
import JFS6WDE.OnlineBusTicketBooking.Services.BusServiceImpl;
import JFS6WDE.OnlineBusTicketBooking.Services.UserServiceImpl;
import JFS6WDE.OnlineBusTicketBooking.Services.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    @Autowired private BusServiceImpl busService;
    @Autowired private UserServiceImpl userService;
    @Autowired private BookingServiceImpl bookingService;

    private void adminLayout(Model model, String body, String title, String activePage) {
        model.addAttribute("body", body);
        model.addAttribute("pageTitle", title + " — GoReserve Admin");
        model.addAttribute("activePage", activePage);
    }

    // ══════════════════════════════
    // DASHBOARD
    // ══════════════════════════════
    @GetMapping("/adminBusList")
    public String adminDashboard(Model model) {
        List<Bus> buses            = busService.viewAllBuses();      // BusServiceImpl
        List<UserDto> users        = userService.findAllUsers();     // UserServiceImpl → List<UserDto>
        List<Booking> bookings     = bookingService.getAllBookings(); // BookingServiceImpl (share if broken)

        long revenue = bookings.stream()
            .filter(b -> b.getPayment() != null && "PAID".equals(b.getPayment().getPaymentStatus()))
            .mapToLong(Booking::getFare).sum();

        List<Booking> recent = bookings.size() > 5
            ? bookings.subList(bookings.size() - 5, bookings.size())
            : bookings;

        model.addAttribute("totalBuses",     buses.size());
        model.addAttribute("totalUsers",     users.size());
        model.addAttribute("totalBookings",  bookings.size());
        model.addAttribute("totalRevenue",   revenue);
        model.addAttribute("recentBookings", recent);
        model.addAttribute("buses",          buses);

        adminLayout(model, "admindashboard", "Dashboard", "dashboard");
        return "adminlayout";
    }

    // ══════════════════════════════
    // BUS MANAGEMENT
    // ══════════════════════════════
    @GetMapping("/adminBuses")
    public String manageBuses(Model model) {
        model.addAttribute("buses", busService.viewAllBuses());
        adminLayout(model, "adminbuslist", "Bus Management", "buses");
        return "adminlayout";
    }

    @GetMapping("/addBus")
    public String showAddBusForm(Model model) {
        adminLayout(model, "addbus", "Add Bus", "addbus");
        return "adminlayout";
    }

    @PostMapping("/addBus")
    public String addBus(@ModelAttribute Bus bus, Model model) {
        busService.createBus(bus);                   // createBus() — also sets fare = distance*2*price
        model.addAttribute("success", "Bus '" + bus.getBusName() + "' added successfully!");
        model.addAttribute("buses", busService.viewAllBuses());
        adminLayout(model, "adminbuslist", "Bus Management", "buses");
        return "adminlayout";
    }

    @PostMapping("/updateBus/{id}")
    public String updateBus(@PathVariable Long id, @ModelAttribute Bus bus, Model model) {
        bus.setBusId(id);
        busService.updateBus(bus);                   // updateBus() — updates existing entity
        model.addAttribute("success", "Bus updated successfully!");
        model.addAttribute("buses", busService.viewAllBuses());
        adminLayout(model, "adminbuslist", "Bus Management", "buses");
        return "adminlayout";
    }

    @PostMapping("/deleteBus/{id}")
    public String deleteBus(@PathVariable Long id, Model model) {
        busService.deleteBus(id);                    // deleteBus(long) — not deleteBusById
        model.addAttribute("success", "Bus deleted successfully.");
        model.addAttribute("buses", busService.viewAllBuses());
        adminLayout(model, "adminbuslist", "Bus Management", "buses");
        return "adminlayout";
    }

    // ══════════════════════════════
    // USER MANAGEMENT
    // ══════════════════════════════
    @GetMapping("/showUsers")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());     // findAllUsers() → List<UserDto>
        adminLayout(model, "adminusers", "Users", "users");
        return "adminlayout";
    }

    // ══════════════════════════════
    // BOOKINGS
    // ══════════════════════════════
    @GetMapping("/adminBookings")
    public String showAllBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        adminLayout(model, "adminbookings", "All Bookings", "bookings");
        return "adminlayout";
    }
}