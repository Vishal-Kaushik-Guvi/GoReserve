package JFS6WDE.OnlineBusTicketBooking.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "bookingHistoryList") // Prevent recursion
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long busId;

    @NotBlank(message = "Bus name can't be blank")
    @Size(max = 50, message = "Bus name must be under 50 characters")
    private String busName;

    @NotBlank(message = "Driver name can't be blank")
    @Size(max = 50, message = "Driver name must be under 50 characters")
    private String driverName;

    @NotBlank(message = "Bus type can't be blank")
    @Size(max = 30, message = "Bus type must be under 30 characters")
    private String busType; // e.g., Deluxe, Normal

    @NotBlank(message = "Route From can't be blank")
    @Size(max = 50)
    private String routeFrom;

    @NotBlank(message = "Route To can't be blank")
    @Size(max = 50)
    private String routeTo;

    @NotNull(message = "Journey date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate busJourneyDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime arrivalTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime departureTime;

    @Min(value = 1, message = "At least 1 seat is required")
    @NotNull(message = "Total seats must be specified")
    private Integer seats;

    @Min(value = 1, message = "Distance must be at least 1 km")
    private int distance;

    @Min(value = 0, message = "Fare must be non-negative")
    private Integer fare;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();
}
