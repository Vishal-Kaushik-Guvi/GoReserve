package JFS6WDE.OnlineBusTicketBooking.Dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    private Long id;

    @Min(value = 1, message = "At least 1 seat must be booked")
    @Max(value = 20, message = "You can book a maximum of 20 seats")
    private int bookseat;

    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;

    @NotNull(message = "Passenger names are required")
    @Size(min = 1, message = "At least one passenger name is required")
    private List<String> passengerNames;

    @NotNull(message = "Passenger ages are required")
    @Size(min = 1, message = "At least one passenger age is required")
    private List<Integer> passengerAges;

    private LocalDate bookingDate;
    private LocalTime bookingTime;
}