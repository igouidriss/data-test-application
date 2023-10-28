package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Casting;
import com.mycompany.myapp.repository.CastingRepository;
import com.mycompany.myapp.service.CastingService;
import com.mycompany.myapp.service.dto.CastingDTO;
import com.mycompany.myapp.service.mapper.CastingMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CastingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CastingResourceIT {

    private static final String ENTITY_API_URL = "/api/castings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CastingRepository castingRepository;

    @Mock
    private CastingRepository castingRepositoryMock;

    @Autowired
    private CastingMapper castingMapper;

    @Mock
    private CastingService castingServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCastingMockMvc;

    private Casting casting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Casting createEntity(EntityManager em) {
        Casting casting = new Casting();
        return casting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Casting createUpdatedEntity(EntityManager em) {
        Casting casting = new Casting();
        return casting;
    }

    @BeforeEach
    public void initTest() {
        casting = createEntity(em);
    }

    @Test
    @Transactional
    void createCasting() throws Exception {
        int databaseSizeBeforeCreate = castingRepository.findAll().size();
        // Create the Casting
        CastingDTO castingDTO = castingMapper.toDto(casting);
        restCastingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(castingDTO)))
            .andExpect(status().isCreated());

        // Validate the Casting in the database
        List<Casting> castingList = castingRepository.findAll();
        assertThat(castingList).hasSize(databaseSizeBeforeCreate + 1);
        Casting testCasting = castingList.get(castingList.size() - 1);
    }

    @Test
    @Transactional
    void createCastingWithExistingId() throws Exception {
        // Create the Casting with an existing ID
        casting.setId(1L);
        CastingDTO castingDTO = castingMapper.toDto(casting);

        int databaseSizeBeforeCreate = castingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCastingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(castingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Casting in the database
        List<Casting> castingList = castingRepository.findAll();
        assertThat(castingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCastings() throws Exception {
        // Initialize the database
        castingRepository.saveAndFlush(casting);

        // Get all the castingList
        restCastingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(casting.getId().intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCastingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(castingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCastingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(castingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCastingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(castingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCastingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(castingRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCasting() throws Exception {
        // Initialize the database
        castingRepository.saveAndFlush(casting);

        // Get the casting
        restCastingMockMvc
            .perform(get(ENTITY_API_URL_ID, casting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(casting.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCasting() throws Exception {
        // Get the casting
        restCastingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCasting() throws Exception {
        // Initialize the database
        castingRepository.saveAndFlush(casting);

        int databaseSizeBeforeUpdate = castingRepository.findAll().size();

        // Update the casting
        Casting updatedCasting = castingRepository.findById(casting.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCasting are not directly saved in db
        em.detach(updatedCasting);
        CastingDTO castingDTO = castingMapper.toDto(updatedCasting);

        restCastingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, castingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(castingDTO))
            )
            .andExpect(status().isOk());

        // Validate the Casting in the database
        List<Casting> castingList = castingRepository.findAll();
        assertThat(castingList).hasSize(databaseSizeBeforeUpdate);
        Casting testCasting = castingList.get(castingList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingCasting() throws Exception {
        int databaseSizeBeforeUpdate = castingRepository.findAll().size();
        casting.setId(count.incrementAndGet());

        // Create the Casting
        CastingDTO castingDTO = castingMapper.toDto(casting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCastingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, castingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(castingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Casting in the database
        List<Casting> castingList = castingRepository.findAll();
        assertThat(castingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCasting() throws Exception {
        int databaseSizeBeforeUpdate = castingRepository.findAll().size();
        casting.setId(count.incrementAndGet());

        // Create the Casting
        CastingDTO castingDTO = castingMapper.toDto(casting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCastingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(castingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Casting in the database
        List<Casting> castingList = castingRepository.findAll();
        assertThat(castingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCasting() throws Exception {
        int databaseSizeBeforeUpdate = castingRepository.findAll().size();
        casting.setId(count.incrementAndGet());

        // Create the Casting
        CastingDTO castingDTO = castingMapper.toDto(casting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCastingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(castingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Casting in the database
        List<Casting> castingList = castingRepository.findAll();
        assertThat(castingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCastingWithPatch() throws Exception {
        // Initialize the database
        castingRepository.saveAndFlush(casting);

        int databaseSizeBeforeUpdate = castingRepository.findAll().size();

        // Update the casting using partial update
        Casting partialUpdatedCasting = new Casting();
        partialUpdatedCasting.setId(casting.getId());

        restCastingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCasting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCasting))
            )
            .andExpect(status().isOk());

        // Validate the Casting in the database
        List<Casting> castingList = castingRepository.findAll();
        assertThat(castingList).hasSize(databaseSizeBeforeUpdate);
        Casting testCasting = castingList.get(castingList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateCastingWithPatch() throws Exception {
        // Initialize the database
        castingRepository.saveAndFlush(casting);

        int databaseSizeBeforeUpdate = castingRepository.findAll().size();

        // Update the casting using partial update
        Casting partialUpdatedCasting = new Casting();
        partialUpdatedCasting.setId(casting.getId());

        restCastingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCasting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCasting))
            )
            .andExpect(status().isOk());

        // Validate the Casting in the database
        List<Casting> castingList = castingRepository.findAll();
        assertThat(castingList).hasSize(databaseSizeBeforeUpdate);
        Casting testCasting = castingList.get(castingList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingCasting() throws Exception {
        int databaseSizeBeforeUpdate = castingRepository.findAll().size();
        casting.setId(count.incrementAndGet());

        // Create the Casting
        CastingDTO castingDTO = castingMapper.toDto(casting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCastingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, castingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(castingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Casting in the database
        List<Casting> castingList = castingRepository.findAll();
        assertThat(castingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCasting() throws Exception {
        int databaseSizeBeforeUpdate = castingRepository.findAll().size();
        casting.setId(count.incrementAndGet());

        // Create the Casting
        CastingDTO castingDTO = castingMapper.toDto(casting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCastingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(castingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Casting in the database
        List<Casting> castingList = castingRepository.findAll();
        assertThat(castingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCasting() throws Exception {
        int databaseSizeBeforeUpdate = castingRepository.findAll().size();
        casting.setId(count.incrementAndGet());

        // Create the Casting
        CastingDTO castingDTO = castingMapper.toDto(casting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCastingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(castingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Casting in the database
        List<Casting> castingList = castingRepository.findAll();
        assertThat(castingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCasting() throws Exception {
        // Initialize the database
        castingRepository.saveAndFlush(casting);

        int databaseSizeBeforeDelete = castingRepository.findAll().size();

        // Delete the casting
        restCastingMockMvc
            .perform(delete(ENTITY_API_URL_ID, casting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Casting> castingList = castingRepository.findAll();
        assertThat(castingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
