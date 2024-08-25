package org.example.hotelbookingbackend.mapper;

import org.example.hotelbookingbackend.entity.Booking;
import org.example.hotelbookingbackend.entity.Hotel;
import org.example.hotelbookingbackend.web.dto.request.UpsertBookingRequest;
import org.example.hotelbookingbackend.web.dto.request.UpsertHotelRequest;
import org.example.hotelbookingbackend.web.dto.response.BookingResponse;
import org.example.hotelbookingbackend.web.dto.response.HotelResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(BookingMapperDelegate.class)
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BookingMapper {

    Booking upsertRequestToBooking(UpsertBookingRequest upsertBookingRequest);

    BookingResponse bookingToResponse(Booking booking);

}
