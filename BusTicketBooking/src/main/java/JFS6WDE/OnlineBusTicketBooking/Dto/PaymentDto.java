package JFS6WDE.OnlineBusTicketBooking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private String cardNumber;
    private String upiId;
    private String cvv;
}