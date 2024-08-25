package org.example.hotelbookingbackend.web.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelbookingbackend.service.RoomService;
import org.example.hotelbookingbackend.web.dto.request.UpsertRoomRequest;
import org.example.hotelbookingbackend.web.dto.response.RoomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/v1/rooms")
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getById(@PathVariable UUID id){
        return roomService.findById(id);
    }

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@RequestBody UpsertRoomRequest request){
        return roomService.create(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable UUID id, @RequestBody UpsertRoomRequest request){
        return roomService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable UUID id){
        return roomService.deleteById(id);
    }

}
