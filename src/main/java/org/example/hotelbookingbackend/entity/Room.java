package org.example.hotelbookingbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue
    private UUID id;

    private String description;

    private Integer number;

    private Double price;

    private Integer maxPeopleCount;


    @ElementCollection
    @CollectionTable(
            name="booked_dates",
            joinColumns=@JoinColumn(name="room_id")
    )
    private Set<Instant> bookedDates = new TreeSet<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    @ToString.Exclude
    private Hotel hotel;

    public void addBookedDates(Set<Instant> dates){
        bookedDates.addAll(dates);
    }

    public void removeBookedDates(Set<Instant> dates){
        bookedDates.removeAll(dates);
    }

}
