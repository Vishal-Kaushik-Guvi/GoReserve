package JFS6WDE.OnlineBusTicketBooking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import JFS6WDE.OnlineBusTicketBooking.Entities.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus,Long> {
   public List<Bus> findByBusType(String busType);

   List<Bus> findByRouteFromAndRouteTo(String routeFrom, String routeTo);
}