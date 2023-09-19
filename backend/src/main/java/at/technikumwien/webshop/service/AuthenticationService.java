package at.technikumwien.webshop.service;

import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    @Autowired
    public AuthenticationService(TokenService tokenService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.tokenService =  tokenService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    public String login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException();
        }
        return tokenService.generateToken(user);
    }
}
