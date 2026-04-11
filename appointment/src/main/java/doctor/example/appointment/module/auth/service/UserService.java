package doctor.example.appointment.module.auth.service;

import doctor.example.appointment.common.jwt.JwtUtil;
import doctor.example.appointment.module.auth.entity.*;
import doctor.example.appointment.module.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    public Users registerUser(Users user){

        // check if email already exists
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("User already exists");
        }

        // hash the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // save user
        return userRepository.save(user);
    }

    public Map<String, Object> login(Users user){

        Optional<Users> optionalUser = userRepository.findByEmail(user.getEmail());

        if(optionalUser.isEmpty()){
            throw new RuntimeException("User not found");
        }

        Users existingUser = optionalUser.get();

        if(!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        // 🔐 Generate JWT
        String token = jwtUtil.generateToken(
                existingUser.getEmail(),
                existingUser.getRole().name()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", existingUser.getRole().name());

        return response;
    }
}
