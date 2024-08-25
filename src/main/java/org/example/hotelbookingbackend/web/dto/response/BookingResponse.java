package org.example.hotelbookingbackend.web.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private UUID id;

    private Instant startBookingDate;

    private Instant endBookingDate;

    private RoomResponse roomResponse;

    private UserResponse userResponse;

}
