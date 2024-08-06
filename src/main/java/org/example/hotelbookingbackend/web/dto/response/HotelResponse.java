package org.example.hotelbookingbackend.web.dto.response;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponse {

    private UUID id;

    private String name;

    private String header;

    private String city;

    private String address;

    private Double distanceFromTheCenter;

    private Double rating;

    private Integer ratingsCount;

}
