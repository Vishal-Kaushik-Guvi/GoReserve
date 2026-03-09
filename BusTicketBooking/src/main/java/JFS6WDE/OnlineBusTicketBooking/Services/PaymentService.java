package JFS6WDE.OnlineBusTicketBooking.Services;

import JFS6WDE.OnlineBusTicketBooking.Entities.Payment;

public interface PaymentService {
    Payment getPaymentByBookingId(Long bookingId);
    void updatePaymentStatus(Long bookingId, String status);
}
