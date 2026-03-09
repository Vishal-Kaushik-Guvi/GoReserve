//package JFS6WDE.OnlineBusTicketBooking.Controller;
//
//import org.springframework.web.bind.annotation.PostMapping;
//
//import jakarta.servlet.http.HttpSession;
//
//public class ThemeController {
//
//    @PostMapping("/toggle-theme")
//    public String toggleTheme(HttpSession session) {
//        String currentTheme = (String) session.getAttribute("theme");
//        if ("dark".equals(currentTheme)) {
//            session.setAttribute("theme", "light");
//        } else {
//            session.setAttribute("theme", "dark");
//        }
//        return "redirect:/"; // or redirect back to referer
//    }
//    
//}
