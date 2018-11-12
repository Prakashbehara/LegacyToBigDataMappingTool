package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyApp;

import com.mycompany.myapp.domain.DataModel;
import com.mycompany.myapp.domain.DataModelMapping;
import com.mycompany.myapp.repository.DataModelRepository;
import com.mycompany.myapp.service.DataModelService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.DataModelCriteria;
import com.mycompany.myapp.service.DataModelQueryService;

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

import com.mycompany.myapp.domain.enumeration.Domain;
/**
 * Test class for the DataModelResource REST controller.
 *
 * @see DataModelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApp.class)
public class DataModelResourceIntTest {

    private static final String DEFAULT_ENTITIY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITIY_NAME = "BBBBBBBBBB";

    private static final Domain DEFAULT_DOMAIN = Domain.POLICY;
    private static final Domain UPDATED_DOMAIN = Domain.FINANCE;

    @Autowired
    private DataModelRepository dataModelRepository;
    
    @Autowired
    private DataModelService dataModelService;

    @Autowired
    private DataModelQueryService dataModelQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDataModelMockMvc;

    private DataModel dataModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataModelResource dataModelResource = new DataModelResource(dataModelService, dataModelQueryService);
        this.restDataModelMockMvc = MockMvcBuilders.standaloneSetup(dataModelResource)
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
    public static DataModel createEntity(EntityManager em) {
        DataModel dataModel = new DataModel()
            .entitiyName(DEFAULT_ENTITIY_NAME)
            .domain(DEFAULT_DOMAIN);
        return dataModel;
    }

    @Before
    public void initTest() {
        dataModel = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataModel() throws Exception {
        int databaseSizeBeforeCreate = dataModelRepository.findAll().size();

        // Create the DataModel
        restDataModelMockMvc.perform(post("/api/data-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataModel)))
            .andExpect(status().isCreated());

        // Validate the DataModel in the database
        List<DataModel> dataModelList = dataModelRepository.findAll();
        assertThat(dataModelList).hasSize(databaseSizeBeforeCreate + 1);
        DataModel testDataModel = dataModelList.get(dataModelList.size() - 1);
        assertThat(testDataModel.getEntitiyName()).isEqualTo(DEFAULT_ENTITIY_NAME);
        assertThat(testDataModel.getDomain()).isEqualTo(DEFAULT_DOMAIN);
    }

    @Test
    @Transactional
    public void createDataModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataModelRepository.findAll().size();

        // Create the DataModel with an existing ID
        dataModel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataModelMockMvc.perform(post("/api/data-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataModel)))
            .andExpect(status().isBadRequest());

        // Validate the DataModel in the database
        List<DataModel> dataModelList = dataModelRepository.findAll();
        assertThat(dataModelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDataModels() throws Exception {
        // Initialize the database
        dataModelRepository.saveAndFlush(dataModel);

        // Get all the dataModelList
        restDataModelMockMvc.perform(get("/api/data-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].entitiyName").value(hasItem(DEFAULT_ENTITIY_NAME.toString())))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN.toString())));
    }
    
    @Test
    @Transactional
    public void getDataModel() throws Exception {
        // Initialize the database
        dataModelRepository.saveAndFlush(dataModel);

        // Get the dataModel
        restDataModelMockMvc.perform(get("/api/data-models/{id}", dataModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataModel.getId().intValue()))
            .andExpect(jsonPath("$.entitiyName").value(DEFAULT_ENTITIY_NAME.toString()))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN.toString()));
    }

    @Test
    @Transactional
    public void getAllDataModelsByEntitiyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dataModelRepository.saveAndFlush(dataModel);

        // Get all the dataModelList where entitiyName equals to DEFAULT_ENTITIY_NAME
        defaultDataModelShouldBeFound("entitiyName.equals=" + DEFAULT_ENTITIY_NAME);

        // Get all the dataModelList where entitiyName equals to UPDATED_ENTITIY_NAME
        defaultDataModelShouldNotBeFound("entitiyName.equals=" + UPDATED_ENTITIY_NAME);
    }

    @Test
    @Transactional
    public void getAllDataModelsByEntitiyNameIsInShouldWork() throws Exception {
        // Initialize the database
        dataModelRepository.saveAndFlush(dataModel);

        // Get all the dataModelList where entitiyName in DEFAULT_ENTITIY_NAME or UPDATED_ENTITIY_NAME
        defaultDataModelShouldBeFound("entitiyName.in=" + DEFAULT_ENTITIY_NAME + "," + UPDATED_ENTITIY_NAME);

        // Get all the dataModelList where entitiyName equals to UPDATED_ENTITIY_NAME
        defaultDataModelShouldNotBeFound("entitiyName.in=" + UPDATED_ENTITIY_NAME);
    }

    @Test
    @Transactional
    public void getAllDataModelsByEntitiyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataModelRepository.saveAndFlush(dataModel);

        // Get all the dataModelList where entitiyName is not null
        defaultDataModelShouldBeFound("entitiyName.specified=true");

        // Get all the dataModelList where entitiyName is null
        defaultDataModelShouldNotBeFound("entitiyName.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataModelsByDomainIsEqualToSomething() throws Exception {
        // Initialize the database
        dataModelRepository.saveAndFlush(dataModel);

        // Get all the dataModelList where domain equals to DEFAULT_DOMAIN
        defaultDataModelShouldBeFound("domain.equals=" + DEFAULT_DOMAIN);

        // Get all the dataModelList where domain equals to UPDATED_DOMAIN
        defaultDataModelShouldNotBeFound("domain.equals=" + UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void getAllDataModelsByDomainIsInShouldWork() throws Exception {
        // Initialize the database
        dataModelRepository.saveAndFlush(dataModel);

        // Get all the dataModelList where domain in DEFAULT_DOMAIN or UPDATED_DOMAIN
        defaultDataModelShouldBeFound("domain.in=" + DEFAULT_DOMAIN + "," + UPDATED_DOMAIN);

        // Get all the dataModelList where domain equals to UPDATED_DOMAIN
        defaultDataModelShouldNotBeFound("domain.in=" + UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void getAllDataModelsByDomainIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataModelRepository.saveAndFlush(dataModel);

        // Get all the dataModelList where domain is not null
        defaultDataModelShouldBeFound("domain.specified=true");

        // Get all the dataModelList where domain is null
        defaultDataModelShouldNotBeFound("domain.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataModelsByDataModelMappingIsEqualToSomething() throws Exception {
        // Initialize the database
        DataModelMapping dataModelMapping = DataModelMappingResourceIntTest.createEntity(em);
        em.persist(dataModelMapping);
        em.flush();
        dataModel.addDataModelMapping(dataModelMapping);
        dataModelRepository.saveAndFlush(dataModel);
        Long dataModelMappingId = dataModelMapping.getId();

        // Get all the dataModelList where dataModelMapping equals to dataModelMappingId
        defaultDataModelShouldBeFound("dataModelMappingId.equals=" + dataModelMappingId);

        // Get all the dataModelList where dataModelMapping equals to dataModelMappingId + 1
        defaultDataModelShouldNotBeFound("dataModelMappingId.equals=" + (dataModelMappingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDataModelShouldBeFound(String filter) throws Exception {
        restDataModelMockMvc.perform(get("/api/data-models?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].entitiyName").value(hasItem(DEFAULT_ENTITIY_NAME.toString())))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN.toString())));

        // Check, that the count call also returns 1
        restDataModelMockMvc.perform(get("/api/data-models/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDataModelShouldNotBeFound(String filter) throws Exception {
        restDataModelMockMvc.perform(get("/api/data-models?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDataModelMockMvc.perform(get("/api/data-models/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDataModel() throws Exception {
        // Get the dataModel
        restDataModelMockMvc.perform(get("/api/data-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataModel() throws Exception {
        // Initialize the database
        dataModelService.save(dataModel);

        int databaseSizeBeforeUpdate = dataModelRepository.findAll().size();

        // Update the dataModel
        DataModel updatedDataModel = dataModelRepository.findById(dataModel.getId()).get();
        // Disconnect from session so that the updates on updatedDataModel are not directly saved in db
        em.detach(updatedDataModel);
        updatedDataModel
            .entitiyName(UPDATED_ENTITIY_NAME)
            .domain(UPDATED_DOMAIN);

        restDataModelMockMvc.perform(put("/api/data-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDataModel)))
            .andExpect(status().isOk());

        // Validate the DataModel in the database
        List<DataModel> dataModelList = dataModelRepository.findAll();
        assertThat(dataModelList).hasSize(databaseSizeBeforeUpdate);
        DataModel testDataModel = dataModelList.get(dataModelList.size() - 1);
        assertThat(testDataModel.getEntitiyName()).isEqualTo(UPDATED_ENTITIY_NAME);
        assertThat(testDataModel.getDomain()).isEqualTo(UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void updateNonExistingDataModel() throws Exception {
        int databaseSizeBeforeUpdate = dataModelRepository.findAll().size();

        // Create the DataModel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataModelMockMvc.perform(put("/api/data-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataModel)))
            .andExpect(status().isBadRequest());

        // Validate the DataModel in the database
        List<DataModel> dataModelList = dataModelRepository.findAll();
        assertThat(dataModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataModel() throws Exception {
        // Initialize the database
        dataModelService.save(dataModel);

        int databaseSizeBeforeDelete = dataModelRepository.findAll().size();

        // Get the dataModel
        restDataModelMockMvc.perform(delete("/api/data-models/{id}", dataModel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DataModel> dataModelList = dataModelRepository.findAll();
        assertThat(dataModelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataModel.class);
        DataModel dataModel1 = new DataModel();
        dataModel1.setId(1L);
        DataModel dataModel2 = new DataModel();
        dataModel2.setId(dataModel1.getId());
        assertThat(dataModel1).isEqualTo(dataModel2);
        dataModel2.setId(2L);
        assertThat(dataModel1).isNotEqualTo(dataModel2);
        dataModel1.setId(null);
        assertThat(dataModel1).isNotEqualTo(dataModel2);
    }
}
