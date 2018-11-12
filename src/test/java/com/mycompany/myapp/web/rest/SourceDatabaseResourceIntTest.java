package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyApp;

import com.mycompany.myapp.domain.SourceDatabase;
import com.mycompany.myapp.domain.Application;
import com.mycompany.myapp.domain.SourceDatabaseMapping;
import com.mycompany.myapp.repository.SourceDatabaseRepository;
import com.mycompany.myapp.service.SourceDatabaseService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.SourceDatabaseCriteria;
import com.mycompany.myapp.service.SourceDatabaseQueryService;

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

import com.mycompany.myapp.domain.enumeration.TableType;
/**
 * Test class for the SourceDatabaseResource REST controller.
 *
 * @see SourceDatabaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApp.class)
public class SourceDatabaseResourceIntTest {

    private static final String DEFAULT_TABLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEMA = "AAAAAAAAAA";
    private static final String UPDATED_SCHEMA = "BBBBBBBBBB";

    private static final TableType DEFAULT_TABLE_TYPE = TableType.FEED_TABLE;
    private static final TableType UPDATED_TABLE_TYPE = TableType.REF_DATA;

    @Autowired
    private SourceDatabaseRepository sourceDatabaseRepository;
    
    @Autowired
    private SourceDatabaseService sourceDatabaseService;

    @Autowired
    private SourceDatabaseQueryService sourceDatabaseQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSourceDatabaseMockMvc;

    private SourceDatabase sourceDatabase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SourceDatabaseResource sourceDatabaseResource = new SourceDatabaseResource(sourceDatabaseService, sourceDatabaseQueryService);
        this.restSourceDatabaseMockMvc = MockMvcBuilders.standaloneSetup(sourceDatabaseResource)
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
    public static SourceDatabase createEntity(EntityManager em) {
        SourceDatabase sourceDatabase = new SourceDatabase()
            .tableName(DEFAULT_TABLE_NAME)
            .schema(DEFAULT_SCHEMA)
            .tableType(DEFAULT_TABLE_TYPE);
        return sourceDatabase;
    }

    @Before
    public void initTest() {
        sourceDatabase = createEntity(em);
    }

    @Test
    @Transactional
    public void createSourceDatabase() throws Exception {
        int databaseSizeBeforeCreate = sourceDatabaseRepository.findAll().size();

        // Create the SourceDatabase
        restSourceDatabaseMockMvc.perform(post("/api/source-databases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceDatabase)))
            .andExpect(status().isCreated());

        // Validate the SourceDatabase in the database
        List<SourceDatabase> sourceDatabaseList = sourceDatabaseRepository.findAll();
        assertThat(sourceDatabaseList).hasSize(databaseSizeBeforeCreate + 1);
        SourceDatabase testSourceDatabase = sourceDatabaseList.get(sourceDatabaseList.size() - 1);
        assertThat(testSourceDatabase.getTableName()).isEqualTo(DEFAULT_TABLE_NAME);
        assertThat(testSourceDatabase.getSchema()).isEqualTo(DEFAULT_SCHEMA);
        assertThat(testSourceDatabase.getTableType()).isEqualTo(DEFAULT_TABLE_TYPE);
    }

    @Test
    @Transactional
    public void createSourceDatabaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sourceDatabaseRepository.findAll().size();

        // Create the SourceDatabase with an existing ID
        sourceDatabase.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSourceDatabaseMockMvc.perform(post("/api/source-databases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceDatabase)))
            .andExpect(status().isBadRequest());

        // Validate the SourceDatabase in the database
        List<SourceDatabase> sourceDatabaseList = sourceDatabaseRepository.findAll();
        assertThat(sourceDatabaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTableNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceDatabaseRepository.findAll().size();
        // set the field null
        sourceDatabase.setTableName(null);

        // Create the SourceDatabase, which fails.

        restSourceDatabaseMockMvc.perform(post("/api/source-databases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceDatabase)))
            .andExpect(status().isBadRequest());

        List<SourceDatabase> sourceDatabaseList = sourceDatabaseRepository.findAll();
        assertThat(sourceDatabaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSourceDatabases() throws Exception {
        // Initialize the database
        sourceDatabaseRepository.saveAndFlush(sourceDatabase);

        // Get all the sourceDatabaseList
        restSourceDatabaseMockMvc.perform(get("/api/source-databases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sourceDatabase.getId().intValue())))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].schema").value(hasItem(DEFAULT_SCHEMA.toString())))
            .andExpect(jsonPath("$.[*].tableType").value(hasItem(DEFAULT_TABLE_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getSourceDatabase() throws Exception {
        // Initialize the database
        sourceDatabaseRepository.saveAndFlush(sourceDatabase);

        // Get the sourceDatabase
        restSourceDatabaseMockMvc.perform(get("/api/source-databases/{id}", sourceDatabase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sourceDatabase.getId().intValue()))
            .andExpect(jsonPath("$.tableName").value(DEFAULT_TABLE_NAME.toString()))
            .andExpect(jsonPath("$.schema").value(DEFAULT_SCHEMA.toString()))
            .andExpect(jsonPath("$.tableType").value(DEFAULT_TABLE_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllSourceDatabasesByTableNameIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceDatabaseRepository.saveAndFlush(sourceDatabase);

        // Get all the sourceDatabaseList where tableName equals to DEFAULT_TABLE_NAME
        defaultSourceDatabaseShouldBeFound("tableName.equals=" + DEFAULT_TABLE_NAME);

        // Get all the sourceDatabaseList where tableName equals to UPDATED_TABLE_NAME
        defaultSourceDatabaseShouldNotBeFound("tableName.equals=" + UPDATED_TABLE_NAME);
    }

    @Test
    @Transactional
    public void getAllSourceDatabasesByTableNameIsInShouldWork() throws Exception {
        // Initialize the database
        sourceDatabaseRepository.saveAndFlush(sourceDatabase);

        // Get all the sourceDatabaseList where tableName in DEFAULT_TABLE_NAME or UPDATED_TABLE_NAME
        defaultSourceDatabaseShouldBeFound("tableName.in=" + DEFAULT_TABLE_NAME + "," + UPDATED_TABLE_NAME);

        // Get all the sourceDatabaseList where tableName equals to UPDATED_TABLE_NAME
        defaultSourceDatabaseShouldNotBeFound("tableName.in=" + UPDATED_TABLE_NAME);
    }

    @Test
    @Transactional
    public void getAllSourceDatabasesByTableNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceDatabaseRepository.saveAndFlush(sourceDatabase);

        // Get all the sourceDatabaseList where tableName is not null
        defaultSourceDatabaseShouldBeFound("tableName.specified=true");

        // Get all the sourceDatabaseList where tableName is null
        defaultSourceDatabaseShouldNotBeFound("tableName.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceDatabasesBySchemaIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceDatabaseRepository.saveAndFlush(sourceDatabase);

        // Get all the sourceDatabaseList where schema equals to DEFAULT_SCHEMA
        defaultSourceDatabaseShouldBeFound("schema.equals=" + DEFAULT_SCHEMA);

        // Get all the sourceDatabaseList where schema equals to UPDATED_SCHEMA
        defaultSourceDatabaseShouldNotBeFound("schema.equals=" + UPDATED_SCHEMA);
    }

    @Test
    @Transactional
    public void getAllSourceDatabasesBySchemaIsInShouldWork() throws Exception {
        // Initialize the database
        sourceDatabaseRepository.saveAndFlush(sourceDatabase);

        // Get all the sourceDatabaseList where schema in DEFAULT_SCHEMA or UPDATED_SCHEMA
        defaultSourceDatabaseShouldBeFound("schema.in=" + DEFAULT_SCHEMA + "," + UPDATED_SCHEMA);

        // Get all the sourceDatabaseList where schema equals to UPDATED_SCHEMA
        defaultSourceDatabaseShouldNotBeFound("schema.in=" + UPDATED_SCHEMA);
    }

    @Test
    @Transactional
    public void getAllSourceDatabasesBySchemaIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceDatabaseRepository.saveAndFlush(sourceDatabase);

        // Get all the sourceDatabaseList where schema is not null
        defaultSourceDatabaseShouldBeFound("schema.specified=true");

        // Get all the sourceDatabaseList where schema is null
        defaultSourceDatabaseShouldNotBeFound("schema.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceDatabasesByTableTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceDatabaseRepository.saveAndFlush(sourceDatabase);

        // Get all the sourceDatabaseList where tableType equals to DEFAULT_TABLE_TYPE
        defaultSourceDatabaseShouldBeFound("tableType.equals=" + DEFAULT_TABLE_TYPE);

        // Get all the sourceDatabaseList where tableType equals to UPDATED_TABLE_TYPE
        defaultSourceDatabaseShouldNotBeFound("tableType.equals=" + UPDATED_TABLE_TYPE);
    }

    @Test
    @Transactional
    public void getAllSourceDatabasesByTableTypeIsInShouldWork() throws Exception {
        // Initialize the database
        sourceDatabaseRepository.saveAndFlush(sourceDatabase);

        // Get all the sourceDatabaseList where tableType in DEFAULT_TABLE_TYPE or UPDATED_TABLE_TYPE
        defaultSourceDatabaseShouldBeFound("tableType.in=" + DEFAULT_TABLE_TYPE + "," + UPDATED_TABLE_TYPE);

        // Get all the sourceDatabaseList where tableType equals to UPDATED_TABLE_TYPE
        defaultSourceDatabaseShouldNotBeFound("tableType.in=" + UPDATED_TABLE_TYPE);
    }

    @Test
    @Transactional
    public void getAllSourceDatabasesByTableTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceDatabaseRepository.saveAndFlush(sourceDatabase);

        // Get all the sourceDatabaseList where tableType is not null
        defaultSourceDatabaseShouldBeFound("tableType.specified=true");

        // Get all the sourceDatabaseList where tableType is null
        defaultSourceDatabaseShouldNotBeFound("tableType.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceDatabasesByApplicationIsEqualToSomething() throws Exception {
        // Initialize the database
        Application application = ApplicationResourceIntTest.createEntity(em);
        em.persist(application);
        em.flush();
        sourceDatabase.setApplication(application);
        sourceDatabaseRepository.saveAndFlush(sourceDatabase);
        Long applicationId = application.getId();

        // Get all the sourceDatabaseList where application equals to applicationId
        defaultSourceDatabaseShouldBeFound("applicationId.equals=" + applicationId);

        // Get all the sourceDatabaseList where application equals to applicationId + 1
        defaultSourceDatabaseShouldNotBeFound("applicationId.equals=" + (applicationId + 1));
    }


    @Test
    @Transactional
    public void getAllSourceDatabasesBySourceDatabaseMappingIsEqualToSomething() throws Exception {
        // Initialize the database
        SourceDatabaseMapping sourceDatabaseMapping = SourceDatabaseMappingResourceIntTest.createEntity(em);
        em.persist(sourceDatabaseMapping);
        em.flush();
        sourceDatabase.addSourceDatabaseMapping(sourceDatabaseMapping);
        sourceDatabaseRepository.saveAndFlush(sourceDatabase);
        Long sourceDatabaseMappingId = sourceDatabaseMapping.getId();

        // Get all the sourceDatabaseList where sourceDatabaseMapping equals to sourceDatabaseMappingId
        defaultSourceDatabaseShouldBeFound("sourceDatabaseMappingId.equals=" + sourceDatabaseMappingId);

        // Get all the sourceDatabaseList where sourceDatabaseMapping equals to sourceDatabaseMappingId + 1
        defaultSourceDatabaseShouldNotBeFound("sourceDatabaseMappingId.equals=" + (sourceDatabaseMappingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSourceDatabaseShouldBeFound(String filter) throws Exception {
        restSourceDatabaseMockMvc.perform(get("/api/source-databases?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sourceDatabase.getId().intValue())))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].schema").value(hasItem(DEFAULT_SCHEMA.toString())))
            .andExpect(jsonPath("$.[*].tableType").value(hasItem(DEFAULT_TABLE_TYPE.toString())));

        // Check, that the count call also returns 1
        restSourceDatabaseMockMvc.perform(get("/api/source-databases/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSourceDatabaseShouldNotBeFound(String filter) throws Exception {
        restSourceDatabaseMockMvc.perform(get("/api/source-databases?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSourceDatabaseMockMvc.perform(get("/api/source-databases/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSourceDatabase() throws Exception {
        // Get the sourceDatabase
        restSourceDatabaseMockMvc.perform(get("/api/source-databases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSourceDatabase() throws Exception {
        // Initialize the database
        sourceDatabaseService.save(sourceDatabase);

        int databaseSizeBeforeUpdate = sourceDatabaseRepository.findAll().size();

        // Update the sourceDatabase
        SourceDatabase updatedSourceDatabase = sourceDatabaseRepository.findById(sourceDatabase.getId()).get();
        // Disconnect from session so that the updates on updatedSourceDatabase are not directly saved in db
        em.detach(updatedSourceDatabase);
        updatedSourceDatabase
            .tableName(UPDATED_TABLE_NAME)
            .schema(UPDATED_SCHEMA)
            .tableType(UPDATED_TABLE_TYPE);

        restSourceDatabaseMockMvc.perform(put("/api/source-databases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSourceDatabase)))
            .andExpect(status().isOk());

        // Validate the SourceDatabase in the database
        List<SourceDatabase> sourceDatabaseList = sourceDatabaseRepository.findAll();
        assertThat(sourceDatabaseList).hasSize(databaseSizeBeforeUpdate);
        SourceDatabase testSourceDatabase = sourceDatabaseList.get(sourceDatabaseList.size() - 1);
        assertThat(testSourceDatabase.getTableName()).isEqualTo(UPDATED_TABLE_NAME);
        assertThat(testSourceDatabase.getSchema()).isEqualTo(UPDATED_SCHEMA);
        assertThat(testSourceDatabase.getTableType()).isEqualTo(UPDATED_TABLE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSourceDatabase() throws Exception {
        int databaseSizeBeforeUpdate = sourceDatabaseRepository.findAll().size();

        // Create the SourceDatabase

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSourceDatabaseMockMvc.perform(put("/api/source-databases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceDatabase)))
            .andExpect(status().isBadRequest());

        // Validate the SourceDatabase in the database
        List<SourceDatabase> sourceDatabaseList = sourceDatabaseRepository.findAll();
        assertThat(sourceDatabaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSourceDatabase() throws Exception {
        // Initialize the database
        sourceDatabaseService.save(sourceDatabase);

        int databaseSizeBeforeDelete = sourceDatabaseRepository.findAll().size();

        // Get the sourceDatabase
        restSourceDatabaseMockMvc.perform(delete("/api/source-databases/{id}", sourceDatabase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SourceDatabase> sourceDatabaseList = sourceDatabaseRepository.findAll();
        assertThat(sourceDatabaseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SourceDatabase.class);
        SourceDatabase sourceDatabase1 = new SourceDatabase();
        sourceDatabase1.setId(1L);
        SourceDatabase sourceDatabase2 = new SourceDatabase();
        sourceDatabase2.setId(sourceDatabase1.getId());
        assertThat(sourceDatabase1).isEqualTo(sourceDatabase2);
        sourceDatabase2.setId(2L);
        assertThat(sourceDatabase1).isNotEqualTo(sourceDatabase2);
        sourceDatabase1.setId(null);
        assertThat(sourceDatabase1).isNotEqualTo(sourceDatabase2);
    }
}
