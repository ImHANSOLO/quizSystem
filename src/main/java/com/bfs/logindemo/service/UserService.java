package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.UserDao;
import com.bfs.logindemo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean registerUser(User user) {
        // check if the email exists
        User existing = userDao.findByEmail(user.getEmail());
        if (existing != null) {
            return false;
        }
        // saving a new user
        userDao.save(user);
        return true;
    }

    public User login(String email, String password) {
        User user = userDao.findByEmail(email);
        if (user != null && user.getPassword().equals(password) && user.isActive()) {
            return user;
        }
        return null;
    }

    public boolean changeUserStatus(int userId, boolean isActive) {
        return userDao.updateStatus(userId, isActive) > 0;
    }

    public User findById(int userId) {
        return userDao.findById(userId);
    }

    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }
}