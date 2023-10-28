package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CinemaDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Cinema}.
 */
public interface CinemaService {
    /**
     * Save a cinema.
     *
     * @param cinemaDTO the entity to save.
     * @return the persisted entity.
     */
    CinemaDTO save(CinemaDTO cinemaDTO);

    /**
     * Updates a cinema.
     *
     * @param cinemaDTO the entity to update.
     * @return the persisted entity.
     */
    CinemaDTO update(CinemaDTO cinemaDTO);

    /**
     * Partially updates a cinema.
     *
     * @param cinemaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CinemaDTO> partialUpdate(CinemaDTO cinemaDTO);

    /**
     * Get all the cinemas.
     *
     * @return the list of entities.
     */
    List<CinemaDTO> findAll();

    /**
     * Get the "id" cinema.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CinemaDTO> findOne(Long id);

    /**
     * Delete the "id" cinema.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
