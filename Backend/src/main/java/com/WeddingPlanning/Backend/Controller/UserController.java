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
                    User matchedUser = new User(fileUsername, fileEmail, filePassword, filePhone);
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
    public ResponseEntity<String> update(@RequestBody User updatedUser) {
        try {
            if (!Files.exists(filePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User file not found.");
            }

            List<String> lines = Files.readAllLines(filePath);
            boolean userFound = false;

            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts.length != 4) continue;

                String username = parts[0].trim();
                if (username.equals(updatedUser.getUsername())) {
                    lines.set(i, String.join(",",
                            updatedUser.getUsername().trim(),
                            updatedUser.getEmail().trim(),
                            updatedUser.getPassword().trim(),
                            updatedUser.getPhone().trim()
                    ));
                    userFound = true;
                    break;
                }
            }

            if (userFound) {
                Files.write(filePath, lines, StandardOpenOption.TRUNCATE_EXISTING);
                return ResponseEntity.ok("User profile updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found.");
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating user profile: " + e.getMessage());
        }
    }

    // ✅ DELETE
    @DeleteMapping("/{username}")
    public boolean delete(@PathVariable String username) {
        return userService.deleteUser(username);
    }

    // ✅ GET PROFILE
    @GetMapping("/user/profile")
    public ResponseEntity<User> getUserProfile(@RequestParam String username) {
        try {
            if (!Files.exists(filePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length != 4) continue;

                String fileUsername = parts[0].trim();
                String fileEmail = parts[1].trim();
                String filePassword = parts[2].trim();
                String filePhone = parts[3].trim();

                if (fileUsername.equals(username.trim())) {
                    User user = new User(fileUsername, fileEmail, filePassword, filePhone);
                    return ResponseEntity.ok(user);
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}