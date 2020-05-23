package be.ucll.demo.Controller;

import be.ucll.demo.DB.UserService;
import be.ucll.demo.DTO.CreateUserDTO;
import be.ucll.demo.DTO.UserDTO;
import be.ucll.demo.Domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping("/register")
    public String goToRegister(Model model) {
        model.addAttribute("user",new CreateUserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid CreateUserDTO user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            return "register";
        }
        service.createUser(user);
        model.addAttribute("success","You have successfully registered");
        return "login";
    }

    @GetMapping("/login")
    public String goToLogin(){
        return "login";
    }
}
