package JFS6WDE.OnlineBusTicketBooking.Controller;

import JFS6WDE.OnlineBusTicketBooking.Dto.UserDto;
import JFS6WDE.OnlineBusTicketBooking.Entities.Bus;
import JFS6WDE.OnlineBusTicketBooking.Services.BusService;

import JFS6WDE.OnlineBusTicketBooking.Services.UserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminController {

	@Autowired
	private BusService busService;
	
	@Autowired
	private UserService userService;

	@GetMapping("/adminBusList")
	public String showAdminBusList(Model model) {
	    List<Bus> buses = busService.viewAllBuses();
	    model.addAttribute("buses", buses);
	    model.addAttribute("pageTitle", "Admin Bus List");
	    return "adminBusList"; // Uses client-side pagination now
	}

	@GetMapping("/addBus")
	public String addBusForm(Model model) {
	    model.addAttribute("bus", new Bus());
	    model.addAttribute("pageTitle", "Add New Bus");
	    return "addbus";
	}

	@PostMapping("/addBus")
	public String addBus(@Valid @ModelAttribute("bus") Bus bus,
	                     BindingResult result,
	                     Model model,
	                     RedirectAttributes redirectAttributes) {
	    if (result.hasErrors()) {
	        model.addAttribute("pageTitle", "Add New Bus");
	        return "addbus";
	    }
	    busService.createBus(bus);
	    redirectAttributes.addFlashAttribute("success", true);
	    return "redirect:/addBus";
	}


	@GetMapping("/updateBus")
	public String showUpdateBusForm(@RequestParam("busId") long busId, Model model) {
	    Bus bus = busService.viewBusById(busId);
	    model.addAttribute("bus", bus);
	    model.addAttribute("pageTitle", "Update Bus");
	    return "updatebus";
	}

	@PostMapping("/updateBus")
	public String updateBus(@Valid @ModelAttribute("bus") Bus bus, BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        model.addAttribute("pageTitle", "Update Bus");
	        return "updatebus";
	    }
	    busService.updateBus(bus);
	    return "redirect:/adminBusList";
	}

	@PostMapping("/deleteBus")
	public String deleteBus(@RequestParam("busId") long busId) {
	    busService.deleteBus(busId);
	    return "redirect:/adminBusList";
	}
	
	@GetMapping("/showUsers")
	public String showAllUsers(Model model) {
	    List<UserDto> users = userService.findAllUsers();
	    model.addAttribute("user", users); // <-- List<UserDto>
	    model.addAttribute("pageTitle", "All Users");
	    return "showusers";
	}

}