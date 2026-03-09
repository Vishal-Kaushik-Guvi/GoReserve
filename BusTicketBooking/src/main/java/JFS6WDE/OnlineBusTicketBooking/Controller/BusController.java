package JFS6WDE.OnlineBusTicketBooking.Controller;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import JFS6WDE.OnlineBusTicketBooking.Entities.Bus;
import JFS6WDE.OnlineBusTicketBooking.Services.BusServiceImpl;

@Controller
public class BusController {
    @Autowired
    private BusServiceImpl busService;

    @GetMapping("/")
    public String home(Model model) {
        return "redirect:/browseBuses";
    }

    @GetMapping("/index")
    public String indexPage(Model model) {
        model.addAttribute("pageTitle", "GoReserve - Home");
        model.addAttribute("body", "index");
        model.addAttribute("fullWidth", true); // ✅ renders edge-to-edge, no <main> wrapper
        return "layout";
    }

    @GetMapping("/userBusList")
    public String showUserBusList(Model model) {
        List<Bus> buses = busService.viewAllBuses();
        model.addAttribute("buses", buses);
        model.addAttribute("pageTitle", "Available Buses");
        model.addAttribute("body", "index");
        model.addAttribute("fullWidth", true);
        return "layout";
    }

    @GetMapping("/browseBuses")
    public String showAllBuses(Model model) {
        List<Bus> buses = busService.viewAllBuses();
        model.addAttribute("buses", buses);
        model.addAttribute("pageTitle", "Browse Buses");
        model.addAttribute("body", "showbuses");
        return "layout";
    }

    @GetMapping("/book-ticket")
    public String showBookingPage(Principal principal) {
        if (principal == null) {
            return "redirect:/login?error";
        }
        return "redirect:/browseBuses";
    }
}
