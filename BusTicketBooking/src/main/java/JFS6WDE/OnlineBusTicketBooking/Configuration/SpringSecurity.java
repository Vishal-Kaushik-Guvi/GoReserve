package JFS6WDE.OnlineBusTicketBooking.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurity {

    private static final Logger logger = LoggerFactory.getLogger(SpringSecurity.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 🔐 Enable CSRF (recommended for production)
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**") // optional if using H2 DB
            )

            // ✅ Authorization Rules
            .authorizeHttpRequests(auth -> auth
                // Allow public resources
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/static/**").permitAll()
                .requestMatchers("/", "/index", "/register/**", "/browseBuses", "/about", "/login").permitAll()

                // Admin-only section
                .requestMatchers("/adminBusList", "/addBus", "/updateBus", "/deleteBus").hasRole("ADMIN")


                // Authenticated users (both USER and ADMIN)
                .requestMatchers("/book-ticket", "/find-bus", "/booking-history").authenticated()

                // All other requests
                .anyRequest().authenticated()
            )

            // ✅ Custom Login Config
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler()) // custom redirect logic
                .permitAll()
                .failureHandler((request, response, exception) -> {
                    // Log and redirect on failed login
                    System.err.println("Authentication failure: " + exception.getMessage());
                    response.sendRedirect("/login?error");
                })
            )

            // ✅ Logout Config
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/") // optional redirect after logout
                .permitAll()
            )

            // ✅ Optional: Access Denied Page (403)
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/403")
            );

        return http.build();
    }


    // Handiling Request accordingly
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                Authentication authentication) throws IOException, ServletException {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                response.sendRedirect("/adminBusList");
            } else {
                response.sendRedirect("/userBusList");
            }
        }
    }
}