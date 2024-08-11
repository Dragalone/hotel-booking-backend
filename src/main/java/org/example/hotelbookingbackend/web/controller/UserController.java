package org.example.hotelbookingbackend.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.hotelbookingbackend.entity.RoleType;
import org.example.hotelbookingbackend.service.UserService;
import org.example.hotelbookingbackend.web.dto.request.PaginationRequest;
import org.example.hotelbookingbackend.web.dto.request.UpsertUserRequest;
import org.example.hotelbookingbackend.web.dto.response.ModelListResponse;
import org.example.hotelbookingbackend.web.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/v1/users")
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<ModelListResponse<UserResponse>> getAll(@Valid PaginationRequest paginationRequest){
        return userService.findAll(paginationRequest.pageRequest());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id){
        return userService.findById(id);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createEvent(@RequestBody UpsertUserRequest request, @Valid @RequestParam RoleType role){
        return userService.create(request, role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateEvent(@PathVariable UUID id, @RequestBody UpsertUserRequest request){
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id){
        return userService.deleteById(id);
    }


}
