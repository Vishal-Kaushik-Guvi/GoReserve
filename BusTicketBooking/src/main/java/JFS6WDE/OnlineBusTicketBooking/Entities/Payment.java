package JFS6WDE.OnlineBusTicketBooking.Entities;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;
    private String cvv;
    
    private String upiId;
    private String paymentStatus; // "PAID", "FAILED", etc.

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = true)
    private Booking booking;
    
    private LocalDate paymentDate;
    
    private LocalTime paymentTime;
    
    private String paymentMode;
    
    @OneToOne
    @JoinColumn(name = "archived_booking_id")  // Allow null for original booking case
    private ArchivedBooking archivedBooking;
    
}
