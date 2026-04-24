package com.notes.app.service;

import com.notes.app.entity.UserEntity;
import com.notes.app.exception.DuplicateEmailException;
import com.notes.app.exception.UserNotFoundException;
import com.notes.app.model.User;
import com.notes.app.model.UserRequest;
import com.notes.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public User getUserById(UUID id) {
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return toModel(entity);
    }

    @Override
    public User createUser(UserRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        UserEntity entity = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .note(request.getNote())
                .build();

        return toModel(repository.save(entity));
    }

    @Override
    public User updateUser(UUID id, UserRequest request) {

        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!entity.getEmail().equals(request.getEmail())
                && repository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        entity.setNote(request.getNote());

        return toModel(repository.save(entity));
    }

    @Override
    public void deleteUser(UUID id) {

        if (!repository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        repository.deleteById(id);
    }

    private User toModel(UserEntity entity) {
        User user = new User();

        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setEmail(entity.getEmail());
        user.setNote(entity.getNote());

        return user;
    }
}