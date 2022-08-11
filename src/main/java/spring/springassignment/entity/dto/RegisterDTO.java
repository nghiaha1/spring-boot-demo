package spring.springassignment.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.springassignment.entity.Role;

import java.util.Set;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RegisterDTO {
    private String fullname;
    private String username;
    private String password;
    private Set<Role> roles;

}
