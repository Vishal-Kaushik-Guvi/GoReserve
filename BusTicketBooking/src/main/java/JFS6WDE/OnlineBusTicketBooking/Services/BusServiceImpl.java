package JFS6WDE.OnlineBusTicketBooking.Services;


import JFS6WDE.OnlineBusTicketBooking.Entities.Bus;
import JFS6WDE.OnlineBusTicketBooking.Exception.AdminException;
import JFS6WDE.OnlineBusTicketBooking.Exception.ResourceNotFound;
import JFS6WDE.OnlineBusTicketBooking.Repository.BusRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusServiceImpl implements BusService {
    @Autowired
    private BusRepository busRepo;
    
    
    @Override
    public Bus getBusById(long id) {
        return busRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Bus not found with id: " + id));
    }

    // Admin methods
    @Override
    public Bus createBus(Bus bus) throws ResourceNotFound, AdminException {
    	int price = bus.getFare();
       bus.setFare(bus.getDistance()*2*price);
       return busRepo.save(bus);
    }

    @Override
    public Bus updateBus(Bus bus) throws ResourceNotFound {
        Optional<Bus> busOptional = busRepo.findById(bus.getBusId());
        if (!busOptional.isPresent()) {
            throw new ResourceNotFound("Bus doesn't exist with busId: " + bus.getBusId());
        }
        Bus existingBus = busOptional.get();
    
        // Update other bus fields if necessary
        existingBus.setBusName(bus.getBusName());
        existingBus.setDriverName(bus.getDriverName());
        existingBus.setBusType(bus.getBusType());
        existingBus.setRouteFrom(bus.getRouteFrom());
        existingBus.setRouteTo(bus.getRouteTo());
        existingBus.setBusJourneyDate(bus.getBusJourneyDate());
        existingBus.setArrivalTime(bus.getArrivalTime());
        existingBus.setDepartureTime(bus.getDepartureTime());
        existingBus.setSeats(bus.getSeats());
        existingBus.setDistance(bus.getDistance());
        existingBus.setFare(bus.getFare());
    
        // Add other fields that need to be updated
    
        return busRepo.save(existingBus);
    }

    @Override
    public void deleteBus(long busId) throws ResourceNotFound, AdminException {
        Optional<Bus> bus = busRepo.findById(busId);

        if (bus.isPresent()) {
           busRepo.deleteById(busId);
     }  else{
        throw new ResourceNotFound("Bus Not Found with id: " +busId);
     }
}

    @Override
    public List<Bus> findByRouteFromAndRouteTo(String routeFrom, String routeTo) {
        return busRepo.findByRouteFromAndRouteTo(routeFrom, routeTo);
    }

    // Admin + User methods
    @Override
    public List<Bus> viewAllBuses() throws ResourceNotFound {
       return busRepo.findAll();
    }

    @Override
    public Bus viewBusById(long busId) throws ResourceNotFound {
          return busRepo.findById(busId).get();
    }

    @Override
    public List<Bus> viewBusByBusType(String busType) throws ResourceNotFound {
        List<Bus> busListType = busRepo.findByBusType(busType);
        if (busListType.isEmpty()) {
            throw new ResourceNotFound("There are no buses with bus type: " + busType);
        }
        return busListType;
    }

//    @Override
//	public Page<Bus> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
//		// check if the sorting is ascending or descending
//		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
//				: Sort.by(sortField).descending();
//		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
//		return busRepo.findAll(pageable);
//	}
    

}
