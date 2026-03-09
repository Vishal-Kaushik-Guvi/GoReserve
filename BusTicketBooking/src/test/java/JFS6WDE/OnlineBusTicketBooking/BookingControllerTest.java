//package JFS6WDE.OnlineBusTicketBooking;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//import java.util.Collections;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//
//import JFS6WDE.OnlineBusTicketBooking.Controller.BookingController;
//import JFS6WDE.OnlineBusTicketBooking.Dto.BookingDto;
//import JFS6WDE.OnlineBusTicketBooking.Entities.Booking;
//import JFS6WDE.OnlineBusTicketBooking.Services.BookingServiceImpl;
//
//public class BookingControllerTest {
//
//    @Mock
//    private BookingServiceImpl bookingService;
//
//    @Mock
//    private Model model;
//
//    @Mock
//    private BindingResult bindingResult;
//
//    @Mock
//    private UserDetails currentUser;
//
//    @InjectMocks
//    private BookingController bookingController;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testGetAllBookings() {
//        when(bookingService.getAllBooking()).thenReturn(Collections.emptyList());
//        
//        String viewName = bookingController.getAllBookings(model);
//        
//        verify(model).addAttribute("bookingHistoryList", Collections.emptyList());
//        assertEquals("bookinghistory", viewName);
//    }
//
//    @Test
//    public void testDeleteBooking() {
//        String viewName = bookingController.deleteBooking(1L);
//        
//        verify(bookingService).deleteBookingById(1L);
//        assertEquals("redirect:/bookings/viewBookingHistory", viewName);
//    }
//
//    @Test
//    public void testFindBookingById() {
//        Booking booking = new Booking();
//        when(bookingService.getBookingById(anyLong())).thenReturn(booking);
//        
//        String viewName = bookingController.findBookingById(1L, model);
//        
//        verify(model).addAttribute("booking", booking);
//        assertEquals("viewbooking", viewName);
//    }
//
//    @Test
//    public void testShowAddBookingForm() {
//        String viewName = bookingController.showAddBookingForm(1L, model);
//        
//        verify(model).addAttribute("busId", 1L);
//        verify(model).addAttribute("bookingDto", new BookingDto());
//        assertEquals("addbooking", viewName);
//    }
//
//    @Test
//    public void testAddBooking_WithErrors() {
//        when(bindingResult.hasErrors()).thenReturn(true);
//
//        String viewName = bookingController.addBooking(1L, new BookingDto(), bindingResult, currentUser, model);
//
//        verify(model).addAttribute("bookingDto", new BookingDto());
//        verify(model).addAttribute("busId", 1L);
//        assertEquals("addbooking", viewName);
//    }
//
//    @Test
//    public void testAddBooking_NoErrors() {
//        when(bindingResult.hasErrors()).thenReturn(false);
//        when(currentUser.getUsername()).thenReturn("test@example.com");
//
//        String viewName = bookingController.addBooking(1L, new BookingDto(), bindingResult, currentUser, model);
//
//        verify(bookingService).saveBooking(any(BookingDto.class), anyString(), anyLong());
//        assertEquals("redirect:/bookings/viewBookingHistory", viewName);
//    }
//}
//
