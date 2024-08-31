package org.example.hotelbookingbackend.service;

import org.example.hotelbookingbackend.web.dto.request.HotelFilterRequest;
import org.example.hotelbookingbackend.web.dto.request.UpsertHotelRequest;
import org.example.hotelbookingbackend.web.dto.response.HotelResponse;
import org.example.hotelbookingbackend.web.dto.response.ModelListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;


public interface HotelService {

    ResponseEntity<ModelListResponse<HotelResponse>> findAll(Pageable pageable);

    ResponseEntity<ModelListResponse<HotelResponse>> filterBy(HotelFilterRequest filter);

    ResponseEntity<HotelResponse> findById(UUID id);

    ResponseEntity<HotelResponse> create(UpsertHotelRequest entityRequest);

    ResponseEntity<HotelResponse> update(UUID id, UpsertHotelRequest entityRequest);

    ResponseEntity<Void> deleteById(UUID id);

     ResponseEntity<HotelResponse> addRating(UUID id, Double rating);


}
