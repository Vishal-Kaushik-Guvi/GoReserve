package JFS6WDE.OnlineBusTicketBooking.Services;

import java.util.List;

import JFS6WDE.OnlineBusTicketBooking.Dto.UserDto;
import JFS6WDE.OnlineBusTicketBooking.Entities.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}