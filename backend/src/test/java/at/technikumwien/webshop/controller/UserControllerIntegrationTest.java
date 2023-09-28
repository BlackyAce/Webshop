package at.technikumwien.webshop.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;

import at.technikumwien.webshop.config.SecurityConfig;
import at.technikumwien.webshop.dto.UserDTO;
import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.repository.UserRepository;
import at.technikumwien.webshop.service.TokenService;
import at.technikumwien.webshop.service.UserService;

@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
@Import({ UserService.class, SecurityConfig.class })
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllUserWithRoleAdmin() throws Exception {
        List<User> testUsers = new ArrayList<>();
        testUsers.add(new User());
        testUsers.add(new User());
        Gson gson = new Gson();

        when(userRepository.findAll()).thenReturn(testUsers);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/user"))
                .andExpect(status().isOk())
                .andExpect(result -> gson.toJson(testUsers));
    }

    @Test
    public void testGetAllUserWithNoRole() throws Exception {
        List<User> testUsers = new ArrayList<>();
        testUsers.add(new User());
        testUsers.add(new User());
        Gson gson = new Gson();

        when(userRepository.findAll()).thenReturn(testUsers);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/user"))
                .andExpect(status().isForbidden())
                .andExpect(result -> gson.toJson(testUsers));
    }

    @Test
    public void testCreateUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUsername");
        userDTO.setPassword("testPassword");
        userDTO.setAdmin(false); // oder true, je nach Testfall
        userDTO.setSalutation("Mr.");
        userDTO.setFirstname("Test");
        userDTO.setLastname("User");
        userDTO.setEmail("test@test.com");
        userDTO.setStreetadress("Test Street");
        userDTO.setStreetnumber("1");
        userDTO.setCity("Test City");
        userDTO.setPostalcode("12345");
        userDTO.setCountry("Test Country");
        Gson gson = new Gson();
        User user = new User();

        when(userRepository.save(any())).thenReturn(user);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/user/createUser")
                .content(gson.toJson(userDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(result -> gson.toJson(user));
    }


}
