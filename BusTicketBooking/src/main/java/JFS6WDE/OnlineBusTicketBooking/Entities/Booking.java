package JFS6WDE.OnlineBusTicketBooking.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    private String routeFrom;
    private String routeTo;
    private Integer distance;
    private Integer fare;

    @Max(20)
    private int bookseat;

    private LocalDate bookingDate;
    private LocalTime bookingTime;

    @ElementCollection
    @CollectionTable(name = "booking_passenger_names", joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "passenger_name")
    private List<String> passengerNames;

    @ElementCollection
    @CollectionTable(name = "booking_passenger_ages", joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "passenger_age")
    private List<Integer> passengerAges;

    private String mobileNumber;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;
    
}