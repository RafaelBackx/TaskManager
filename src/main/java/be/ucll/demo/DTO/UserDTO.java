package be.ucll.demo.DTO;

import be.ucll.demo.Domain.Role;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDTO {
    long id;
    @NotEmpty
    @NotNull
    private String name,password;
    private Role role;

    public UserDTO(){
        this.role = Role.USER;
    }
    public UserDTO(String name, String password,Role role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
