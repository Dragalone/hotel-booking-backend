package org.example.hotelbookingbackend.web.dto.request;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.hotelbookingbackend.entity.Hotel;

import java.time.Instant;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertRoomRequest {

    private String description;

    private Integer number;

    private Double price;

    private Integer maxPeopleCount;

    private Instant startBookingDate;

    private Instant endBookingDate;

    private UUID hotelId;

}
