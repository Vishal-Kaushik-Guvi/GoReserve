package JFS6WDE.OnlineBusTicketBooking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import JFS6WDE.OnlineBusTicketBooking.Entities.ArchivedBooking;
import JFS6WDE.OnlineBusTicketBooking.Entities.User;

public interface ArchivedBookingRepository extends JpaRepository<ArchivedBooking, Long> {
    List<ArchivedBooking> findByUser(User user);
}