package at.technikumwien.webshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository, passwordEncoder);
    }

    // Unit Test
    @Test
    public void whenGetAllUsers_thenGetAListOfUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());

        when(userRepository.findAll()).thenReturn(userList);

        List<User> newUserList = userService.getAllUsers();

        assertEquals(userList, newUserList);
    }

    @Test
    public void testCreateUser() {
        User testCreateUser = new User();
        testCreateUser.setUsername("testUserName");
        testCreateUser.setPassword("testPassword");

        when(userRepository.save(any())).thenReturn(testCreateUser);

        User testResult = userService.createUser(testCreateUser);

        assertEquals(passwordEncoder.encode("testPassword"), testResult.getPassword());
        assertEquals("testUserName", testResult.getUsername());

    }

    @Test
    public void testGetUserById() {
        User testUser = new User();


        when(userRepository.findById(any())).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.getUserById(1L);

        assertEquals(testUser, result.get());
    }

    
}
