package doctor.example.appointment.module.auth.controller;

import doctor.example.appointment.module.auth.entity.Users;
import doctor.example.appointment.module.auth.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pharmacy")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public Users register(@RequestBody Users user)
    {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public Users login(@RequestBody Users user){
        return userService.login(user);
    }
}
