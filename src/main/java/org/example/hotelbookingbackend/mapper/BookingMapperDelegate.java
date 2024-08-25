package org.example.hotelbookingbackend.mapper;

import org.example.hotelbookingbackend.entity.Booking;
import org.example.hotelbookingbackend.web.dto.request.UpsertBookingRequest;
import org.example.hotelbookingbackend.web.dto.response.BookingResponse;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BookingMapperDelegate implements BookingMapper{

    @Autowired
    private BookingMapper delegate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoomMapper roomMapper;


    @Override
    public BookingResponse bookingToResponse(Booking booking){
        BookingResponse bookingResponse = delegate.bookingToResponse(booking);
        bookingResponse.setUserResponse(userMapper.userToResponse(booking.getUser()));
        bookingResponse.setRoomResponse(roomMapper.roomToResponse(booking.getRoom()));
        return bookingResponse;
    }
}
