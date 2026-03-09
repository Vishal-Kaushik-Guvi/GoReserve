package JFS6WDE.OnlineBusTicketBooking.Configuration;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

	
	@ControllerAdvice
	public class GlobalControllerAdvice {

	    @ModelAttribute 
	    public void setDefaultTheme(HttpSession session) {
	        if (session.getAttribute("theme") == null) {
	            session.setAttribute("theme", "light"); // default theme
	        }
	    }
	}
