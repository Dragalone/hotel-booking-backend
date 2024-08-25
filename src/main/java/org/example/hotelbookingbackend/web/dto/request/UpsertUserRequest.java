package org.example.hotelbookingbackend.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertUserRequest {

    private String username;

    private String email;

    private String password;
}
