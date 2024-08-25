package org.example.hotelbookingbackend.service;

import org.example.hotelbookingbackend.web.dto.request.UpsertBookingRequest;
import org.example.hotelbookingbackend.web.dto.request.UpsertHotelRequest;
import org.example.hotelbookingbackend.web.dto.response.BookingResponse;
import org.example.hotelbookingbackend.web.dto.response.HotelResponse;
import org.example.hotelbookingbackend.web.dto.response.ModelListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface BookingService {

    ResponseEntity<ModelListResponse<BookingResponse>> findAll(Pageable pageable);

    ResponseEntity<ModelListResponse<BookingResponse>> findAllByRoomId(Pageable pageable, UUID roomId);

    ResponseEntity<BookingResponse> findById(UUID id);

    ResponseEntity<BookingResponse> create(UpsertBookingRequest entityRequest);

    ResponseEntity<BookingResponse> update(UUID id, UpsertBookingRequest entityRequest);

    ResponseEntity<Void> deleteById(UUID id);

}
