package org.example.hotelbookingbackend.repository;

import org.example.hotelbookingbackend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
}
