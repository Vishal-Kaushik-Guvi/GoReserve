package JFS6WDE.OnlineBusTicketBooking.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import JFS6WDE.OnlineBusTicketBooking.Entities.Bus;
import JFS6WDE.OnlineBusTicketBooking.Services.BusServiceImpl;

@Controller
public class RouteController {

    @Autowired
    private BusServiceImpl busService;

    @GetMapping("/showRoute")
    public String showSearchBusPage() {
        return "showroute";
    }

    @GetMapping("/searchBus")
    public String searchBus(@RequestParam("routeFrom") String routeFrom,
                            @RequestParam("routeTo") String routeTo,
                            Model model) {
        List<Bus> buses = busService.findByRouteFromAndRouteTo(routeFrom, routeTo);
        model.addAttribute("buses", buses);
        return "showroute";
    }
}
