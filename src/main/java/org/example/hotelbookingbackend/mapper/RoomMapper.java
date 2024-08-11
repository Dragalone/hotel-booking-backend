package org.example.hotelbookingbackend.mapper;

import org.example.hotelbookingbackend.entity.Room;
import org.example.hotelbookingbackend.web.dto.request.UpsertRoomRequest;
import org.example.hotelbookingbackend.web.dto.response.RoomResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RoomMapper {

    Room upsertRequestToRoom(UpsertRoomRequest upsertRoomRequest);

    RoomResponse roomToResponse(Room room);

}
