package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Cinema;
import com.mycompany.myapp.repository.CinemaRepository;
import com.mycompany.myapp.service.dto.CinemaDTO;
import com.mycompany.myapp.service.mapper.CinemaMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CinemaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CinemaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cinemas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private CinemaMapper cinemaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCinemaMockMvc;

    private Cinema cinema;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cinema createEntity(EntityManager em) {
        Cinema cinema = new Cinema().name(DEFAULT_NAME);
        return cinema;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cinema createUpdatedEntity(EntityManager em) {
        Cinema cinema = new Cinema().name(UPDATED_NAME);
        return cinema;
    }

    @BeforeEach
    public void initTest() {
        cinema = createEntity(em);
    }

    @Test
    @Transactional
    void createCinema() throws Exception {
        int databaseSizeBeforeCreate = cinemaRepository.findAll().size();
        // Create the Cinema
        CinemaDTO cinemaDTO = cinemaMapper.toDto(cinema);
        restCinemaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cinemaDTO)))
            .andExpect(status().isCreated());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeCreate + 1);
        Cinema testCinema = cinemaList.get(cinemaList.size() - 1);
        assertThat(testCinema.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createCinemaWithExistingId() throws Exception {
        // Create the Cinema with an existing ID
        cinema.setId(1L);
        CinemaDTO cinemaDTO = cinemaMapper.toDto(cinema);

        int databaseSizeBeforeCreate = cinemaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCinemaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cinemaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCinemas() throws Exception {
        // Initialize the database
        cinemaRepository.saveAndFlush(cinema);

        // Get all the cinemaList
        restCinemaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cinema.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getCinema() throws Exception {
        // Initialize the database
        cinemaRepository.saveAndFlush(cinema);

        // Get the cinema
        restCinemaMockMvc
            .perform(get(ENTITY_API_URL_ID, cinema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cinema.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCinema() throws Exception {
        // Get the cinema
        restCinemaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCinema() throws Exception {
        // Initialize the database
        cinemaRepository.saveAndFlush(cinema);

        int databaseSizeBeforeUpdate = cinemaRepository.findAll().size();

        // Update the cinema
        Cinema updatedCinema = cinemaRepository.findById(cinema.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCinema are not directly saved in db
        em.detach(updatedCinema);
        updatedCinema.name(UPDATED_NAME);
        CinemaDTO cinemaDTO = cinemaMapper.toDto(updatedCinema);

        restCinemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cinemaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cinemaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeUpdate);
        Cinema testCinema = cinemaList.get(cinemaList.size() - 1);
        assertThat(testCinema.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCinema() throws Exception {
        int databaseSizeBeforeUpdate = cinemaRepository.findAll().size();
        cinema.setId(count.incrementAndGet());

        // Create the Cinema
        CinemaDTO cinemaDTO = cinemaMapper.toDto(cinema);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCinemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cinemaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cinemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCinema() throws Exception {
        int databaseSizeBeforeUpdate = cinemaRepository.findAll().size();
        cinema.setId(count.incrementAndGet());

        // Create the Cinema
        CinemaDTO cinemaDTO = cinemaMapper.toDto(cinema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCinemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cinemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCinema() throws Exception {
        int databaseSizeBeforeUpdate = cinemaRepository.findAll().size();
        cinema.setId(count.incrementAndGet());

        // Create the Cinema
        CinemaDTO cinemaDTO = cinemaMapper.toDto(cinema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCinemaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cinemaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCinemaWithPatch() throws Exception {
        // Initialize the database
        cinemaRepository.saveAndFlush(cinema);

        int databaseSizeBeforeUpdate = cinemaRepository.findAll().size();

        // Update the cinema using partial update
        Cinema partialUpdatedCinema = new Cinema();
        partialUpdatedCinema.setId(cinema.getId());

        partialUpdatedCinema.name(UPDATED_NAME);

        restCinemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCinema.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCinema))
            )
            .andExpect(status().isOk());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeUpdate);
        Cinema testCinema = cinemaList.get(cinemaList.size() - 1);
        assertThat(testCinema.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCinemaWithPatch() throws Exception {
        // Initialize the database
        cinemaRepository.saveAndFlush(cinema);

        int databaseSizeBeforeUpdate = cinemaRepository.findAll().size();

        // Update the cinema using partial update
        Cinema partialUpdatedCinema = new Cinema();
        partialUpdatedCinema.setId(cinema.getId());

        partialUpdatedCinema.name(UPDATED_NAME);

        restCinemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCinema.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCinema))
            )
            .andExpect(status().isOk());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeUpdate);
        Cinema testCinema = cinemaList.get(cinemaList.size() - 1);
        assertThat(testCinema.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCinema() throws Exception {
        int databaseSizeBeforeUpdate = cinemaRepository.findAll().size();
        cinema.setId(count.incrementAndGet());

        // Create the Cinema
        CinemaDTO cinemaDTO = cinemaMapper.toDto(cinema);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCinemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cinemaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cinemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCinema() throws Exception {
        int databaseSizeBeforeUpdate = cinemaRepository.findAll().size();
        cinema.setId(count.incrementAndGet());

        // Create the Cinema
        CinemaDTO cinemaDTO = cinemaMapper.toDto(cinema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCinemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cinemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCinema() throws Exception {
        int databaseSizeBeforeUpdate = cinemaRepository.findAll().size();
        cinema.setId(count.incrementAndGet());

        // Create the Cinema
        CinemaDTO cinemaDTO = cinemaMapper.toDto(cinema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCinemaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cinemaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCinema() throws Exception {
        // Initialize the database
        cinemaRepository.saveAndFlush(cinema);

        int databaseSizeBeforeDelete = cinemaRepository.findAll().size();

        // Delete the cinema
        restCinemaMockMvc
            .perform(delete(ENTITY_API_URL_ID, cinema.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
