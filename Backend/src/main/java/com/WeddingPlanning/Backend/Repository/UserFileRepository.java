package com.WeddingPlanning.Backend.Repository;

import com.WeddingPlanning.Backend.Model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.*;

public class UserFileRepository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserFileRepository.class);
    private static final String FILE_PATH = "users.txt";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public synchronized void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFile(), true))) {
            writer.write(serialize(user));
            writer.newLine();
        } catch (IOException e) {
            logger.error("Error saving user: {}", user.getUsername(), e);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(FILE_PATH).getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = deserialize(line);
                if (user != null && user.getUsername().equals(username)) {
                    return user;
                }
            }
        } catch (IOException e) {
            logger.error("Error reading user by username: {}", username, e);
        }
        return null;
    }

    @Override
    public synchronized void updateUser(User updatedUser) {
        List<User> users = getAllUsers();
        boolean userUpdated = false;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFile()))) {
            for (User user : users) {
                if (user.getUsername().equals(updatedUser.getUsername()) && !userUpdated) {
                    writer.write(serialize(updatedUser));
                    userUpdated = true;
                } else {
                    writer.write(serialize(user));
                }
                writer.newLine();
            }
            if (!userUpdated) {
                logger.warn("User with username {} not found for update.", updatedUser.getUsername());
            }
        } catch (IOException e) {
            logger.error("Error updating user: {}", updatedUser.getUsername(), e);
        }
    }

    @Override
    public synchronized void deleteUser(String username) {
        List<User> users = getAllUsers();
        users.removeIf(user -> user.getUsername().equals(username));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFile()))) {
            for (User user : users) {
                writer.write(serialize(user));
                writer.newLine();
            }
        } catch (IOException e) {
            logger.error("Error deleting user: {}", username, e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(FILE_PATH).getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = deserialize(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            logger.error("Error reading all users", e);
        }
        return users;
    }

    // Helper method to get the file path
    private File getFile() {
        try {
            return new ClassPathResource(FILE_PATH).getFile();
        } catch (IOException e) {
            logger.error("Error getting the file path: {}", FILE_PATH, e);
            return new File(FILE_PATH);
        }
    }

    private String serialize(User user) {
        try {
            return objectMapper.writeValueAsString(user); // Using Jackson for serialization
        } catch (IOException e) {
            logger.error("Error serializing user: {}", user.getUsername(), e);
            return "";
        }
    }

    private User deserialize(String line) {
        try {
            return objectMapper.readValue(line, User.class); // Using Jackson for deserialization
        } catch (IOException e) {
            logger.error("Error deserializing line: {}", line, e);
            return null;
        }
    }
}
