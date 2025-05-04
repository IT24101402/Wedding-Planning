package com.WeddingPlanning.Backend.Controller;

import com.WeddingPlanning.Backend.Model.*;
import com.WeddingPlanning.Backend.Service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")

public class UserController {
    private final UserService userService = new UserServiceImpl();

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user.getUsername(), user.getPassword());
    }

    @PutMapping("/update")
    public boolean update(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{username}")
    public boolean delete(@PathVariable String username) {
        return userService.deleteUser(username);
    }



}
