package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CinemaRepository;
import com.mycompany.myapp.service.CinemaService;
import com.mycompany.myapp.service.dto.CinemaDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Cinema}.
 */
@RestController
@RequestMapping("/api")
public class CinemaResource {

    private final Logger log = LoggerFactory.getLogger(CinemaResource.class);

    private static final String ENTITY_NAME = "cinema";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CinemaService cinemaService;

    private final CinemaRepository cinemaRepository;

    public CinemaResource(CinemaService cinemaService, CinemaRepository cinemaRepository) {
        this.cinemaService = cinemaService;
        this.cinemaRepository = cinemaRepository;
    }

    /**
     * {@code POST  /cinemas} : Create a new cinema.
     *
     * @param cinemaDTO the cinemaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cinemaDTO, or with status {@code 400 (Bad Request)} if the cinema has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cinemas")
    public ResponseEntity<CinemaDTO> createCinema(@RequestBody CinemaDTO cinemaDTO) throws URISyntaxException {
        log.debug("REST request to save Cinema : {}", cinemaDTO);
        if (cinemaDTO.getId() != null) {
            throw new BadRequestAlertException("A new cinema cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CinemaDTO result = cinemaService.save(cinemaDTO);
        return ResponseEntity
            .created(new URI("/api/cinemas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cinemas/:id} : Updates an existing cinema.
     *
     * @param id the id of the cinemaDTO to save.
     * @param cinemaDTO the cinemaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cinemaDTO,
     * or with status {@code 400 (Bad Request)} if the cinemaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cinemaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cinemas/{id}")
    public ResponseEntity<CinemaDTO> updateCinema(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CinemaDTO cinemaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Cinema : {}, {}", id, cinemaDTO);
        if (cinemaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cinemaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cinemaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CinemaDTO result = cinemaService.update(cinemaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cinemaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cinemas/:id} : Partial updates given fields of an existing cinema, field will ignore if it is null
     *
     * @param id the id of the cinemaDTO to save.
     * @param cinemaDTO the cinemaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cinemaDTO,
     * or with status {@code 400 (Bad Request)} if the cinemaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cinemaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cinemaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cinemas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CinemaDTO> partialUpdateCinema(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CinemaDTO cinemaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cinema partially : {}, {}", id, cinemaDTO);
        if (cinemaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cinemaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cinemaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CinemaDTO> result = cinemaService.partialUpdate(cinemaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cinemaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cinemas} : get all the cinemas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cinemas in body.
     */
    @GetMapping("/cinemas")
    public List<CinemaDTO> getAllCinemas() {
        log.debug("REST request to get all Cinemas");
        return cinemaService.findAll();
    }

    /**
     * {@code GET  /cinemas/:id} : get the "id" cinema.
     *
     * @param id the id of the cinemaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cinemaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cinemas/{id}")
    public ResponseEntity<CinemaDTO> getCinema(@PathVariable Long id) {
        log.debug("REST request to get Cinema : {}", id);
        Optional<CinemaDTO> cinemaDTO = cinemaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cinemaDTO);
    }

    /**
     * {@code DELETE  /cinemas/:id} : delete the "id" cinema.
     *
     * @param id the id of the cinemaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cinemas/{id}")
    public ResponseEntity<Void> deleteCinema(@PathVariable Long id) {
        log.debug("REST request to delete Cinema : {}", id);
        cinemaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
