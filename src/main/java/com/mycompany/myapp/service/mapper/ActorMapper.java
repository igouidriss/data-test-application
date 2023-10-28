package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Actor;
import com.mycompany.myapp.service.dto.ActorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Actor} and its DTO {@link ActorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ActorMapper extends EntityMapper<ActorDTO, Actor> {}
