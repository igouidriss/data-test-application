package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Movie;
import com.mycompany.myapp.service.dto.MovieDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Movie} and its DTO {@link MovieDTO}.
 */
@Mapper(componentModel = "spring")
public interface MovieMapper extends EntityMapper<MovieDTO, Movie> {}
