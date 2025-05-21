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
import java.util.List;
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
            Path filePath = Paths.get("src/main/resources/static/users.txt");

            if (!Files.exists(filePath)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "User file not found"));
            }

            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length != 4) continue;

                String fileUsername = parts[0].trim();
                String fileEmail = parts[1].trim();
                String filePassword = parts[2].trim();
                String filePhone = parts[3].trim();

                boolean matchesUsernameOrEmail = user.getUsername().equals(fileUsername) || user.getUsername().equals(fileEmail);
                boolean matchesPassword = user.getPassword().equals(filePassword);

                if (matchesUsernameOrEmail && matchesPassword) {
                    User matchedUser = new User(null, fileUsername, fileEmail, filePassword, filePhone);
                    return ResponseEntity.ok(matchedUser);
                }
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid username or password"));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Login failed: " + e.getMessage()));
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