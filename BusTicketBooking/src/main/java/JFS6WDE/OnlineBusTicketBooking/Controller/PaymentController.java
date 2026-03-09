package JFS6WDE.OnlineBusTicketBooking.Controller;
import JFS6WDE.OnlineBusTicketBooking.Dto.PaymentDto;
import JFS6WDE.OnlineBusTicketBooking.Entities.Booking;
import JFS6WDE.OnlineBusTicketBooking.Entities.Payment;
import JFS6WDE.OnlineBusTicketBooking.Services.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookings")
public class PaymentController {

    @Autowired
    private BookingServiceImpl bookingService;

    @GetMapping("/payment")
    public String showPaymentForm(@RequestParam("bookingId") Long bookingId, Model model) {
        Booking booking = bookingService.getBookingById(bookingId);
        model.addAttribute("booking", booking);
        model.addAttribute("paymentDto", new PaymentDto());
        model.addAttribute("pageTitle", "Payment - GoReserve");
        model.addAttribute("body", "payment");
        model.addAttribute("fullWidth", true);
        return "layout";
    }

    @PostMapping("/payment")
    public String processPayment(@RequestParam("bookingId") Long bookingId,
                                 @ModelAttribute PaymentDto paymentDto,
                                 Model model) {
        Booking booking = bookingService.getBookingById(bookingId);

        if (booking == null) {
            model.addAttribute("error", "Booking not found.");
            model.addAttribute("paymentDto", paymentDto);
            model.addAttribute("pageTitle", "Payment - GoReserve");
            model.addAttribute("body", "payment");
            model.addAttribute("fullWidth", true);
            return "layout";
        }

        Payment payment = new Payment();
        payment.setCardNumber(paymentDto.getCardNumber());
        payment.setUpiId(paymentDto.getUpiId());
        payment.setCvv(paymentDto.getCvv());
        payment.setPaymentStatus("PAID");
        payment.setBooking(booking);
        booking.setPayment(payment);
        bookingService.updateBookingWithPayment(booking);

        model.addAttribute("booking", booking);
        model.addAttribute("pageTitle", "Booking Confirmed! - GoReserve");
        model.addAttribute("body", "paymentconfirmation");
        model.addAttribute("fullWidth", true);
        return "layout";
    }
}