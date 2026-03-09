package JFS6WDE.OnlineBusTicketBooking.Controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import JFS6WDE.OnlineBusTicketBooking.Dto.BookingDto;
import JFS6WDE.OnlineBusTicketBooking.Entities.Booking;
import JFS6WDE.OnlineBusTicketBooking.Entities.Bus;
import JFS6WDE.OnlineBusTicketBooking.Services.BookingService;
import JFS6WDE.OnlineBusTicketBooking.Services.BusService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BusService busService;

    // ✅ Show user's booking history
    @GetMapping("/booking-history")
    public String viewUserBookingHistory(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        String email = currentUser.getUsername();
        List<Booking> bookings = bookingService.getPaidBookingsByUserEmail(email);
        model.addAttribute("bookings", bookings);
        model.addAttribute("pageTitle", "Booking History - GoReserve");
        model.addAttribute("body", "bookinghistory");
        return "layout";
    }

    // ✅ View a specific booking
    @GetMapping("/viewBooking")
    public String findBookingById(@RequestParam long id, Model model) {
        Booking booking = bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        model.addAttribute("pageTitle", "View Booking - GoReserve");
        model.addAttribute("body", "viewbooking");
        return "layout";
    }

    // ✅ Show form to book a bus
    @GetMapping("/addBooking")
    public String showAddBookingForm(@RequestParam long busId, Model model) {
        Bus bus = busService.getBusById(busId);
        model.addAttribute("bus", bus);
        model.addAttribute("bookingDto", new BookingDto());
        model.addAttribute("pageTitle", "Book Ticket - GoReserve");
        model.addAttribute("body", "addbooking");       // ✅ routes through layout
        model.addAttribute("fullWidth", true);           // ✅ edge-to-edge, no <main> wrapper
        return "layout";
    }

    // ✅ Submit booking and redirect to payment
    @PostMapping("/addBooking")
    public String addBooking(
            @RequestParam long busId,
            @Valid @ModelAttribute BookingDto bookingDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails currentUser,
            Model model) {

        if (bindingResult.hasErrors()) {
            Bus bus = busService.getBusById(busId);
            model.addAttribute("bus", bus);
            model.addAttribute("bookingDto", bookingDto);
            model.addAttribute("pageTitle", "Book Ticket - GoReserve");
            model.addAttribute("body", "addbooking");
            model.addAttribute("fullWidth", true);
            return "layout";
        }

        String email = currentUser.getUsername();
        Long bookingId = bookingService.saveBooking(bookingDto, email, busId);
        return "redirect:/bookings/payment?bookingId=" + bookingId;
    }

    // ✅ Cancel booking
    @PostMapping("/cancelBooking")
    public String cancelBooking(@RequestParam("bookingId") Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return "redirect:/bookings/booking-history";
    }
}