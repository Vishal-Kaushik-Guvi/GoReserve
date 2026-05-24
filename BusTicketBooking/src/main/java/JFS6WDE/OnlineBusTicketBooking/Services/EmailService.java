package JFS6WDE.OnlineBusTicketBooking.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtp(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your GoReserve OTP");
        message.setText("Your OTP for registration is: " + otp 
                      + "\n\nThis OTP is valid for 5 minutes."
                      + "\n\n— GoReserve Team");
        mailSender.send(message);
    }
}