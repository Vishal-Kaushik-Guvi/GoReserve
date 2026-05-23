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

        UserDto user = new UserDto();

        model.addAttribute("user", user);

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

        // Check existing user
        User existingUser =
                userService.findUserByEmail(
                        userDto.getEmail());

        if (existingUser != null
                && existingUser.getEmail() != null
                && !existingUser.getEmail().isEmpty()) {

            result.rejectValue(
                    "email",
                    "null",
                    "There is already an account registered with the same email"
            );
        }

        // Validation errors
        if (result.hasErrors()) {

            model.addAttribute(
                    "user",
                    userDto
            );

            return "register";
        }

        
        // =========================
        // GENERATE OTP
        // =========================
        
        String otp = OtpUtil.generateOtp();

        
        // =========================
        // STORE OTP IN SESSION
        // =========================
        
        session.setAttribute("otp", otp);

        
        // Store temporary user
        session.setAttribute(
                "tempUser",
                userDto
        );

        
        // =========================
        // SEND OTP EMAIL
        // =========================
        
        emailService.sendOtp(
                userDto.getEmail(),
                otp
        );

        
        model.addAttribute(
                "message",
                "OTP sent to your email"
        );

        
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

        
        // Get saved OTP
        String savedOtp =
                (String) session.getAttribute("otp");

        
        // Get temporary user
        UserDto userDto =
                (UserDto) session.getAttribute("tempUser");

        
        // Compare OTP
        if (savedOtp != null
                && savedOtp.equals(userOtp)) {

            
            // Save user in database
            userService.saveUser(userDto);

            
            // Remove session data
            session.removeAttribute("otp");
            session.removeAttribute("tempUser");

            
            return "redirect:/login?success";

        } else {

            
            model.addAttribute(
                    "error",
                    "Invalid OTP"
            );

            
            return "verify-otp";
        }
    }

    
    // =========================
    // SHOW USERS
    // =========================
    
    @GetMapping("/users")
    public String users(Model model) {

        List<UserDto> users =
                userService.findAllUsers();

        model.addAttribute(
                "users",
                users
        );

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