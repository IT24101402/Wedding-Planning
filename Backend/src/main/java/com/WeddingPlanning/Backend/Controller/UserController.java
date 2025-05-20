package com.WeddingPlanning.Backend.Controller;

import com.WeddingPlanning.Backend.Model.User;
import com.WeddingPlanning.Backend.Service.UserService;
import com.WeddingPlanning.Backend.Service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService = new UserServiceImpl();
    private final Path filePath = Paths.get("src/main/resources/static/users.txt");

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            String csvLine = String.join(",",
                    user.getUsername().trim(),
                    user.getEmail().trim(),
                    user.getPassword().trim(),
                    user.getPhone().trim()
            );

            Files.write(filePath, Collections.singletonList(csvLine),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);

            return ResponseEntity.ok("User registered successfully!");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving user.");
        }
    }

    // ✅ LOGIN
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    // ✅ UPDATE
    @PutMapping("/update")
    public boolean update(@RequestBody User user) {
        return userService.updateUser(user);
    }

    // ✅ DELETE
    @DeleteMapping("/{username}")
    public boolean delete(@PathVariable String username) {
        return userService.deleteUser(username);
    }
}