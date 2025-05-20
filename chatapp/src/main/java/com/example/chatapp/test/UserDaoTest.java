package com.example.chatapp.test;


import com.example.chatapp.dao.UserDao;
import com.example.chatapp.model.User;

public class UserDaoTest {

    private UserDao userDao;

    public void setUp() {
        userDao = new UserDao();
    }
   

    public void testInsertAndFindByUsername() {
        String username = "user_" + System.currentTimeMillis();
        String password = "pass";
        String displayName = "テスト表示名";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setDisplayName(displayName);

        boolean result = userDao.insertUser(user);
        if (!result) {
            throw new RuntimeException("insertUser failed");
        }

        User found = userDao.findByUsername(username);
        if (found == null) {
            throw new RuntimeException("User not found");
        }

        if (!found.getDisplayName().equals(displayName)) {
            throw new RuntimeException("Display name mismatch");
        }

        if (!found.getPassword().equals(password)) {
            throw new RuntimeException("Password mismatch");
        }

        System.out.println("Test passed: insertUser and findByUsername");
    }
}

