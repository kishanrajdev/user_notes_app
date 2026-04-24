package com.notes.app.controller;

import com.notes.app.api.UserApi;
import com.notes.app.model.User;
import com.notes.app.model.UserRequest;
import com.notes.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController implements UserApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Override
    public ResponseEntity<User> getUserById(UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Override
    public ResponseEntity<User> createUser(@Valid UserRequest userRequest) {
        return ResponseEntity.status(201)
                .body(userService.createUser(userRequest));
    }

    @Override
    public ResponseEntity<User> updateUser(UUID id, @Valid UserRequest userRequest) {
        return ResponseEntity.ok(
                userService.updateUser(id, userRequest)
        );
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}