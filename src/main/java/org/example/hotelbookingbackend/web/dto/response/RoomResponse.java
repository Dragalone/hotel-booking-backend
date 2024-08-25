package org.example.hotelbookingbackend.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.example.hotelbookingbackend.entity.Hotel;

import java.time.Instant;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {

    private UUID id;

    private String description;

    private Integer number;

    private Double price;

    private Integer maxPeopleCount;

    private TreeSet<Instant> bookedDates = new TreeSet<>();

    private HotelResponse hotel;

}
