package org.example.hotelbookingbackend.mapper;

import org.example.hotelbookingbackend.entity.Hotel;
import org.example.hotelbookingbackend.web.dto.request.UpsertHotelRequest;
import org.example.hotelbookingbackend.web.dto.response.HotelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface HotelMapper {

    Hotel upsertRequestToHotel(UpsertHotelRequest upsertHotelRequest);

    HotelResponse hotelToResponse(Hotel hotel);


}
