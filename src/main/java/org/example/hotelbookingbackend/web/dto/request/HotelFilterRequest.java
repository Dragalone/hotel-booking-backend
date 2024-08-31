package org.example.hotelbookingbackend.web.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelFilterRequest {

    @NotNull
    @Positive
    private Integer pageSize;

    @NotNull
    @PositiveOrZero
    private Integer pageNumber;

    @Nullable
    private UUID id;

    @Nullable
    private String name;

    @Nullable
    private String header;

    @Nullable
    private String city;

    @Nullable
    private String address;

    @Nullable
    private Double distanceFromTheCenter;

    @Nullable
    private Double minRating;

    @Nullable
    private Integer ratingsCount;

    public PageRequest pageRequest() {
        return PageRequest.of(pageNumber, pageSize);
    }

}
