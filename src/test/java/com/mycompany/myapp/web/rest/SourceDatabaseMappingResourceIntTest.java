package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyApp;

import com.mycompany.myapp.domain.SourceDatabaseMapping;
import com.mycompany.myapp.domain.SourceDatabase;
import com.mycompany.myapp.repository.SourceDatabaseMappingRepository;
import com.mycompany.myapp.service.SourceDatabaseMappingService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.SourceDatabaseMappingCriteria;
import com.mycompany.myapp.service.SourceDatabaseMappingQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.FieldDataType;
/**
 * Test class for the SourceDatabaseMappingResource REST controller.
 *
 * @see SourceDatabaseMappingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApp.class)
public class SourceDatabaseMappingResourceIntTest {

    private static final String DEFAULT_DB_COLUMN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DB_COLUMN_NAME = "BBBBBBBBBB";

    private static final FieldDataType DEFAULT_DB_DATA_TYPE = FieldDataType.STRING;
    private static final FieldDataType UPDATED_DB_DATA_TYPE = FieldDataType.NUMBER;

    private static final Integer DEFAULT_DB_FIELD_SCALE = 1;
    private static final Integer UPDATED_DB_FIELD_SCALE = 2;

    private static final Integer DEFAULT_DB_FIELD_PRECISION = 1;
    private static final Integer UPDATED_DB_FIELD_PRECISION = 2;

    @Autowired
    private SourceDatabaseMappingRepository sourceDatabaseMappingRepository;
    
    @Autowired
    private SourceDatabaseMappingService sourceDatabaseMappingService;

    @Autowired
    private SourceDatabaseMappingQueryService sourceDatabaseMappingQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSourceDatabaseMappingMockMvc;

    private SourceDatabaseMapping sourceDatabaseMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SourceDatabaseMappingResource sourceDatabaseMappingResource = new SourceDatabaseMappingResource(sourceDatabaseMappingService, sourceDatabaseMappingQueryService);
        this.restSourceDatabaseMappingMockMvc = MockMvcBuilders.standaloneSetup(sourceDatabaseMappingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SourceDatabaseMapping createEntity(EntityManager em) {
        SourceDatabaseMapping sourceDatabaseMapping = new SourceDatabaseMapping()
            .dbColumnName(DEFAULT_DB_COLUMN_NAME)
            .dbDataType(DEFAULT_DB_DATA_TYPE)
            .dbFieldScale(DEFAULT_DB_FIELD_SCALE)
            .dbFieldPrecision(DEFAULT_DB_FIELD_PRECISION);
        return sourceDatabaseMapping;
    }

    @Before
    public void initTest() {
        sourceDatabaseMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createSourceDatabaseMapping() throws Exception {
        int databaseSizeBeforeCreate = sourceDatabaseMappingRepository.findAll().size();

        // Create the SourceDatabaseMapping
        restSourceDatabaseMappingMockMvc.perform(post("/api/source-database-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceDatabaseMapping)))
            .andExpect(status().isCreated());

        // Validate the SourceDatabaseMapping in the database
        List<SourceDatabaseMapping> sourceDatabaseMappingList = sourceDatabaseMappingRepository.findAll();
        assertThat(sourceDatabaseMappingList).hasSize(databaseSizeBeforeCreate + 1);
        SourceDatabaseMapping testSourceDatabaseMapping = sourceDatabaseMappingList.get(sourceDatabaseMappingList.size() - 1);
        assertThat(testSourceDatabaseMapping.getDbColumnName()).isEqualTo(DEFAULT_DB_COLUMN_NAME);
        assertThat(testSourceDatabaseMapping.getDbDataType()).isEqualTo(DEFAULT_DB_DATA_TYPE);
        assertThat(testSourceDatabaseMapping.getDbFieldScale()).isEqualTo(DEFAULT_DB_FIELD_SCALE);
        assertThat(testSourceDatabaseMapping.getDbFieldPrecision()).isEqualTo(DEFAULT_DB_FIELD_PRECISION);
    }

    @Test
    @Transactional
    public void createSourceDatabaseMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sourceDatabaseMappingRepository.findAll().size();

        // Create the SourceDatabaseMapping with an existing ID
        sourceDatabaseMapping.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSourceDatabaseMappingMockMvc.perform(post("/api/source-database-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceDatabaseMapping)))
            .andExpect(status().isBadRequest());

        // Validate the SourceDatabaseMapping in the database
        List<SourceDatabaseMapping> sourceDatabaseMappingList = sourceDatabaseMappingRepository.findAll();
        assertThat(sourceDatabaseMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDbColumnNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceDatabaseMappingRepository.findAll().size();
        // set the field null
        sourceDatabaseMapping.setDbColumnName(null);

        // Create the SourceDatabaseMapping, which fails.

        restSourceDatabaseMappingMockMvc.perform(post("/api/source-database-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceDatabaseMapping)))
            .andExpect(status().isBadRequest());

        List<SourceDatabaseMapping> sourceDatabaseMappingList = sourceDatabaseMappingRepository.findAll();
        assertThat(sourceDatabaseMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDbDataTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceDatabaseMappingRepository.findAll().size();
        // set the field null
        sourceDatabaseMapping.setDbDataType(null);

        // Create the SourceDatabaseMapping, which fails.

        restSourceDatabaseMappingMockMvc.perform(post("/api/source-database-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceDatabaseMapping)))
            .andExpect(status().isBadRequest());

        List<SourceDatabaseMapping> sourceDatabaseMappingList = sourceDatabaseMappingRepository.findAll();
        assertThat(sourceDatabaseMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSourceDatabaseMappings() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get all the sourceDatabaseMappingList
        restSourceDatabaseMappingMockMvc.perform(get("/api/source-database-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sourceDatabaseMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].dbColumnName").value(hasItem(DEFAULT_DB_COLUMN_NAME.toString())))
            .andExpect(jsonPath("$.[*].dbDataType").value(hasItem(DEFAULT_DB_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dbFieldScale").value(hasItem(DEFAULT_DB_FIELD_SCALE)))
            .andExpect(jsonPath("$.[*].dbFieldPrecision").value(hasItem(DEFAULT_DB_FIELD_PRECISION)));
    }
    
    @Test
    @Transactional
    public void getSourceDatabaseMapping() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get the sourceDatabaseMapping
        restSourceDatabaseMappingMockMvc.perform(get("/api/source-database-mappings/{id}", sourceDatabaseMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sourceDatabaseMapping.getId().intValue()))
            .andExpect(jsonPath("$.dbColumnName").value(DEFAULT_DB_COLUMN_NAME.toString()))
            .andExpect(jsonPath("$.dbDataType").value(DEFAULT_DB_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.dbFieldScale").value(DEFAULT_DB_FIELD_SCALE))
            .andExpect(jsonPath("$.dbFieldPrecision").value(DEFAULT_DB_FIELD_PRECISION));
    }

    @Test
    @Transactional
    public void getAllSourceDatabaseMappingsByDbColumnNameIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get all the sourceDatabaseMappingList where dbColumnName equals to DEFAULT_DB_COLUMN_NAME
        defaultSourceDatabaseMappingShouldBeFound("dbColumnName.equals=" + DEFAULT_DB_COLUMN_NAME);

        // Get all the sourceDatabaseMappingList where dbColumnName equals to UPDATED_DB_COLUMN_NAME
        defaultSourceDatabaseMappingShouldNotBeFound("dbColumnName.equals=" + UPDATED_DB_COLUMN_NAME);
    }

    @Test
    @Transactional
    public void getAllSourceDatabaseMappingsByDbColumnNameIsInShouldWork() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get all the sourceDatabaseMappingList where dbColumnName in DEFAULT_DB_COLUMN_NAME or UPDATED_DB_COLUMN_NAME
        defaultSourceDatabaseMappingShouldBeFound("dbColumnName.in=" + DEFAULT_DB_COLUMN_NAME + "," + UPDATED_DB_COLUMN_NAME);

        // Get all the sourceDatabaseMappingList where dbColumnName equals to UPDATED_DB_COLUMN_NAME
        defaultSourceDatabaseMappingShouldNotBeFound("dbColumnName.in=" + UPDATED_DB_COLUMN_NAME);
    }

    @Test
    @Transactional
    public void getAllSourceDatabaseMappingsByDbColumnNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get all the sourceDatabaseMappingList where dbColumnName is not null
        defaultSourceDatabaseMappingShouldBeFound("dbColumnName.specified=true");

        // Get all the sourceDatabaseMappingList where dbColumnName is null
        defaultSourceDatabaseMappingShouldNotBeFound("dbColumnName.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceDatabaseMappingsByDbDataTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get all the sourceDatabaseMappingList where dbDataType equals to DEFAULT_DB_DATA_TYPE
        defaultSourceDatabaseMappingShouldBeFound("dbDataType.equals=" + DEFAULT_DB_DATA_TYPE);

        // Get all the sourceDatabaseMappingList where dbDataType equals to UPDATED_DB_DATA_TYPE
        defaultSourceDatabaseMappingShouldNotBeFound("dbDataType.equals=" + UPDATED_DB_DATA_TYPE);
    }

    @Test
    @Transactional
    public void getAllSourceDatabaseMappingsByDbDataTypeIsInShouldWork() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get all the sourceDatabaseMappingList where dbDataType in DEFAULT_DB_DATA_TYPE or UPDATED_DB_DATA_TYPE
        defaultSourceDatabaseMappingShouldBeFound("dbDataType.in=" + DEFAULT_DB_DATA_TYPE + "," + UPDATED_DB_DATA_TYPE);

        // Get all the sourceDatabaseMappingList where dbDataType equals to UPDATED_DB_DATA_TYPE
        defaultSourceDatabaseMappingShouldNotBeFound("dbDataType.in=" + UPDATED_DB_DATA_TYPE);
    }

    @Test
    @Transactional
    public void getAllSourceDatabaseMappingsByDbDataTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get all the sourceDatabaseMappingList where dbDataType is not null
        defaultSourceDatabaseMappingShouldBeFound("dbDataType.specified=true");

        // Get all the sourceDatabaseMappingList where dbDataType is null
        defaultSourceDatabaseMappingShouldNotBeFound("dbDataType.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceDatabaseMappingsByDbFieldScaleIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get all the sourceDatabaseMappingList where dbFieldScale equals to DEFAULT_DB_FIELD_SCALE
        defaultSourceDatabaseMappingShouldBeFound("dbFieldScale.equals=" + DEFAULT_DB_FIELD_SCALE);

        // Get all the sourceDatabaseMappingList where dbFieldScale equals to UPDATED_DB_FIELD_SCALE
        defaultSourceDatabaseMappingShouldNotBeFound("dbFieldScale.equals=" + UPDATED_DB_FIELD_SCALE);
    }

    @Test
    @Transactional
    public void getAllSourceDatabaseMappingsByDbFieldScaleIsInShouldWork() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get all the sourceDatabaseMappingList where dbFieldScale in DEFAULT_DB_FIELD_SCALE or UPDATED_DB_FIELD_SCALE
        defaultSourceDatabaseMappingShouldBeFound("dbFieldScale.in=" + DEFAULT_DB_FIELD_SCALE + "," + UPDATED_DB_FIELD_SCALE);

        // Get all the sourceDatabaseMappingList where dbFieldScale equals to UPDATED_DB_FIELD_SCALE
        defaultSourceDatabaseMappingShouldNotBeFound("dbFieldScale.in=" + UPDATED_DB_FIELD_SCALE);
    }

    @Test
    @Transactional
    public void getAllSourceDatabaseMappingsByDbFieldScaleIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get all the sourceDatabaseMappingList where dbFieldScale is not null
        defaultSourceDatabaseMappingShouldBeFound("dbFieldScale.specified=true");

        // Get all the sourceDatabaseMappingList where dbFieldScale is null
        defaultSourceDatabaseMappingShouldNotBeFound("dbFieldScale.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceDatabaseMappingsByDbFieldScaleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get all the sourceDatabaseMappingList where dbFieldScale greater than or equals to DEFAULT_DB_FIELD_SCALE
        defaultSourceDatabaseMappingShouldBeFound("dbFieldScale.greaterOrEqualThan=" + DEFAULT_DB_FIELD_SCALE);

        // Get all the sourceDatabaseMappingList where dbFieldScale greater than or equals to UPDATED_DB_FIELD_SCALE
        defaultSourceDatabaseMappingShouldNotBeFound("dbFieldScale.greaterOrEqualThan=" + UPDATED_DB_FIELD_SCALE);
    }

    @Test
    @Transactional
    public void getAllSourceDatabaseMappingsByDbFieldScaleIsLessThanSomething() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get all the sourceDatabaseMappingList where dbFieldScale less than or equals to DEFAULT_DB_FIELD_SCALE
        defaultSourceDatabaseMappingShouldNotBeFound("dbFieldScale.lessThan=" + DEFAULT_DB_FIELD_SCALE);

        // Get all the sourceDatabaseMappingList where dbFieldScale less than or equals to UPDATED_DB_FIELD_SCALE
        defaultSourceDatabaseMappingShouldBeFound("dbFieldScale.lessThan=" + UPDATED_DB_FIELD_SCALE);
    }


    @Test
    @Transactional
    public void getAllSourceDatabaseMappingsByDbFieldPrecisionIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get all the sourceDatabaseMappingList where dbFieldPrecision equals to DEFAULT_DB_FIELD_PRECISION
        defaultSourceDatabaseMappingShouldBeFound("dbFieldPrecision.equals=" + DEFAULT_DB_FIELD_PRECISION);

        // Get all the sourceDatabaseMappingList where dbFieldPrecision equals to UPDATED_DB_FIELD_PRECISION
        defaultSourceDatabaseMappingShouldNotBeFound("dbFieldPrecision.equals=" + UPDATED_DB_FIELD_PRECISION);
    }

    @Test
    @Transactional
    public void getAllSourceDatabaseMappingsByDbFieldPrecisionIsInShouldWork() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get all the sourceDatabaseMappingList where dbFieldPrecision in DEFAULT_DB_FIELD_PRECISION or UPDATED_DB_FIELD_PRECISION
        defaultSourceDatabaseMappingShouldBeFound("dbFieldPrecision.in=" + DEFAULT_DB_FIELD_PRECISION + "," + UPDATED_DB_FIELD_PRECISION);

        // Get all the sourceDatabaseMappingList where dbFieldPrecision equals to UPDATED_DB_FIELD_PRECISION
        defaultSourceDatabaseMappingShouldNotBeFound("dbFieldPrecision.in=" + UPDATED_DB_FIELD_PRECISION);
    }

    @Test
    @Transactional
    public void getAllSourceDatabaseMappingsByDbFieldPrecisionIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get all the sourceDatabaseMappingList where dbFieldPrecision is not null
        defaultSourceDatabaseMappingShouldBeFound("dbFieldPrecision.specified=true");

        // Get all the sourceDatabaseMappingList where dbFieldPrecision is null
        defaultSourceDatabaseMappingShouldNotBeFound("dbFieldPrecision.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceDatabaseMappingsByDbFieldPrecisionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get all the sourceDatabaseMappingList where dbFieldPrecision greater than or equals to DEFAULT_DB_FIELD_PRECISION
        defaultSourceDatabaseMappingShouldBeFound("dbFieldPrecision.greaterOrEqualThan=" + DEFAULT_DB_FIELD_PRECISION);

        // Get all the sourceDatabaseMappingList where dbFieldPrecision greater than or equals to UPDATED_DB_FIELD_PRECISION
        defaultSourceDatabaseMappingShouldNotBeFound("dbFieldPrecision.greaterOrEqualThan=" + UPDATED_DB_FIELD_PRECISION);
    }

    @Test
    @Transactional
    public void getAllSourceDatabaseMappingsByDbFieldPrecisionIsLessThanSomething() throws Exception {
        // Initialize the database
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);

        // Get all the sourceDatabaseMappingList where dbFieldPrecision less than or equals to DEFAULT_DB_FIELD_PRECISION
        defaultSourceDatabaseMappingShouldNotBeFound("dbFieldPrecision.lessThan=" + DEFAULT_DB_FIELD_PRECISION);

        // Get all the sourceDatabaseMappingList where dbFieldPrecision less than or equals to UPDATED_DB_FIELD_PRECISION
        defaultSourceDatabaseMappingShouldBeFound("dbFieldPrecision.lessThan=" + UPDATED_DB_FIELD_PRECISION);
    }


    @Test
    @Transactional
    public void getAllSourceDatabaseMappingsBySourceDatabaseIsEqualToSomething() throws Exception {
        // Initialize the database
        SourceDatabase sourceDatabase = SourceDatabaseResourceIntTest.createEntity(em);
        em.persist(sourceDatabase);
        em.flush();
        sourceDatabaseMapping.setSourceDatabase(sourceDatabase);
        sourceDatabaseMappingRepository.saveAndFlush(sourceDatabaseMapping);
        Long sourceDatabaseId = sourceDatabase.getId();

        // Get all the sourceDatabaseMappingList where sourceDatabase equals to sourceDatabaseId
        defaultSourceDatabaseMappingShouldBeFound("sourceDatabaseId.equals=" + sourceDatabaseId);

        // Get all the sourceDatabaseMappingList where sourceDatabase equals to sourceDatabaseId + 1
        defaultSourceDatabaseMappingShouldNotBeFound("sourceDatabaseId.equals=" + (sourceDatabaseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSourceDatabaseMappingShouldBeFound(String filter) throws Exception {
        restSourceDatabaseMappingMockMvc.perform(get("/api/source-database-mappings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sourceDatabaseMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].dbColumnName").value(hasItem(DEFAULT_DB_COLUMN_NAME.toString())))
            .andExpect(jsonPath("$.[*].dbDataType").value(hasItem(DEFAULT_DB_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dbFieldScale").value(hasItem(DEFAULT_DB_FIELD_SCALE)))
            .andExpect(jsonPath("$.[*].dbFieldPrecision").value(hasItem(DEFAULT_DB_FIELD_PRECISION)));

        // Check, that the count call also returns 1
        restSourceDatabaseMappingMockMvc.perform(get("/api/source-database-mappings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSourceDatabaseMappingShouldNotBeFound(String filter) throws Exception {
        restSourceDatabaseMappingMockMvc.perform(get("/api/source-database-mappings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSourceDatabaseMappingMockMvc.perform(get("/api/source-database-mappings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSourceDatabaseMapping() throws Exception {
        // Get the sourceDatabaseMapping
        restSourceDatabaseMappingMockMvc.perform(get("/api/source-database-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSourceDatabaseMapping() throws Exception {
        // Initialize the database
        sourceDatabaseMappingService.save(sourceDatabaseMapping);

        int databaseSizeBeforeUpdate = sourceDatabaseMappingRepository.findAll().size();

        // Update the sourceDatabaseMapping
        SourceDatabaseMapping updatedSourceDatabaseMapping = sourceDatabaseMappingRepository.findById(sourceDatabaseMapping.getId()).get();
        // Disconnect from session so that the updates on updatedSourceDatabaseMapping are not directly saved in db
        em.detach(updatedSourceDatabaseMapping);
        updatedSourceDatabaseMapping
            .dbColumnName(UPDATED_DB_COLUMN_NAME)
            .dbDataType(UPDATED_DB_DATA_TYPE)
            .dbFieldScale(UPDATED_DB_FIELD_SCALE)
            .dbFieldPrecision(UPDATED_DB_FIELD_PRECISION);

        restSourceDatabaseMappingMockMvc.perform(put("/api/source-database-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSourceDatabaseMapping)))
            .andExpect(status().isOk());

        // Validate the SourceDatabaseMapping in the database
        List<SourceDatabaseMapping> sourceDatabaseMappingList = sourceDatabaseMappingRepository.findAll();
        assertThat(sourceDatabaseMappingList).hasSize(databaseSizeBeforeUpdate);
        SourceDatabaseMapping testSourceDatabaseMapping = sourceDatabaseMappingList.get(sourceDatabaseMappingList.size() - 1);
        assertThat(testSourceDatabaseMapping.getDbColumnName()).isEqualTo(UPDATED_DB_COLUMN_NAME);
        assertThat(testSourceDatabaseMapping.getDbDataType()).isEqualTo(UPDATED_DB_DATA_TYPE);
        assertThat(testSourceDatabaseMapping.getDbFieldScale()).isEqualTo(UPDATED_DB_FIELD_SCALE);
        assertThat(testSourceDatabaseMapping.getDbFieldPrecision()).isEqualTo(UPDATED_DB_FIELD_PRECISION);
    }

    @Test
    @Transactional
    public void updateNonExistingSourceDatabaseMapping() throws Exception {
        int databaseSizeBeforeUpdate = sourceDatabaseMappingRepository.findAll().size();

        // Create the SourceDatabaseMapping

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSourceDatabaseMappingMockMvc.perform(put("/api/source-database-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceDatabaseMapping)))
            .andExpect(status().isBadRequest());

        // Validate the SourceDatabaseMapping in the database
        List<SourceDatabaseMapping> sourceDatabaseMappingList = sourceDatabaseMappingRepository.findAll();
        assertThat(sourceDatabaseMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSourceDatabaseMapping() throws Exception {
        // Initialize the database
        sourceDatabaseMappingService.save(sourceDatabaseMapping);

        int databaseSizeBeforeDelete = sourceDatabaseMappingRepository.findAll().size();

        // Get the sourceDatabaseMapping
        restSourceDatabaseMappingMockMvc.perform(delete("/api/source-database-mappings/{id}", sourceDatabaseMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SourceDatabaseMapping> sourceDatabaseMappingList = sourceDatabaseMappingRepository.findAll();
        assertThat(sourceDatabaseMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SourceDatabaseMapping.class);
        SourceDatabaseMapping sourceDatabaseMapping1 = new SourceDatabaseMapping();
        sourceDatabaseMapping1.setId(1L);
        SourceDatabaseMapping sourceDatabaseMapping2 = new SourceDatabaseMapping();
        sourceDatabaseMapping2.setId(sourceDatabaseMapping1.getId());
        assertThat(sourceDatabaseMapping1).isEqualTo(sourceDatabaseMapping2);
        sourceDatabaseMapping2.setId(2L);
        assertThat(sourceDatabaseMapping1).isNotEqualTo(sourceDatabaseMapping2);
        sourceDatabaseMapping1.setId(null);
        assertThat(sourceDatabaseMapping1).isNotEqualTo(sourceDatabaseMapping2);
    }
}
