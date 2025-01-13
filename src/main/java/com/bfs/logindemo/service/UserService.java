package com.bfs.logindemo.service;

import com.bfs.logindemo.entity.User;
import com.bfs.logindemo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public boolean registerUser(User user) {
        // check if the email exists
        User existing = userRepo.findByEmail(user.getEmail());
        if (existing != null) {
            return false;
        }
        // saving a new user
        userRepo.save(user);
        return true;
    }

    public User login(String email, String password) {
        User user = userRepo.findByEmail(email);
        if (user != null && user.getPassword().equals(password) && user.isActive()) {
            return user;
        }
        return null;
    }

    public boolean changeUserStatus(int userId, boolean isActive) {
        // replicate userDao.updateStatus => userRepo.updateStatus
        // if you used a direct query approach:
        int rows = userRepo.updateStatus(userId, isActive);
        return (rows > 0);
    }

    public User findById(int userId) {
        return userRepo.findById(userId).orElse(null);
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }
}
