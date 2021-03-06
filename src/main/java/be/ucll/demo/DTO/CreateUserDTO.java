package be.ucll.demo.DTO;

import be.ucll.demo.Domain.Role;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateUserDTO {
    @NotEmpty
    @NotNull
    private String name,password;
    private Role role;

    public CreateUserDTO(){
        this.role = Role.USER;
    }
    public CreateUserDTO(String name, String password,Role role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
