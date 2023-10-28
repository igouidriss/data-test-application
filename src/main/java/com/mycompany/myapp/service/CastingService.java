package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CastingDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Casting}.
 */
public interface CastingService {
    /**
     * Save a casting.
     *
     * @param castingDTO the entity to save.
     * @return the persisted entity.
     */
    CastingDTO save(CastingDTO castingDTO);

    /**
     * Updates a casting.
     *
     * @param castingDTO the entity to update.
     * @return the persisted entity.
     */
    CastingDTO update(CastingDTO castingDTO);

    /**
     * Partially updates a casting.
     *
     * @param castingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CastingDTO> partialUpdate(CastingDTO castingDTO);

    /**
     * Get all the castings.
     *
     * @return the list of entities.
     */
    List<CastingDTO> findAll();

    /**
     * Get all the castings with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CastingDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" casting.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CastingDTO> findOne(Long id);

    /**
     * Delete the "id" casting.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
