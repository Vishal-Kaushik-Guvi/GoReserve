package JFS6WDE.OnlineBusTicketBooking.Configuration;

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

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // ✅ CSRF enabled, ignore H2 console only
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )

            // ✅ Authorization rules
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/css/**", "/js/**", "/images/**",
                    "/webjars/**", "/static/**"
                ).permitAll()
                .requestMatchers(
                    "/", "/index",
                    "/register", "/register/save",
                    "/verify-otp",                  // ← covers GET + POST
                    "/browseBuses", "/about", "/login"
                ).permitAll()
                .requestMatchers(
                    "/adminBusList", "/addBus",
                    "/updateBus", "/deleteBus"
                ).hasRole("ADMIN")
                .requestMatchers(
                    "/book-ticket", "/find-bus",
                    "/booking-history"
                ).authenticated()
                .anyRequest().authenticated()
            )

            // ✅ SESSION FIX — migrates attributes to new session
            // prevents otp + tempUser being wiped after security events
            .sessionManagement(session -> session
                .sessionFixation().migrateSession()
            )

            // ✅ Custom login
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler())
                .permitAll()
                .failureHandler((request, response, exception) -> {
                    System.err.println("Login failed: " + exception.getMessage());
                    response.sendRedirect("/login?error");
                })
            )

            // ✅ Logout
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)        // ← clears session on logout
                .deleteCookies("JSESSIONID")        // ← cleans up cookie too
                .permitAll()
            )

            // ✅ Access denied page
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/403")
            );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    public class CustomAuthenticationSuccessHandler
            implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(
                HttpServletRequest request,
                HttpServletResponse response,
                Authentication authentication)
                throws IOException, ServletException {

            if (authentication.getAuthorities()
                    .contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                response.sendRedirect("/adminBusList");
            } else {
                response.sendRedirect("/userBusList");
            }
        }
    }
}