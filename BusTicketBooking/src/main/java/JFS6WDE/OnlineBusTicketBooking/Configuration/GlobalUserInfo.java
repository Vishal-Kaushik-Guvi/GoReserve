package JFS6WDE.OnlineBusTicketBooking.Configuration;

import JFS6WDE.OnlineBusTicketBooking.Entities.User;
import JFS6WDE.OnlineBusTicketBooking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalUserInfo {

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void addUserNameToModel(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            if (user != null) {
                model.addAttribute("loggedInUserName", user.getName()); // or user.getFirstName() + " " + user.getLastName()
            }
        }
    }
}
