package org.example.hotelbookingbackend.service;

import org.example.hotelbookingbackend.web.dto.request.UpsertRoomRequest;
import org.example.hotelbookingbackend.web.dto.response.ModelListResponse;
import org.example.hotelbookingbackend.web.dto.response.RoomResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface RoomService {

    ResponseEntity<RoomResponse> findById(UUID id);

    ResponseEntity<RoomResponse> create(UpsertRoomRequest entityRequest);

    ResponseEntity<RoomResponse> update(UUID id, UpsertRoomRequest entityRequest);

    ResponseEntity<ModelListResponse<RoomResponse>> findAll(Pageable pageable);

    ResponseEntity<Void> deleteById(UUID id);

}
