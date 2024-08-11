package org.example.hotelbookingbackend.web.controller;



import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.hotelbookingbackend.service.HotelService;
import org.example.hotelbookingbackend.web.dto.request.PaginationRequest;
import org.example.hotelbookingbackend.web.dto.request.UpsertHotelRequest;
import org.example.hotelbookingbackend.web.dto.response.HotelResponse;
import org.example.hotelbookingbackend.web.dto.response.ModelListResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RequestMapping("api/v1/hotels")
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<ModelListResponse<HotelResponse>> getAll(@Valid PaginationRequest paginationRequest){
        return hotelService.findAll(paginationRequest.pageRequest());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getById(@PathVariable UUID id){
        return hotelService.findById(id);
    }

    @PostMapping
    public ResponseEntity<HotelResponse> createEvent(@RequestBody UpsertHotelRequest request){
        return hotelService.create(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> updateEvent(@PathVariable UUID id, @RequestBody UpsertHotelRequest request){
        return hotelService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id){
        return hotelService.deleteById(id);
    }

}
