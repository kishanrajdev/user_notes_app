package com.notes.app.service;

import com.notes.app.model.User;
import com.notes.app.model.UserRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(UUID id);

    User createUser(UserRequest request);

    User updateUser(UUID id, UserRequest request);

    void deleteUser(UUID id);
}