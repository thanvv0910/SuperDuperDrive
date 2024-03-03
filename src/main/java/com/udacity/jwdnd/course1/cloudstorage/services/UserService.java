package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.model.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.type.UserServiceStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    public UserServiceStatus createUser(User user) {
        if (!isUsernameAvailable(user.getUsername())) return UserServiceStatus.USERNAME_EXITS;
        User updatedUser = hashedPasswordForUser(user);
        userMapper.insert(updatedUser);
        return UserServiceStatus.SUCCESS;
    }

    public UserServiceStatus updateUser(User user) {
        if (isUsernameAvailable(user.getUsername())) return UserServiceStatus.USERNAME_NOT_EXITS;
        User updatedUser = hashedPasswordForUser(user);
        userMapper.update(updatedUser);
        return UserServiceStatus.SUCCESS;
    }

    public UserServiceStatus deleteUser(String userName) {
        if (isUsernameAvailable(userName)) return UserServiceStatus.USERNAME_NOT_EXITS;
        userMapper.delete(userName);
        return UserServiceStatus.SUCCESS;
    }

    private User hashedPasswordForUser(User user) {
        if (user.getSalt() != null) return user;
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

        //Update salt and hashPassword
        return new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName());
    }

    public User getUser(String username) {
        return userMapper.getUser(username);
    }
}