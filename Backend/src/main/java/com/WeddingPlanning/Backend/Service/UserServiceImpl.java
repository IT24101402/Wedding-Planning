package com.WeddingPlanning.Backend.Service;

import com.WeddingPlanning.Backend.Model.User;
import com.WeddingPlanning.Backend.Repository.*;

public class UserServiceImpl extends UserService {
    private final UserRepository userRepository = new UserFileRepository();

    @Override
    public String register(User user) {
        if (userRepository.getUserByUsername(user.getUsername()) != null)
            return "Username already exists!";
        userRepository.saveUser(user);
        return "User registered successfully!";
    }

    @Override
    public User login(String username, String password) {
        User user = userRepository.getUserByUsername(username);
        return (user != null && user.getPassword().equals(password)) ? user : null;
    }

    @Override
    public boolean updateUser(User user) {
        userRepository.updateUser(user);
        return true;
    }

    @Override
    public boolean deleteUser(String username) {
        userRepository.deleteUser(username);
        return true;
    }

}
