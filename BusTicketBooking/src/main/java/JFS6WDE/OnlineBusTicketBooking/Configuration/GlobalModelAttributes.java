package JFS6WDE.OnlineBusTicketBooking.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import JFS6WDE.OnlineBusTicketBooking.Entities.User;
import JFS6WDE.OnlineBusTicketBooking.Services.UserServiceImpl;

@ControllerAdvice
public class GlobalModelAttributes {
	
	@Autowired
    private final UserServiceImpl userService;

    public GlobalModelAttributes(UserServiceImpl userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void addUserDetails(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            String email = auth.getName(); // returns the logged-in user's email
            User user = userService.findUserByEmail(email);
            if (user != null) {
                model.addAttribute("loggedInUser", user.getName());
            }
        }
    }
}