package JFS6WDE.OnlineBusTicketBooking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import JFS6WDE.OnlineBusTicketBooking.Entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}