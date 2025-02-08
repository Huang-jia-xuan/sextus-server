package jnu.edu.service;

import jnu.edu.dao.UserRepository;
import jnu.edu.entity.User;
import jnu.edu.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User user = new User();
        user.setUsername(username);
//        user.setPassword(passwordEncoder.encode(password));
        user.setPassword(password);
        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
//        if (userOptional.isEmpty() ||
//                !(password.equals(userOptional.get().getPassword()))) {
//            throw new RuntimeException("Invalid credentials");
//        }
//        return jwtUtil.generateToken(username);
        return "Login successful";
    }
}
