package org.example.hotelbookingbackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.hotelbookingbackend.entity.Hotel;
import org.example.hotelbookingbackend.exception.EntityNotFoundException;
import org.example.hotelbookingbackend.mapper.HotelMapper;
import org.example.hotelbookingbackend.repository.HotelRepository;
import org.example.hotelbookingbackend.repository.HotelSpecification;
import org.example.hotelbookingbackend.service.HotelService;
import org.example.hotelbookingbackend.web.dto.request.HotelFilterRequest;
import org.example.hotelbookingbackend.web.dto.request.UpsertHotelRequest;
import org.example.hotelbookingbackend.web.dto.response.HotelResponse;
import org.example.hotelbookingbackend.web.dto.response.ModelListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository repository;

    private final HotelMapper hotelMapper;


    @Override
    public ResponseEntity<ModelListResponse<HotelResponse>> findAll(Pageable pageable) {
        log.info("Find all hotels");
        Page<Hotel> hotels = repository.findAll(pageable);
        return ResponseEntity.ok(                ModelListResponse.<HotelResponse>builder()
                .totalCount(hotels.getTotalElements())
                .data(hotels.stream().map(hotelMapper::hotelToResponse).toList())
                .build()
        );
    }

    @Override
    public ResponseEntity<ModelListResponse<HotelResponse>> filterBy(HotelFilterRequest filter) {
        log.info("Filter hotels with parameters: {}",filter);
        Page<Hotel> hotels = repository.findAll(HotelSpecification.withFilter(filter)
                ,filter.pageRequest());
        return ResponseEntity.ok(
                ModelListResponse.<HotelResponse>builder()
                .totalCount(hotels.getTotalElements())
                .data(hotels.stream().map(hotelMapper::hotelToResponse).toList())
                .build()
        );
    }

    @Override
    public ResponseEntity<HotelResponse> findById(UUID id) {
        log.info("Find hotel by id: {}", id);
        return ResponseEntity.ok(
                hotelMapper.hotelToResponse(
                        repository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Hotel with id {0} not found!", id))))
        );
    }

    @Override
    public ResponseEntity<HotelResponse> create(UpsertHotelRequest entityRequest) {
        log.info("Create hotel: {}", entityRequest);
        Hotel hotel = hotelMapper.upsertRequestToHotel(entityRequest);
        hotel.setRating(0.0);
        hotel.setRatingsCount(0);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        hotelMapper.hotelToResponse(
                                repository.save(hotel)
                        )
                );
    }

    @Override
    @Transactional
    public ResponseEntity<HotelResponse> update(UUID id, UpsertHotelRequest entityRequest) {
        log.info("Update hotel with ID: {}", id);

        var updatedHotel = updateFields(repository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format("Hotel with with ID {0} not found!", id)
                        )),
                hotelMapper.upsertRequestToHotel(entityRequest));
        log.info("Updated hotel: {}", updatedHotel);
        return ResponseEntity.ok(
                hotelMapper.hotelToResponse(
                    repository.save(updatedHotel)
                ));
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<HotelResponse> addRating(UUID id, Double rating) {
        log.info("Add rating to hotel with ID: {}", id);
        var updatedHotel = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Hotel with with ID {0} not found!", id)));
        if (updatedHotel.getRatingsCount() == 0){
            updatedHotel.setRatingsCount(1);
            updatedHotel.setRating(rating);
        } else {
            double totalRating = updatedHotel.getRating() * updatedHotel.getRatingsCount();
            totalRating = totalRating - updatedHotel.getRating() + rating;
            updatedHotel.setRating(totalRating/updatedHotel.getRatingsCount());
            updatedHotel.setRatingsCount(updatedHotel.getRatingsCount() + 1);
        }
        return ResponseEntity.ok(
                hotelMapper.hotelToResponse(
                        repository.save(updatedHotel)
                ));
    }

    protected Hotel updateFields(Hotel oldEntity, Hotel newEntity) {
        if (StringUtils.hasText(newEntity.getName())){
            oldEntity.setName(newEntity.getName());
        }
        if (StringUtils.hasText(newEntity.getAddress())){
            oldEntity.setAddress(newEntity.getAddress());
        }
        if (StringUtils.hasText(newEntity.getHeader())){
            oldEntity.setHeader(newEntity.getHeader());
        }
        if (StringUtils.hasText(newEntity.getCity())){
            oldEntity.setCity(newEntity.getCity());
        }
        if (newEntity.getDistanceFromTheCenter()!=null){
            oldEntity.setDistanceFromTheCenter(newEntity.getDistanceFromTheCenter());
        }
        return oldEntity;
    }

}
