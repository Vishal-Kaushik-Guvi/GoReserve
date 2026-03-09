package JFS6WDE.OnlineBusTicketBooking.Entities;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class ArchivedBooking {
    @Id
    private Long id;

    private String routeFrom;
    private String routeTo;
    private int bookseat;
    private double fare;
    private LocalDate bookingDate;
    private LocalTime bookingTime;

    @ManyToOne
    private Bus bus;

    @ManyToOne
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    private String status = "CANCELLED";  // can add timestamp if needed
}
