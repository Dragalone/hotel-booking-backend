package org.example.hotelbookingbackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.hotelbookingbackend.entity.Booking;
import org.example.hotelbookingbackend.entity.Hotel;
import org.example.hotelbookingbackend.entity.Room;
import org.example.hotelbookingbackend.exception.AlreadyBookedException;
import org.example.hotelbookingbackend.exception.EntityNotFoundException;
import org.example.hotelbookingbackend.mapper.BookingMapper;
import org.example.hotelbookingbackend.repository.BookingRepository;
import org.example.hotelbookingbackend.repository.RoomRepository;
import org.example.hotelbookingbackend.repository.UserRepository;
import org.example.hotelbookingbackend.service.BookingService;
import org.example.hotelbookingbackend.service.HotelService;
import org.example.hotelbookingbackend.util.DateUtils;
import org.example.hotelbookingbackend.web.dto.request.UpsertBookingRequest;
import org.example.hotelbookingbackend.web.dto.request.UpsertHotelRequest;
import org.example.hotelbookingbackend.web.dto.response.BookingResponse;
import org.example.hotelbookingbackend.web.dto.response.HotelResponse;
import org.example.hotelbookingbackend.web.dto.response.ModelListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repository;

    private final BookingMapper bookingMapper;

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;



    @Override
    public ResponseEntity<ModelListResponse<BookingResponse>> findAll(Pageable pageable) {
        log.info("Find all bookings");
        Page<Booking> bookings = repository.findAll(pageable);
        return ResponseEntity.ok(
                ModelListResponse.<BookingResponse>builder()
                .totalCount(bookings.getTotalElements())
                .data(bookings.stream().map(bookingMapper::bookingToResponse).toList())
                .build()
        );
    }

    @Override
    public ResponseEntity<ModelListResponse<BookingResponse>> findAllByRoomId(Pageable pageable, UUID roomId) {
        log.info("Find all bookings by roomId: {}", roomId);
        Page<Booking> bookings = repository.findAllByRoomId(pageable,roomId);
        return ResponseEntity.ok(
                ModelListResponse.<BookingResponse>builder()
                        .totalCount(bookings.getTotalElements())
                        .data(bookings.stream().map(bookingMapper::bookingToResponse).toList())
                        .build()
        );
    }


    @Override
    public ResponseEntity<BookingResponse> findById(UUID id) {
        log.info("Find booking by id: {}", id);
        return ResponseEntity.ok(
                bookingMapper.bookingToResponse(
                        repository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Booking with id {0} not found!", id))))
        );
    }

    @Override
    public ResponseEntity<BookingResponse> create(UpsertBookingRequest entityRequest) {
        log.info("Create booking: {}", entityRequest);
        Booking booking = bookingMapper.upsertRequestToBooking(entityRequest);
        Room room = roomRepository.findById(entityRequest.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Room with id {0} not found!", entityRequest.getRoomId())));
        if (DateUtils.isInInterval(room.getBookedDates(),booking.getStartBookingDate(),booking.getEndBookingDate())){
            throw new AlreadyBookedException("The room for this date is already booked!");
        } else {
            room.addBookedDates(DateUtils.getInstantsBetween(booking.getStartBookingDate(), booking.getEndBookingDate()));
        }
        roomRepository.save(room);
        booking.setRoom(room);
        booking.setUser(
                userRepository.findById(entityRequest.getUserId())
                        .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("User with id {0} not found!", entityRequest.getUserId())))
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        bookingMapper.bookingToResponse(
                                repository.save(booking)
                        )
                );
    }

    @Override
    @Transactional
    public ResponseEntity<BookingResponse> update(UUID id, UpsertBookingRequest entityRequest) {
        log.info("Update booking with ID: {}", id);

        Booking upsertBooking = bookingMapper.upsertRequestToBooking(entityRequest);
        Booking bookingForUpdate = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Booking with with ID {0} not found!", id)
                ));

        var updatedBooking = updateFields(bookingForUpdate,
                upsertBooking);

        Room room = updatedBooking.getRoom();
        room.removeBookedDates(DateUtils.getInstantsBetween(bookingForUpdate.getStartBookingDate(), bookingForUpdate.getEndBookingDate()));
        room.addBookedDates(DateUtils.getInstantsBetween(updatedBooking.getStartBookingDate(),updatedBooking.getEndBookingDate()));
        roomRepository.save(room);
        updatedBooking.setRoom(room);

        log.info("Updated booking: {}", updatedBooking);
        return ResponseEntity.ok(
                bookingMapper.bookingToResponse(
                        repository.save(updatedBooking)
                ));
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        Booking booking = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Booking with id {0} not found!", id)));
        Room room = booking.getRoom();

        room.removeBookedDates(DateUtils.getInstantsBetween(booking.getStartBookingDate(), booking.getEndBookingDate()));

        roomRepository.save(room);
        booking.setRoom(room);

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    protected Booking updateFields(Booking oldEntity, Booking newEntity) {
        if (newEntity.getEndBookingDate()!=null){
            oldEntity.setEndBookingDate(newEntity.getEndBookingDate());
        }
        if (newEntity.getStartBookingDate()!=null){
            oldEntity.setStartBookingDate(newEntity.getStartBookingDate());
        }
        return oldEntity;
    }

}
