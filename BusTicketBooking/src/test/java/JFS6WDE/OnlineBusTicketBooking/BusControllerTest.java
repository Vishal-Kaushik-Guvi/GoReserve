//package JFS6WDE.OnlineBusTicketBooking;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//import java.util.Collections;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.ui.Model;
//
//import JFS6WDE.OnlineBusTicketBooking.Controller.BusController;
//import JFS6WDE.OnlineBusTicketBooking.Entities.Bus;
//import JFS6WDE.OnlineBusTicketBooking.Services.BusServiceImpl;
//import org.springframework.data.domain.Page;
//
//public class BusControllerTest {
//
//    @Mock
//    private BusServiceImpl busService;
//
//    @Mock
//    private Model model;
//
//    @InjectMocks
//    private BusController busController;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testHome() {
//        String viewName = busController.home();
//        assertEquals("index", viewName);
//    }
//
//    @Test
//    public void testAddBusForm() {
//        String viewName = busController.addBusForm();
//        assertEquals("addbus", viewName);
//    }
//
//    @Test
//    public void testAddBus() {
//        Bus bus = new Bus();
//        String viewName = busController.addBus(bus, model);
//        verify(busService).createBus(bus);
//        verify(model).addAttribute("success", true);
//        assertEquals("addbus", viewName);
//    }
//
//    @Test
//    public void testViewHomePage() {
//        Page<Bus> page = mock(Page.class);
//        when(busService.findPaginated(anyInt(), anyInt(), anyString(), anyString())).thenReturn(page);
//        String viewName = busController.viewHomePage(model);
//        verify(model).addAttribute("currentPage", 1);
//        assertEquals("index", viewName);
//    }
//
//    @Test
//    public void testShowAdminBusList() {
//        List<Bus> buses = Collections.emptyList();
//        when(busService.viewAllBuses()).thenReturn(buses);
//        String viewName = busController.showAdminBusList(model);
//        verify(model).addAttribute("buses", buses);
//        assertEquals("buslist", viewName);
//    }
//
//    @Test
//    public void testShowUserBusList() {
//        List<Bus> buses = Collections.emptyList();
//        when(busService.viewAllBuses()).thenReturn(buses);
//        String viewName = busController.showUserBusList(model);
//        verify(model).addAttribute("buses", buses);
//        assertEquals("index", viewName);
//    }
//
//    @Test
//    public void testShowUpdateBusForm() {
//        Bus bus = new Bus();
//        when(busService.viewBusById(anyLong())).thenReturn(bus);
//        String viewName = busController.showUpdateBusForm(1L, model);
//        verify(model).addAttribute("bus", bus);
//        assertEquals("updatebus", viewName);
//    }
//
//    @Test
//    public void testUpdateBus() {
//        Bus bus = new Bus();
//        String viewName = busController.updateBus(bus, model);
//        verify(busService).updateBus(bus);
//        verify(model).addAttribute("success", true);
//        assertEquals("redirect:/adminBusList", viewName);
//    }
//
//    @Test
//    public void testDeleteBus() {
//        String viewName = busController.deleteBus(1L);
//        verify(busService).deleteBus(1L);
//        assertEquals("redirect:/adminBusList", viewName);
//    }
//
//}
