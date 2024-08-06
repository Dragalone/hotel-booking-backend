package org.example.hotelbookingbackend.repository;

import org.example.hotelbookingbackend.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HotelRepository extends JpaRepository<Hotel, UUID> {



}
