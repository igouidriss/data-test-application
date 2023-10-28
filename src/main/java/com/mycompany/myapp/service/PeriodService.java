package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PeriodDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Period}.
 */
public interface PeriodService {
    /**
     * Save a period.
     *
     * @param periodDTO the entity to save.
     * @return the persisted entity.
     */
    PeriodDTO save(PeriodDTO periodDTO);

    /**
     * Updates a period.
     *
     * @param periodDTO the entity to update.
     * @return the persisted entity.
     */
    PeriodDTO update(PeriodDTO periodDTO);

    /**
     * Partially updates a period.
     *
     * @param periodDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PeriodDTO> partialUpdate(PeriodDTO periodDTO);

    /**
     * Get all the periods.
     *
     * @return the list of entities.
     */
    List<PeriodDTO> findAll();

    /**
     * Get all the PeriodDTO where Session is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<PeriodDTO> findAllWhereSessionIsNull();

    /**
     * Get the "id" period.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PeriodDTO> findOne(Long id);

    /**
     * Delete the "id" period.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
