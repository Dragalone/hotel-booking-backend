package org.example.hotelbookingbackend.repository;

import org.example.hotelbookingbackend.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    Page<Booking>  findAllByRoomId(Pageable pageable, UUID roomId);
}
