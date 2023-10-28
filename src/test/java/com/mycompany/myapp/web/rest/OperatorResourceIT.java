package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Operator;
import com.mycompany.myapp.repository.OperatorRepository;
import com.mycompany.myapp.service.dto.OperatorDTO;
import com.mycompany.myapp.service.mapper.OperatorMapper;
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
 * Integration tests for the {@link OperatorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OperatorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/operators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private OperatorMapper operatorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOperatorMockMvc;

    private Operator operator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Operator createEntity(EntityManager em) {
        Operator operator = new Operator().name(DEFAULT_NAME);
        return operator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Operator createUpdatedEntity(EntityManager em) {
        Operator operator = new Operator().name(UPDATED_NAME);
        return operator;
    }

    @BeforeEach
    public void initTest() {
        operator = createEntity(em);
    }

    @Test
    @Transactional
    void createOperator() throws Exception {
        int databaseSizeBeforeCreate = operatorRepository.findAll().size();
        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);
        restOperatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operatorDTO)))
            .andExpect(status().isCreated());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeCreate + 1);
        Operator testOperator = operatorList.get(operatorList.size() - 1);
        assertThat(testOperator.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createOperatorWithExistingId() throws Exception {
        // Create the Operator with an existing ID
        operator.setId(1L);
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        int databaseSizeBeforeCreate = operatorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOperators() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        // Get all the operatorList
        restOperatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operator.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getOperator() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        // Get the operator
        restOperatorMockMvc
            .perform(get(ENTITY_API_URL_ID, operator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(operator.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingOperator() throws Exception {
        // Get the operator
        restOperatorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOperator() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        int databaseSizeBeforeUpdate = operatorRepository.findAll().size();

        // Update the operator
        Operator updatedOperator = operatorRepository.findById(operator.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOperator are not directly saved in db
        em.detach(updatedOperator);
        updatedOperator.name(UPDATED_NAME);
        OperatorDTO operatorDTO = operatorMapper.toDto(updatedOperator);

        restOperatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operatorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeUpdate);
        Operator testOperator = operatorList.get(operatorList.size() - 1);
        assertThat(testOperator.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingOperator() throws Exception {
        int databaseSizeBeforeUpdate = operatorRepository.findAll().size();
        operator.setId(count.incrementAndGet());

        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOperator() throws Exception {
        int databaseSizeBeforeUpdate = operatorRepository.findAll().size();
        operator.setId(count.incrementAndGet());

        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOperator() throws Exception {
        int databaseSizeBeforeUpdate = operatorRepository.findAll().size();
        operator.setId(count.incrementAndGet());

        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperatorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operatorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOperatorWithPatch() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        int databaseSizeBeforeUpdate = operatorRepository.findAll().size();

        // Update the operator using partial update
        Operator partialUpdatedOperator = new Operator();
        partialUpdatedOperator.setId(operator.getId());

        partialUpdatedOperator.name(UPDATED_NAME);

        restOperatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperator))
            )
            .andExpect(status().isOk());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeUpdate);
        Operator testOperator = operatorList.get(operatorList.size() - 1);
        assertThat(testOperator.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateOperatorWithPatch() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        int databaseSizeBeforeUpdate = operatorRepository.findAll().size();

        // Update the operator using partial update
        Operator partialUpdatedOperator = new Operator();
        partialUpdatedOperator.setId(operator.getId());

        partialUpdatedOperator.name(UPDATED_NAME);

        restOperatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperator))
            )
            .andExpect(status().isOk());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeUpdate);
        Operator testOperator = operatorList.get(operatorList.size() - 1);
        assertThat(testOperator.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingOperator() throws Exception {
        int databaseSizeBeforeUpdate = operatorRepository.findAll().size();
        operator.setId(count.incrementAndGet());

        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, operatorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOperator() throws Exception {
        int databaseSizeBeforeUpdate = operatorRepository.findAll().size();
        operator.setId(count.incrementAndGet());

        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOperator() throws Exception {
        int databaseSizeBeforeUpdate = operatorRepository.findAll().size();
        operator.setId(count.incrementAndGet());

        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperatorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(operatorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOperator() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        int databaseSizeBeforeDelete = operatorRepository.findAll().size();

        // Delete the operator
        restOperatorMockMvc
            .perform(delete(ENTITY_API_URL_ID, operator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
