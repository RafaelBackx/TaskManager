package be.ucll.demo.DB;

import be.ucll.demo.DTO.CreateUserDTO;
import be.ucll.demo.DTO.UserDTO;
import be.ucll.demo.Domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDTO createUser(CreateUserDTO user);

    void deleteUser(long id);

    List<User> getAll();
}
