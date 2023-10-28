package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SessionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Session}.
 */
public interface SessionService {
    /**
     * Save a session.
     *
     * @param sessionDTO the entity to save.
     * @return the persisted entity.
     */
    SessionDTO save(SessionDTO sessionDTO);

    /**
     * Updates a session.
     *
     * @param sessionDTO the entity to update.
     * @return the persisted entity.
     */
    SessionDTO update(SessionDTO sessionDTO);

    /**
     * Partially updates a session.
     *
     * @param sessionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SessionDTO> partialUpdate(SessionDTO sessionDTO);

    /**
     * Get all the sessions.
     *
     * @return the list of entities.
     */
    List<SessionDTO> findAll();

    /**
     * Get the "id" session.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SessionDTO> findOne(Long id);

    /**
     * Delete the "id" session.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
