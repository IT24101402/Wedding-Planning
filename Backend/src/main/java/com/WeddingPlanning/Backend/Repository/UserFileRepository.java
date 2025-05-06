package com.WeddingPlanning.Backend.Repository;

import com.WeddingPlanning.Backend.Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class UserFileRepository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserFileRepository.class);
    private static final String FILE_PATH = "users.txt"; // Looks in root project folder
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
        try (BufferedReader reader = new BufferedReader(new FileReader(getFile()))) {
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFile()))) {
            for (User user : users) {
                if (user.getUsername().equals(updatedUser.getUsername())) {
                    writer.write(serialize(updatedUser));
                } else {
                    writer.write(serialize(user));
                }
                writer.newLine();
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
        try (BufferedReader reader = new BufferedReader(new FileReader(getFile()))) {
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

    private File getFile() {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            logger.error("Could not create users.txt", e);
        }
        return file;
    }

    private String serialize(User user) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (IOException e) {
            logger.error("Serialization failed", e);
            return "";
        }
    }

    private User deserialize(String line) {
        try {
            return objectMapper.readValue(line, User.class);
        } catch (IOException e) {
            logger.error("Deserialization failed", e);
            return null;
        }
    }
}
