package com.WeddingPlanning.Backend.Repository;

import com.WeddingPlanning.Backend.Model.User;
import java.util.*;

public interface UserRepository {
    void saveUser(User user);
    User getUserByUsername(String username);
    void updateUser(User user);
    void deleteUser(String username);
    List<User> getAllUsers();
}
