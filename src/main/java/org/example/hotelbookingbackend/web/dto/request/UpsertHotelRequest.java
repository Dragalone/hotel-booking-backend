package org.example.hotelbookingbackend.web.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertHotelRequest {

    private String name;

    private String header;

    private String city;

    private String address;

    private Double distanceFromTheCenter;


}
