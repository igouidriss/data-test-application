package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Actor;
import com.mycompany.myapp.domain.Casting;
import com.mycompany.myapp.domain.Movie;
import com.mycompany.myapp.service.dto.ActorDTO;
import com.mycompany.myapp.service.dto.CastingDTO;
import com.mycompany.myapp.service.dto.MovieDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Casting} and its DTO {@link CastingDTO}.
 */
@Mapper(componentModel = "spring")
public interface CastingMapper extends EntityMapper<CastingDTO, Casting> {
    @Mapping(target = "movie", source = "movie", qualifiedByName = "movieId")
    @Mapping(target = "actors", source = "actors", qualifiedByName = "actorIdSet")
    CastingDTO toDto(Casting s);

    @Mapping(target = "removeActors", ignore = true)
    Casting toEntity(CastingDTO castingDTO);

    @Named("movieId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MovieDTO toDtoMovieId(Movie movie);

    @Named("actorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ActorDTO toDtoActorId(Actor actor);

    @Named("actorIdSet")
    default Set<ActorDTO> toDtoActorIdSet(Set<Actor> actor) {
        return actor.stream().map(this::toDtoActorId).collect(Collectors.toSet());
    }
}
