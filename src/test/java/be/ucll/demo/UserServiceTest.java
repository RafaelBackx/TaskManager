package be.ucll.demo;

import be.ucll.demo.DB.UserService;
import be.ucll.demo.DTO.CreateUserDTO;
import be.ucll.demo.Domain.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService service;
    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    public void setup(){
        CreateUserDTO userdto = new CreateUserDTO();
        userdto.setName("Rafael");
        userdto.setPassword(encoder.encode("test"));
        userdto.setRole(Role.ADMIN);
        service.createUser(userdto);
    }

    @Test
    public void getUser(){
        assertNotNull(service.getAll().get(0));
    }

    @Test
    public void addMultipleUsers(){
        CreateUserDTO userdto = new CreateUserDTO();
        userdto.setName("TestUser");
        userdto.setPassword(encoder.encode("test"));
        userdto.setRole(Role.ADMIN);
        service.createUser(userdto);

        long userId = service.getAll().get(0).getId();
        service.deleteUser(userId);
        assertEquals(1,service.getAll().size());
    }

    @AfterEach
    public void deleteUser(){
        long userId = service.getAll().get(0).getId();
        service.deleteUser(userId);
        assertEquals(0,service.getAll().size());
    }

}
