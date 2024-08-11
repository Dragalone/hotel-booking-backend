package org.example.hotelbookingbackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.hotelbookingbackend.entity.Hotel;
import org.example.hotelbookingbackend.entity.Room;
import org.example.hotelbookingbackend.exception.EntityNotFoundException;
import org.example.hotelbookingbackend.mapper.RoomMapper;
import org.example.hotelbookingbackend.repository.RoomRepository;
import org.example.hotelbookingbackend.service.RoomService;
import org.example.hotelbookingbackend.web.dto.request.UpsertRoomRequest;
import org.example.hotelbookingbackend.web.dto.response.RoomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomMapper roomMapper;

    private final RoomRepository repository;

    @Override
    public ResponseEntity<RoomResponse> findById(UUID id) {
        log.info("Find room by id: {}", id);
        return ResponseEntity.ok(
                roomMapper.roomToResponse(
                        repository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Room with id {0} not found!", id))))
        );
    }

    @Override
    public ResponseEntity<RoomResponse> create(UpsertRoomRequest entityRequest) {
        log.info("Create room: {}", entityRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        roomMapper.roomToResponse(
                                repository.save(roomMapper.upsertRequestToRoom(entityRequest))
                        )
                );
    }

    @Override
    public ResponseEntity<RoomResponse> update(UUID id, UpsertRoomRequest entityRequest) {
        log.info("Update room with ID: {}", id);

        var updatedRoom = updateFields(repository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format("Room with with ID {0} not found!", id)
                        )),
                roomMapper.upsertRequestToRoom(entityRequest));
        log.info("Updated room: {}", updatedRoom);
        return ResponseEntity.ok(
                roomMapper.roomToResponse(
                        repository.save(updatedRoom)
                ));
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    protected Room updateFields(Room oldEntity, Room newEntity) {
        if (StringUtils.hasText(newEntity.getDescription())){
            oldEntity.setDescription(newEntity.getDescription());
        }
        if (newEntity.getNumber()!=null){
            oldEntity.setNumber(newEntity.getNumber());
        }
        if (newEntity.getPrice()!=null){
            oldEntity.setPrice(newEntity.getPrice());
        }
        if (newEntity.getStartBookingDate()!=null){
            oldEntity.setStartBookingDate(newEntity.getStartBookingDate());
        }
        if (newEntity.getEndBookingDate()!=null){
            oldEntity.setEndBookingDate(newEntity.getEndBookingDate());
        }
        if (newEntity.getMaxPeopleCount()!=null){
            oldEntity.setMaxPeopleCount(newEntity.getMaxPeopleCount());
        }
        return oldEntity;
    }
}
