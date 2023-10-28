package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.RoomDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Room}.
 */
public interface RoomService {
    /**
     * Save a room.
     *
     * @param roomDTO the entity to save.
     * @return the persisted entity.
     */
    RoomDTO save(RoomDTO roomDTO);

    /**
     * Updates a room.
     *
     * @param roomDTO the entity to update.
     * @return the persisted entity.
     */
    RoomDTO update(RoomDTO roomDTO);

    /**
     * Partially updates a room.
     *
     * @param roomDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RoomDTO> partialUpdate(RoomDTO roomDTO);

    /**
     * Get all the rooms.
     *
     * @return the list of entities.
     */
    List<RoomDTO> findAll();

    /**
     * Get the "id" room.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoomDTO> findOne(Long id);

    /**
     * Delete the "id" room.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
