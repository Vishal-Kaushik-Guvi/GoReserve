package JFS6WDE.OnlineBusTicketBooking;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import JFS6WDE.OnlineBusTicketBooking.Controller.RouteController;
import JFS6WDE.OnlineBusTicketBooking.Entities.Bus;
import JFS6WDE.OnlineBusTicketBooking.Services.BusServiceImpl;

public class RouteControllerTest {

    @Mock
    private BusServiceImpl busService;

    @Mock
    private Model model;

    @InjectMocks
    private RouteController routeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowSearchBusPage() {
        String viewName = routeController.showSearchBusPage();
        assertEquals("showroute", viewName);
    }

    @Test
    public void testSearchBus() {
        List<Bus> buses = Collections.emptyList();
        when(busService.findByRouteFromAndRouteTo(anyString(), anyString())).thenReturn(buses);
        
        String viewName = routeController.searchBus("RouteFrom", "RouteTo", model);
        
        verify(busService).findByRouteFromAndRouteTo("RouteFrom", "RouteTo");
        verify(model).addAttribute("buses", buses);
        assertEquals("showroute", viewName);
    }
}
