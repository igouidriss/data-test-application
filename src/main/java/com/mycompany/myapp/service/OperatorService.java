package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.OperatorDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Operator}.
 */
public interface OperatorService {
    /**
     * Save a operator.
     *
     * @param operatorDTO the entity to save.
     * @return the persisted entity.
     */
    OperatorDTO save(OperatorDTO operatorDTO);

    /**
     * Updates a operator.
     *
     * @param operatorDTO the entity to update.
     * @return the persisted entity.
     */
    OperatorDTO update(OperatorDTO operatorDTO);

    /**
     * Partially updates a operator.
     *
     * @param operatorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OperatorDTO> partialUpdate(OperatorDTO operatorDTO);

    /**
     * Get all the operators.
     *
     * @return the list of entities.
     */
    List<OperatorDTO> findAll();

    /**
     * Get all the OperatorDTO where Cinema is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<OperatorDTO> findAllWhereCinemaIsNull();

    /**
     * Get the "id" operator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OperatorDTO> findOne(Long id);

    /**
     * Delete the "id" operator.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
