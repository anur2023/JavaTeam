package doctor.example.appointment.module.auth.service;

import doctor.example.appointment.module.auth.entity.*;
import doctor.example.appointment.module.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Users registerUser( Users user){
        if (userRepository.findByEmail(user.getEmail())){
            new RuntimeException("User already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword());
        return userRepository.save(user);
    }

    public Users login(Users user){
        Optional<Users> optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isEmpty()){
            throw new RuntimeException("User not exist");
        }
        Users existUser = optionalUser.get();
        return existUser;
    }
}
