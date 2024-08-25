package org.example.hotelbookingbackend.web.dto.request;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.hotelbookingbackend.entity.Room;
import org.example.hotelbookingbackend.entity.User;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertBookingRequest {

    private Instant startBookingDate;

    private Instant endBookingDate;

    private UUID roomId;

    private UUID userId;

}
