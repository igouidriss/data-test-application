package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.MovieDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Movie}.
 */
public interface MovieService {
    /**
     * Save a movie.
     *
     * @param movieDTO the entity to save.
     * @return the persisted entity.
     */
    MovieDTO save(MovieDTO movieDTO);

    /**
     * Updates a movie.
     *
     * @param movieDTO the entity to update.
     * @return the persisted entity.
     */
    MovieDTO update(MovieDTO movieDTO);

    /**
     * Partially updates a movie.
     *
     * @param movieDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MovieDTO> partialUpdate(MovieDTO movieDTO);

    /**
     * Get all the movies.
     *
     * @return the list of entities.
     */
    List<MovieDTO> findAll();

    /**
     * Get all the MovieDTO where Casting is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<MovieDTO> findAllWhereCastingIsNull();

    /**
     * Get the "id" movie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MovieDTO> findOne(Long id);

    /**
     * Delete the "id" movie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
