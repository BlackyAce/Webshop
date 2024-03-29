package at.technikumwien.webshop.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.technikumwien.webshop.dto.UserDTO;
import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDTO userDTO) {
        User user = fromDTO(userDTO);
        User createdUser = userService.createUser(user);
        return ResponseEntity.created(URI.create("http://localhost:8080/users")).body(createdUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    private User fromDTO(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setAdmin(userDTO.isAdmin());
        user.setSalutation(userDTO.getSalutation());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setStreetadress(userDTO.getStreetadress());
        user.setStreetnumber(userDTO.getStreetnumber());
        user.setCity(userDTO.getCity());
        user.setPostalcode(userDTO.getPostalcode());
        user.setCountry(userDTO.getCountry());
        return user;
    }
}
