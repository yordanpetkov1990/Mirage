package nightclub.web.nightclub.services;

import nightclub.web.nightclub.entities.User;
import nightclub.web.nightclub.entities.dtos.RegistrationDTO;

public interface UserService {
    void registerUser(RegistrationDTO registrationDTO);

    User findById(Long id);

    Iterable<User> getAllUsers();

    boolean findUserByEmail(String value);

    boolean findUserByUserName(String value);

    boolean findByPhoneNumber(String value);
}
