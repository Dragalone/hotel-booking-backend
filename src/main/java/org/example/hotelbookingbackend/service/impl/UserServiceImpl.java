package org.example.hotelbookingbackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.hotelbookingbackend.entity.RoleType;
import org.example.hotelbookingbackend.entity.User;
import org.example.hotelbookingbackend.exception.AlreadyExistsException;
import org.example.hotelbookingbackend.exception.EntityNotFoundException;
import org.example.hotelbookingbackend.mapper.UserMapper;
import org.example.hotelbookingbackend.repository.UserRepository;
import org.example.hotelbookingbackend.service.UserService;

import org.example.hotelbookingbackend.web.dto.request.UpsertUserRequest;

import org.example.hotelbookingbackend.web.dto.response.ModelListResponse;

import org.example.hotelbookingbackend.web.dto.response.UserResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;




@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UserMapper userMapper;


    @Override
    public ResponseEntity<UserResponse> findByUsername(String username) {
        log.info("Find user by username: {}", username);
        return ResponseEntity.ok(
                userMapper.userToResponse(
                        repository.findByUsername(username)
                                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("User with username {0} not found!", username))))
        );
    }

    @Override
    public ResponseEntity<UserResponse> findByEmail(String email) {
        log.info("Find user by email: {}", email);
        return ResponseEntity.ok(
                userMapper.userToResponse(
                        repository.findByEmail(email)
                                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("User with email {0} not found!", email))))
        );
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public ResponseEntity<UserResponse> findById(UUID userId) {
        log.info("Find user by id: {}", userId);
        return ResponseEntity.ok(
                userMapper.userToResponse(
                        repository.findById(userId)
                                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("User with id {0} not found!", userId))))
        );
    }

    @Override
    public ResponseEntity<UserResponse> create(UpsertUserRequest entityRequest, RoleType role) {
        if (existsByEmail(entityRequest.getEmail())) {
            throw new AlreadyExistsException(MessageFormat.format("User with email {0} already exists!", entityRequest.getEmail()));
        }
        if (existsByUsername(entityRequest.getUsername())) {
            throw new AlreadyExistsException(MessageFormat.format("User with email {0} already exists!", entityRequest.getEmail()));
        }
        log.info("Create user: {}", entityRequest);
        User user = userMapper.upsertRequestToUser(entityRequest);
        user.addRole(role);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        userMapper.userToResponse(
                                repository.save(user)
                        )
                );
    }

    @Override
    public ResponseEntity<ModelListResponse<UserResponse>> findAll(Pageable pageable) {
        log.info("Find all users");
        Page<User> users = repository.findAll(pageable);
        return ResponseEntity.ok(
                ModelListResponse.<UserResponse>builder()
                .totalCount(users.getTotalElements())
                .data(users.stream().map(userMapper::userToResponse).toList())
                .build()
        );
    }

    @Override
    @Transactional
    public ResponseEntity<UserResponse> update(UUID id, UpsertUserRequest entityRequest) {
        log.info("Update user with ID: {}", id);

        var updatedUser = updateFields(repository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format("User with with ID {0} not found!", id)
                        )),
                userMapper.upsertRequestToUser(entityRequest));
        log.info("Updated user: {}", updatedUser);
        return ResponseEntity.ok(
                userMapper.userToResponse(
                        repository.save(updatedUser)
                ));
    }


    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    public User updateFields(User oldEntity, User newEntity) {
        if (!Objects.equals(oldEntity.getUsername(), newEntity.getUsername()) && existsByUsername(newEntity.getUsername())) {
            throw new AlreadyExistsException(
                    MessageFormat.format("User with username {0} already exists!",  newEntity.getUsername())
            );
        } else if (!Objects.equals(oldEntity.getUsername(), newEntity.getUsername())) {
            oldEntity.setUsername(newEntity.getUsername());
        }

        if (!Objects.equals(oldEntity.getEmail(), newEntity.getEmail()) && existsByEmail(newEntity.getEmail())) {
            throw new AlreadyExistsException(
                    MessageFormat.format("User with email {0} already exists!",  newEntity.getUsername())
            );
        } else if (!Objects.equals(oldEntity.getEmail(), newEntity.getEmail())) {
            oldEntity.setEmail(newEntity.getEmail());
        }

        return oldEntity;
    }
}
