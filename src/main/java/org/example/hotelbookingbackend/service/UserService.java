package org.example.hotelbookingbackend.service;

import org.example.hotelbookingbackend.entity.RoleType;
import org.example.hotelbookingbackend.web.dto.request.UpsertUserRequest;
import org.example.hotelbookingbackend.web.dto.response.ModelListResponse;
import org.example.hotelbookingbackend.web.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserService {

    ResponseEntity<UserResponse> findByUsername(String username);

    ResponseEntity<UserResponse> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    ResponseEntity<UserResponse> findById(UUID userId);

    ResponseEntity<UserResponse> create(UpsertUserRequest entityRequest, RoleType role);

    ResponseEntity<ModelListResponse<UserResponse>> findAll(Pageable pageable);
    ResponseEntity<UserResponse> update (UUID id, UpsertUserRequest entityRequest);

    ResponseEntity<Void> deleteById(UUID id);

}
