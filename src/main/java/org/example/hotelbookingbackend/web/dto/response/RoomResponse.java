package org.example.hotelbookingbackend.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.example.hotelbookingbackend.entity.Hotel;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {

    private UUID id;

    private String description;

    private Integer number;

    private Double price;

    private Integer maxPeopleCount;

    private Instant startBookingDate;

    private Instant endBookingDate;

    private HotelResponse hotel;

}
