package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Period;
import com.mycompany.myapp.domain.Room;
import com.mycompany.myapp.service.dto.PeriodDTO;
import com.mycompany.myapp.service.dto.RoomDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Period} and its DTO {@link PeriodDTO}.
 */
@Mapper(componentModel = "spring")
public interface PeriodMapper extends EntityMapper<PeriodDTO, Period> {
    @Mapping(target = "room", source = "room", qualifiedByName = "roomId")
    PeriodDTO toDto(Period s);

    @Named("roomId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RoomDTO toDtoRoomId(Room room);
}
