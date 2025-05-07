package com.WeddingPlanning.Backend.Controller;

import com.WeddingPlanning.Backend.Model.*;
import com.WeddingPlanning.Backend.Service.*;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


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
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User loggedInUser = userService.login(user.getUsername(), user.getPassword());
            if (loggedInUser != null) {
                return ResponseEntity.ok(loggedInUser);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Invalid credentials"));
            }
        } catch (Exception e) {
            // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
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
