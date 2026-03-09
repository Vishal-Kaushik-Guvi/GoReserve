package JFS6WDE.OnlineBusTicketBooking.Services;

import java.util.List;

import JFS6WDE.OnlineBusTicketBooking.Dto.BookingDto;
import JFS6WDE.OnlineBusTicketBooking.Entities.Booking;

public interface BookingService {

	 List<Booking> getAllBookings(); // renamed for plural consistency

	    void deleteBookingById(long id);

	    Booking getBookingById(long id);

	    // Save a new booking and return its ID
	    Long saveBooking(BookingDto bookingDto, String userEmail, long busId);
	    
	    void updateBookingWithPayment(Booking booking);
	    
	    List<Booking> getPaidBookingsByUserEmail(String email);
	    
	    void cancelBooking(Long bookingId);
	    
	    void saveUpdatedBooking(Booking booking);
}
