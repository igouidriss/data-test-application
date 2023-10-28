package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Movie;
import com.mycompany.myapp.repository.MovieRepository;
import com.mycompany.myapp.service.MovieService;
import com.mycompany.myapp.service.dto.MovieDTO;
import com.mycompany.myapp.service.mapper.MovieMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Movie}.
 */
@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    private final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    public MovieDTO save(MovieDTO movieDTO) {
        log.debug("Request to save Movie : {}", movieDTO);
        Movie movie = movieMapper.toEntity(movieDTO);
        movie = movieRepository.save(movie);
        return movieMapper.toDto(movie);
    }

    @Override
    public MovieDTO update(MovieDTO movieDTO) {
        log.debug("Request to update Movie : {}", movieDTO);
        Movie movie = movieMapper.toEntity(movieDTO);
        movie = movieRepository.save(movie);
        return movieMapper.toDto(movie);
    }

    @Override
    public Optional<MovieDTO> partialUpdate(MovieDTO movieDTO) {
        log.debug("Request to partially update Movie : {}", movieDTO);

        return movieRepository
            .findById(movieDTO.getId())
            .map(existingMovie -> {
                movieMapper.partialUpdate(existingMovie, movieDTO);

                return existingMovie;
            })
            .map(movieRepository::save)
            .map(movieMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieDTO> findAll() {
        log.debug("Request to get all Movies");
        return movieRepository.findAll().stream().map(movieMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the movies where Casting is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MovieDTO> findAllWhereCastingIsNull() {
        log.debug("Request to get all movies where Casting is null");
        return StreamSupport
            .stream(movieRepository.findAll().spliterator(), false)
            .filter(movie -> movie.getCasting() == null)
            .map(movieMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MovieDTO> findOne(Long id) {
        log.debug("Request to get Movie : {}", id);
        return movieRepository.findById(id).map(movieMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Movie : {}", id);
        movieRepository.deleteById(id);
    }
}
