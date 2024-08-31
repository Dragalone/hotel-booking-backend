package org.example.hotelbookingbackend.web.controller;



import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.example.hotelbookingbackend.service.HotelService;
import org.example.hotelbookingbackend.web.dto.request.PaginationRequest;
import org.example.hotelbookingbackend.web.dto.request.UpsertHotelRequest;
import org.example.hotelbookingbackend.web.dto.response.HotelResponse;
import org.example.hotelbookingbackend.web.dto.response.ModelListResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HotelResponse> createHotel(@RequestBody UpsertHotelRequest request){
        return hotelService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HotelResponse> updateHotel(@PathVariable UUID id, @RequestBody UpsertHotelRequest request){
        return hotelService.update(id, request);
    }

    @PutMapping("/rating/{id}")
    public ResponseEntity<HotelResponse> addRating(@PathVariable UUID id, @RequestParam @Min(0) @Max(5) Double rating){
        return hotelService.addRating(id, rating);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteHotel(@PathVariable UUID id){
        return hotelService.deleteById(id);
    }

}
