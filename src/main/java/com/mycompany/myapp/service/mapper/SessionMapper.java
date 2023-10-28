package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Movie;
import com.mycompany.myapp.domain.Period;
import com.mycompany.myapp.domain.Session;
import com.mycompany.myapp.service.dto.MovieDTO;
import com.mycompany.myapp.service.dto.PeriodDTO;
import com.mycompany.myapp.service.dto.SessionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Session} and its DTO {@link SessionDTO}.
 */
@Mapper(componentModel = "spring")
public interface SessionMapper extends EntityMapper<SessionDTO, Session> {
    @Mapping(target = "period", source = "period", qualifiedByName = "periodId")
    @Mapping(target = "movie", source = "movie", qualifiedByName = "movieId")
    SessionDTO toDto(Session s);

    @Named("periodId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PeriodDTO toDtoPeriodId(Period period);

    @Named("movieId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MovieDTO toDtoMovieId(Movie movie);
}
