package JFS6WDE.OnlineBusTicketBooking.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import JFS6WDE.OnlineBusTicketBooking.Dto.UserDto;
import JFS6WDE.OnlineBusTicketBooking.Entities.User;
import JFS6WDE.OnlineBusTicketBooking.Services.EmailService;
import JFS6WDE.OnlineBusTicketBooking.Services.UserServiceImpl;
import JFS6WDE.OnlineBusTicketBooking.Utility.OtpUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private EmailService emailService;


    // =========================
    // SHOW REGISTER PAGE
    // =========================

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }


    // =========================
    // REGISTER + SEND OTP
    // =========================

    @PostMapping("/register/save")
    public String registration(
            @Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model,
            HttpSession session) {

        // Check existing email
        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if (existingUser != null
                && existingUser.getEmail() != null
                && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", "null",
                    "There is already an account registered with this email");
        }

        // Return to form if validation errors
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register";
        }

        // Generate OTP
        String otp = OtpUtil.generateOtp();

        // Store in session
        session.setAttribute("otp", otp);
        session.setAttribute("tempUser", userDto);

        // Debug logs
        System.out.println("=== SESSION ID (register): " + session.getId());
        System.out.println("=== OTP generated: " + otp);

        // Send OTP email
        try {
            emailService.sendOtp(userDto.getEmail(), otp);
            model.addAttribute("message", "OTP sent to " + userDto.getEmail());
        } catch (Exception e) {
            System.err.println("=== MAIL ERROR: " + e.getMessage());
            model.addAttribute("error", "Failed to send OTP. Check mail config.");
        }

        return "verify-otp";   // ← this line is what was missing
    }


    // =========================
    // SHOW VERIFY OTP PAGE  ← THIS WAS MISSING
    // =========================

    @GetMapping("/verify-otp")
    public String showVerifyOtp() {
        return "verify-otp";
    }


    // =========================
    // VERIFY OTP
    // =========================

    @PostMapping("/verify-otp")
    public String verifyOtp(
            @RequestParam("otp") String userOtp,
            HttpSession session,
            Model model) {

        // Debug logs — remove after testing
        System.out.println("=== SESSION ID (verify): " + session.getId());
        System.out.println("=== OTP from session: " + session.getAttribute("otp"));
        System.out.println("=== OTP entered by user: " + userOtp);

        String savedOtp = (String) session.getAttribute("otp");
        UserDto userDto = (UserDto) session.getAttribute("tempUser");

        // Session expired
        if (savedOtp == null || userDto == null) {
            model.addAttribute("error", 
                "Session expired. Please register again.");
            return "verify-otp";
        }

        // OTP matched
        if (savedOtp.equals(userOtp.trim())) {
            userService.saveUser(userDto);
            session.removeAttribute("otp");
            session.removeAttribute("tempUser");
            return "redirect:/login?success";
        }

        // OTP wrong
        model.addAttribute("error", "Invalid OTP. Please try again.");
        return "verify-otp";
    }


    // =========================
    // SHOW USERS
    // =========================

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }


    // =========================
    // LOGIN PAGE
    // =========================

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}