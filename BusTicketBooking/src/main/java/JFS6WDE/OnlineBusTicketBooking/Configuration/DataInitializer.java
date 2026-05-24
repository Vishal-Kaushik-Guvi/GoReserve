package JFS6WDE.OnlineBusTicketBooking.Configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import JFS6WDE.OnlineBusTicketBooking.Entities.Role;
import JFS6WDE.OnlineBusTicketBooking.Entities.User;
import JFS6WDE.OnlineBusTicketBooking.Repository.RoleRepository;
import JFS6WDE.OnlineBusTicketBooking.Repository.UserRepository;
import jakarta.transaction.Transactional;


@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional          // ← entire run() in one transaction
    public void run(String... args) {

        // Create ROLE_ADMIN if not exists
        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
            System.out.println("=== ROLE_ADMIN created ===");
        }

        // Create ROLE_USER if not exists
        if (roleRepository.findByName("ROLE_USER") == null) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
            System.out.println("=== ROLE_USER created ===");
        }

        // Create admin user if not exists
        if (userRepository.findByEmail("admin@goreserve.com") == null) {

            // Re-fetch role fresh from DB ← this is the key fix
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");

            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@goreserve.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Arrays.asList(adminRole));
            userRepository.save(admin);

            System.out.println("=== Admin user created ===");
            System.out.println("=== Email:    admin@goreserve.com ===");
            System.out.println("=== Password: admin123 ===");
        }
    }
}