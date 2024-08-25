package org.example.hotelbookingbackend.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.hotelbookingbackend.service.BookingService;
import org.example.hotelbookingbackend.web.dto.request.PaginationRequest;
import org.example.hotelbookingbackend.web.dto.request.UpsertBookingRequest;
import org.example.hotelbookingbackend.web.dto.response.BookingResponse;
import org.example.hotelbookingbackend.web.dto.response.ModelListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/v1/booking")
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<ModelListResponse<BookingResponse>> getAll(@Valid PaginationRequest paginationRequest){
        return bookingService.findAll(paginationRequest.pageRequest());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getById(@PathVariable UUID id){
        return bookingService.findById(id);
    }


    @GetMapping("/room/{id}")
    public ResponseEntity<ModelListResponse<BookingResponse>> getAllByRoomId(@Valid PaginationRequest paginationRequest, @RequestParam UUID roomId){
        return bookingService.findAllByRoomId(paginationRequest.pageRequest(),roomId);
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody UpsertBookingRequest request){
        return bookingService.create(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> updateBooking(@RequestBody UpsertBookingRequest request, @PathVariable UUID id){
        return bookingService.update(id,request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable UUID id){
        return bookingService.deleteById(id);
    }

}
