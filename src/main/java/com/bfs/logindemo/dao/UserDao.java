package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

// Repository / DAO layer
// We are using some mocked (fake) data here.
// In your project, you need to use mySQL database, configure the data source.
@Repository
public class UserDao {

    private static final List<User> users;

    static {
        users = new ArrayList<>();
        users.add(new User(1, "user1", "pass1"));
        users.add(new User(2, "user2", "pass2"));
        users.add(new User(3, "user3", "pass3"));
    }

    public void createNewUser(User user) {
        users.add(user);
    }

    public List<User> getAllUsers() {
        return users;
    }

}
