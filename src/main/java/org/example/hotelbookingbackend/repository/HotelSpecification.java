package org.example.hotelbookingbackend.repository;

import org.example.hotelbookingbackend.entity.Hotel;
import org.example.hotelbookingbackend.web.dto.request.HotelFilterRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.UUID;

public interface HotelSpecification {

    static Specification<Hotel> withFilter(HotelFilterRequest filter) {
        return Specification.where(byId(filter.getId()))
                .and(byName(filter.getName()))
                .and(byHeader(filter.getHeader()))
                .and(byCity(filter.getCity()))
                .and(byAddress(filter.getAddress()))
                .and(byDistanceFromTheCenter(filter.getDistanceFromTheCenter()))
                .and(byMinRating(filter.getMinRating()))
                .and(byRatingsCount(filter.getRatingsCount()));
    }

    static Specification<Hotel> byId(UUID id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(Hotel.Fields.id), id);
        };
    }

    static Specification<Hotel> byName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }

            return criteriaBuilder.like(criteriaBuilder.lower(root.get(Hotel.Fields.name)), "%" + name.toLowerCase() + "%");
        };
    }

    static Specification<Hotel> byHeader(String header) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(header)) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get(Hotel.Fields.header)), "%" + header.toLowerCase() + "%");
        };
    }

    static Specification<Hotel> byCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(city)) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get(Hotel.Fields.city)), "%" + city.toLowerCase() + "%");
        };
    }

    static Specification<Hotel> byAddress(String address) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(address)) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get(Hotel.Fields.address)), "%" + address + "%");
        };
    }

    static Specification<Hotel> byDistanceFromTheCenter(Double distanceFromTheCenter) {
        return (root, query, criteriaBuilder) -> {
            if (distanceFromTheCenter == null) {
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get(Hotel.Fields.distanceFromTheCenter),distanceFromTheCenter);
        };
    }

    static Specification<Hotel> byMinRating(Double minRating) {
        return (root, query, criteriaBuilder) -> {
            if (minRating == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get(Hotel.Fields.rating),minRating);
        };
    }

    static Specification<Hotel> byRatingsCount(Integer ratingsCount) {
        return (root, query, criteriaBuilder) -> {
            if (ratingsCount == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get(Hotel.Fields.ratingsCount),ratingsCount);
        };
    }
}
