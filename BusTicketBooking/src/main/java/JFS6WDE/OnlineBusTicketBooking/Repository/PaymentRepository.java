package JFS6WDE.OnlineBusTicketBooking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import JFS6WDE.OnlineBusTicketBooking.Entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Payment findByBookingId(Long bookingId);
}
