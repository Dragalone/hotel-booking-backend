package org.example.hotelbookingbackend.mapper;


import org.example.hotelbookingbackend.entity.User;
import org.example.hotelbookingbackend.web.dto.request.UpsertUserRequest;
import org.example.hotelbookingbackend.web.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    User upsertRequestToUser(UpsertUserRequest request);

    UserResponse userToResponse(User user);

}
