package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CastingRepository;
import com.mycompany.myapp.service.CastingService;
import com.mycompany.myapp.service.dto.CastingDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Casting}.
 */
@RestController
@RequestMapping("/api")
public class CastingResource {

    private final Logger log = LoggerFactory.getLogger(CastingResource.class);

    private static final String ENTITY_NAME = "casting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CastingService castingService;

    private final CastingRepository castingRepository;

    public CastingResource(CastingService castingService, CastingRepository castingRepository) {
        this.castingService = castingService;
        this.castingRepository = castingRepository;
    }

    /**
     * {@code POST  /castings} : Create a new casting.
     *
     * @param castingDTO the castingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new castingDTO, or with status {@code 400 (Bad Request)} if the casting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/castings")
    public ResponseEntity<CastingDTO> createCasting(@RequestBody CastingDTO castingDTO) throws URISyntaxException {
        log.debug("REST request to save Casting : {}", castingDTO);
        if (castingDTO.getId() != null) {
            throw new BadRequestAlertException("A new casting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CastingDTO result = castingService.save(castingDTO);
        return ResponseEntity
            .created(new URI("/api/castings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /castings/:id} : Updates an existing casting.
     *
     * @param id the id of the castingDTO to save.
     * @param castingDTO the castingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated castingDTO,
     * or with status {@code 400 (Bad Request)} if the castingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the castingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/castings/{id}")
    public ResponseEntity<CastingDTO> updateCasting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CastingDTO castingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Casting : {}, {}", id, castingDTO);
        if (castingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, castingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!castingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CastingDTO result = castingService.update(castingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, castingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /castings/:id} : Partial updates given fields of an existing casting, field will ignore if it is null
     *
     * @param id the id of the castingDTO to save.
     * @param castingDTO the castingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated castingDTO,
     * or with status {@code 400 (Bad Request)} if the castingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the castingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the castingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/castings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CastingDTO> partialUpdateCasting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CastingDTO castingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Casting partially : {}, {}", id, castingDTO);
        if (castingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, castingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!castingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CastingDTO> result = castingService.partialUpdate(castingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, castingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /castings} : get all the castings.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of castings in body.
     */
    @GetMapping("/castings")
    public List<CastingDTO> getAllCastings(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Castings");
        return castingService.findAll();
    }

    /**
     * {@code GET  /castings/:id} : get the "id" casting.
     *
     * @param id the id of the castingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the castingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/castings/{id}")
    public ResponseEntity<CastingDTO> getCasting(@PathVariable Long id) {
        log.debug("REST request to get Casting : {}", id);
        Optional<CastingDTO> castingDTO = castingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(castingDTO);
    }

    /**
     * {@code DELETE  /castings/:id} : delete the "id" casting.
     *
     * @param id the id of the castingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/castings/{id}")
    public ResponseEntity<Void> deleteCasting(@PathVariable Long id) {
        log.debug("REST request to delete Casting : {}", id);
        castingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
