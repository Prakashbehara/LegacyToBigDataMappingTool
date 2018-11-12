package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyApp;

import com.mycompany.myapp.domain.DataAsset;
import com.mycompany.myapp.domain.Application;
import com.mycompany.myapp.domain.SourceFeed;
import com.mycompany.myapp.domain.SourceDatabase;
import com.mycompany.myapp.repository.DataAssetRepository;
import com.mycompany.myapp.service.DataAssetService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.DataAssetCriteria;
import com.mycompany.myapp.service.DataAssetQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.DataAssetType;
import com.mycompany.myapp.domain.enumeration.Frequency;
/**
 * Test class for the DataAssetResource REST controller.
 *
 * @see DataAssetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApp.class)
public class DataAssetResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_FILE_NAME = "BBBBBBBBBB";

    private static final DataAssetType DEFAULT_TYPE = DataAssetType.CONTROL_REPORTS;
    private static final DataAssetType UPDATED_TYPE = DataAssetType.EXCEPTION_REPORTS;

    private static final Frequency DEFAULT_FREQUENCY = Frequency.DAILY;
    private static final Frequency UPDATED_FREQUENCY = Frequency.WEEKLY;

    private static final String DEFAULT_STORED_PROCEDURE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STORED_PROCEDURE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_QUERY_LOGIC = "AAAAAAAAAA";
    private static final String UPDATED_QUERY_LOGIC = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_EDH_ASSET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EDH_ASSET_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_DISTRIBUTION = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_DISTRIBUTION = "BBBBBBBBBB";

    @Autowired
    private DataAssetRepository dataAssetRepository;

    @Mock
    private DataAssetRepository dataAssetRepositoryMock;
    

    @Mock
    private DataAssetService dataAssetServiceMock;

    @Autowired
    private DataAssetService dataAssetService;

    @Autowired
    private DataAssetQueryService dataAssetQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDataAssetMockMvc;

    private DataAsset dataAsset;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataAssetResource dataAssetResource = new DataAssetResource(dataAssetService, dataAssetQueryService);
        this.restDataAssetMockMvc = MockMvcBuilders.standaloneSetup(dataAssetResource)
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
    public static DataAsset createEntity(EntityManager em) {
        DataAsset dataAsset = new DataAsset()
            .name(DEFAULT_NAME)
            .assetFileName(DEFAULT_ASSET_FILE_NAME)
            .type(DEFAULT_TYPE)
            .frequency(DEFAULT_FREQUENCY)
            .storedProcedureName(DEFAULT_STORED_PROCEDURE_NAME)
            .queryLogic(DEFAULT_QUERY_LOGIC)
            .remarks(DEFAULT_REMARKS)
            .edhAssetName(DEFAULT_EDH_ASSET_NAME)
            .emailDistribution(DEFAULT_EMAIL_DISTRIBUTION);
        return dataAsset;
    }

    @Before
    public void initTest() {
        dataAsset = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataAsset() throws Exception {
        int databaseSizeBeforeCreate = dataAssetRepository.findAll().size();

        // Create the DataAsset
        restDataAssetMockMvc.perform(post("/api/data-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataAsset)))
            .andExpect(status().isCreated());

        // Validate the DataAsset in the database
        List<DataAsset> dataAssetList = dataAssetRepository.findAll();
        assertThat(dataAssetList).hasSize(databaseSizeBeforeCreate + 1);
        DataAsset testDataAsset = dataAssetList.get(dataAssetList.size() - 1);
        assertThat(testDataAsset.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataAsset.getAssetFileName()).isEqualTo(DEFAULT_ASSET_FILE_NAME);
        assertThat(testDataAsset.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDataAsset.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
        assertThat(testDataAsset.getStoredProcedureName()).isEqualTo(DEFAULT_STORED_PROCEDURE_NAME);
        assertThat(testDataAsset.getQueryLogic()).isEqualTo(DEFAULT_QUERY_LOGIC);
        assertThat(testDataAsset.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testDataAsset.getEdhAssetName()).isEqualTo(DEFAULT_EDH_ASSET_NAME);
        assertThat(testDataAsset.getEmailDistribution()).isEqualTo(DEFAULT_EMAIL_DISTRIBUTION);
    }

    @Test
    @Transactional
    public void createDataAssetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataAssetRepository.findAll().size();

        // Create the DataAsset with an existing ID
        dataAsset.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataAssetMockMvc.perform(post("/api/data-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataAsset)))
            .andExpect(status().isBadRequest());

        // Validate the DataAsset in the database
        List<DataAsset> dataAssetList = dataAssetRepository.findAll();
        assertThat(dataAssetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataAssetRepository.findAll().size();
        // set the field null
        dataAsset.setName(null);

        // Create the DataAsset, which fails.

        restDataAssetMockMvc.perform(post("/api/data-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataAsset)))
            .andExpect(status().isBadRequest());

        List<DataAsset> dataAssetList = dataAssetRepository.findAll();
        assertThat(dataAssetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAssetFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataAssetRepository.findAll().size();
        // set the field null
        dataAsset.setAssetFileName(null);

        // Create the DataAsset, which fails.

        restDataAssetMockMvc.perform(post("/api/data-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataAsset)))
            .andExpect(status().isBadRequest());

        List<DataAsset> dataAssetList = dataAssetRepository.findAll();
        assertThat(dataAssetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataAssetRepository.findAll().size();
        // set the field null
        dataAsset.setType(null);

        // Create the DataAsset, which fails.

        restDataAssetMockMvc.perform(post("/api/data-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataAsset)))
            .andExpect(status().isBadRequest());

        List<DataAsset> dataAssetList = dataAssetRepository.findAll();
        assertThat(dataAssetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFrequencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataAssetRepository.findAll().size();
        // set the field null
        dataAsset.setFrequency(null);

        // Create the DataAsset, which fails.

        restDataAssetMockMvc.perform(post("/api/data-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataAsset)))
            .andExpect(status().isBadRequest());

        List<DataAsset> dataAssetList = dataAssetRepository.findAll();
        assertThat(dataAssetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDataAssets() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList
        restDataAssetMockMvc.perform(get("/api/data-assets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataAsset.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].assetFileName").value(hasItem(DEFAULT_ASSET_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].storedProcedureName").value(hasItem(DEFAULT_STORED_PROCEDURE_NAME.toString())))
            .andExpect(jsonPath("$.[*].queryLogic").value(hasItem(DEFAULT_QUERY_LOGIC.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].edhAssetName").value(hasItem(DEFAULT_EDH_ASSET_NAME.toString())))
            .andExpect(jsonPath("$.[*].emailDistribution").value(hasItem(DEFAULT_EMAIL_DISTRIBUTION.toString())));
    }
    
    public void getAllDataAssetsWithEagerRelationshipsIsEnabled() throws Exception {
        DataAssetResource dataAssetResource = new DataAssetResource(dataAssetServiceMock, dataAssetQueryService);
        when(dataAssetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDataAssetMockMvc = MockMvcBuilders.standaloneSetup(dataAssetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDataAssetMockMvc.perform(get("/api/data-assets?eagerload=true"))
        .andExpect(status().isOk());

        verify(dataAssetServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllDataAssetsWithEagerRelationshipsIsNotEnabled() throws Exception {
        DataAssetResource dataAssetResource = new DataAssetResource(dataAssetServiceMock, dataAssetQueryService);
            when(dataAssetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restDataAssetMockMvc = MockMvcBuilders.standaloneSetup(dataAssetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDataAssetMockMvc.perform(get("/api/data-assets?eagerload=true"))
        .andExpect(status().isOk());

            verify(dataAssetServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDataAsset() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get the dataAsset
        restDataAssetMockMvc.perform(get("/api/data-assets/{id}", dataAsset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataAsset.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.assetFileName").value(DEFAULT_ASSET_FILE_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY.toString()))
            .andExpect(jsonPath("$.storedProcedureName").value(DEFAULT_STORED_PROCEDURE_NAME.toString()))
            .andExpect(jsonPath("$.queryLogic").value(DEFAULT_QUERY_LOGIC.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.edhAssetName").value(DEFAULT_EDH_ASSET_NAME.toString()))
            .andExpect(jsonPath("$.emailDistribution").value(DEFAULT_EMAIL_DISTRIBUTION.toString()));
    }

    @Test
    @Transactional
    public void getAllDataAssetsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where name equals to DEFAULT_NAME
        defaultDataAssetShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the dataAssetList where name equals to UPDATED_NAME
        defaultDataAssetShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDataAssetsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDataAssetShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the dataAssetList where name equals to UPDATED_NAME
        defaultDataAssetShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDataAssetsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where name is not null
        defaultDataAssetShouldBeFound("name.specified=true");

        // Get all the dataAssetList where name is null
        defaultDataAssetShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataAssetsByAssetFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where assetFileName equals to DEFAULT_ASSET_FILE_NAME
        defaultDataAssetShouldBeFound("assetFileName.equals=" + DEFAULT_ASSET_FILE_NAME);

        // Get all the dataAssetList where assetFileName equals to UPDATED_ASSET_FILE_NAME
        defaultDataAssetShouldNotBeFound("assetFileName.equals=" + UPDATED_ASSET_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllDataAssetsByAssetFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where assetFileName in DEFAULT_ASSET_FILE_NAME or UPDATED_ASSET_FILE_NAME
        defaultDataAssetShouldBeFound("assetFileName.in=" + DEFAULT_ASSET_FILE_NAME + "," + UPDATED_ASSET_FILE_NAME);

        // Get all the dataAssetList where assetFileName equals to UPDATED_ASSET_FILE_NAME
        defaultDataAssetShouldNotBeFound("assetFileName.in=" + UPDATED_ASSET_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllDataAssetsByAssetFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where assetFileName is not null
        defaultDataAssetShouldBeFound("assetFileName.specified=true");

        // Get all the dataAssetList where assetFileName is null
        defaultDataAssetShouldNotBeFound("assetFileName.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataAssetsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where type equals to DEFAULT_TYPE
        defaultDataAssetShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the dataAssetList where type equals to UPDATED_TYPE
        defaultDataAssetShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllDataAssetsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultDataAssetShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the dataAssetList where type equals to UPDATED_TYPE
        defaultDataAssetShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllDataAssetsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where type is not null
        defaultDataAssetShouldBeFound("type.specified=true");

        // Get all the dataAssetList where type is null
        defaultDataAssetShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataAssetsByFrequencyIsEqualToSomething() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where frequency equals to DEFAULT_FREQUENCY
        defaultDataAssetShouldBeFound("frequency.equals=" + DEFAULT_FREQUENCY);

        // Get all the dataAssetList where frequency equals to UPDATED_FREQUENCY
        defaultDataAssetShouldNotBeFound("frequency.equals=" + UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    public void getAllDataAssetsByFrequencyIsInShouldWork() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where frequency in DEFAULT_FREQUENCY or UPDATED_FREQUENCY
        defaultDataAssetShouldBeFound("frequency.in=" + DEFAULT_FREQUENCY + "," + UPDATED_FREQUENCY);

        // Get all the dataAssetList where frequency equals to UPDATED_FREQUENCY
        defaultDataAssetShouldNotBeFound("frequency.in=" + UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    public void getAllDataAssetsByFrequencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where frequency is not null
        defaultDataAssetShouldBeFound("frequency.specified=true");

        // Get all the dataAssetList where frequency is null
        defaultDataAssetShouldNotBeFound("frequency.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataAssetsByStoredProcedureNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where storedProcedureName equals to DEFAULT_STORED_PROCEDURE_NAME
        defaultDataAssetShouldBeFound("storedProcedureName.equals=" + DEFAULT_STORED_PROCEDURE_NAME);

        // Get all the dataAssetList where storedProcedureName equals to UPDATED_STORED_PROCEDURE_NAME
        defaultDataAssetShouldNotBeFound("storedProcedureName.equals=" + UPDATED_STORED_PROCEDURE_NAME);
    }

    @Test
    @Transactional
    public void getAllDataAssetsByStoredProcedureNameIsInShouldWork() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where storedProcedureName in DEFAULT_STORED_PROCEDURE_NAME or UPDATED_STORED_PROCEDURE_NAME
        defaultDataAssetShouldBeFound("storedProcedureName.in=" + DEFAULT_STORED_PROCEDURE_NAME + "," + UPDATED_STORED_PROCEDURE_NAME);

        // Get all the dataAssetList where storedProcedureName equals to UPDATED_STORED_PROCEDURE_NAME
        defaultDataAssetShouldNotBeFound("storedProcedureName.in=" + UPDATED_STORED_PROCEDURE_NAME);
    }

    @Test
    @Transactional
    public void getAllDataAssetsByStoredProcedureNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where storedProcedureName is not null
        defaultDataAssetShouldBeFound("storedProcedureName.specified=true");

        // Get all the dataAssetList where storedProcedureName is null
        defaultDataAssetShouldNotBeFound("storedProcedureName.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataAssetsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where remarks equals to DEFAULT_REMARKS
        defaultDataAssetShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the dataAssetList where remarks equals to UPDATED_REMARKS
        defaultDataAssetShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllDataAssetsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultDataAssetShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the dataAssetList where remarks equals to UPDATED_REMARKS
        defaultDataAssetShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllDataAssetsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where remarks is not null
        defaultDataAssetShouldBeFound("remarks.specified=true");

        // Get all the dataAssetList where remarks is null
        defaultDataAssetShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataAssetsByEdhAssetNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where edhAssetName equals to DEFAULT_EDH_ASSET_NAME
        defaultDataAssetShouldBeFound("edhAssetName.equals=" + DEFAULT_EDH_ASSET_NAME);

        // Get all the dataAssetList where edhAssetName equals to UPDATED_EDH_ASSET_NAME
        defaultDataAssetShouldNotBeFound("edhAssetName.equals=" + UPDATED_EDH_ASSET_NAME);
    }

    @Test
    @Transactional
    public void getAllDataAssetsByEdhAssetNameIsInShouldWork() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where edhAssetName in DEFAULT_EDH_ASSET_NAME or UPDATED_EDH_ASSET_NAME
        defaultDataAssetShouldBeFound("edhAssetName.in=" + DEFAULT_EDH_ASSET_NAME + "," + UPDATED_EDH_ASSET_NAME);

        // Get all the dataAssetList where edhAssetName equals to UPDATED_EDH_ASSET_NAME
        defaultDataAssetShouldNotBeFound("edhAssetName.in=" + UPDATED_EDH_ASSET_NAME);
    }

    @Test
    @Transactional
    public void getAllDataAssetsByEdhAssetNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where edhAssetName is not null
        defaultDataAssetShouldBeFound("edhAssetName.specified=true");

        // Get all the dataAssetList where edhAssetName is null
        defaultDataAssetShouldNotBeFound("edhAssetName.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataAssetsByEmailDistributionIsEqualToSomething() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where emailDistribution equals to DEFAULT_EMAIL_DISTRIBUTION
        defaultDataAssetShouldBeFound("emailDistribution.equals=" + DEFAULT_EMAIL_DISTRIBUTION);

        // Get all the dataAssetList where emailDistribution equals to UPDATED_EMAIL_DISTRIBUTION
        defaultDataAssetShouldNotBeFound("emailDistribution.equals=" + UPDATED_EMAIL_DISTRIBUTION);
    }

    @Test
    @Transactional
    public void getAllDataAssetsByEmailDistributionIsInShouldWork() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where emailDistribution in DEFAULT_EMAIL_DISTRIBUTION or UPDATED_EMAIL_DISTRIBUTION
        defaultDataAssetShouldBeFound("emailDistribution.in=" + DEFAULT_EMAIL_DISTRIBUTION + "," + UPDATED_EMAIL_DISTRIBUTION);

        // Get all the dataAssetList where emailDistribution equals to UPDATED_EMAIL_DISTRIBUTION
        defaultDataAssetShouldNotBeFound("emailDistribution.in=" + UPDATED_EMAIL_DISTRIBUTION);
    }

    @Test
    @Transactional
    public void getAllDataAssetsByEmailDistributionIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataAssetRepository.saveAndFlush(dataAsset);

        // Get all the dataAssetList where emailDistribution is not null
        defaultDataAssetShouldBeFound("emailDistribution.specified=true");

        // Get all the dataAssetList where emailDistribution is null
        defaultDataAssetShouldNotBeFound("emailDistribution.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataAssetsByApplicationIsEqualToSomething() throws Exception {
        // Initialize the database
        Application application = ApplicationResourceIntTest.createEntity(em);
        em.persist(application);
        em.flush();
        dataAsset.setApplication(application);
        dataAssetRepository.saveAndFlush(dataAsset);
        Long applicationId = application.getId();

        // Get all the dataAssetList where application equals to applicationId
        defaultDataAssetShouldBeFound("applicationId.equals=" + applicationId);

        // Get all the dataAssetList where application equals to applicationId + 1
        defaultDataAssetShouldNotBeFound("applicationId.equals=" + (applicationId + 1));
    }


    @Test
    @Transactional
    public void getAllDataAssetsBySourceFeedIsEqualToSomething() throws Exception {
        // Initialize the database
        SourceFeed sourceFeed = SourceFeedResourceIntTest.createEntity(em);
        em.persist(sourceFeed);
        em.flush();
        dataAsset.addSourceFeed(sourceFeed);
        dataAssetRepository.saveAndFlush(dataAsset);
        Long sourceFeedId = sourceFeed.getId();

        // Get all the dataAssetList where sourceFeed equals to sourceFeedId
        defaultDataAssetShouldBeFound("sourceFeedId.equals=" + sourceFeedId);

        // Get all the dataAssetList where sourceFeed equals to sourceFeedId + 1
        defaultDataAssetShouldNotBeFound("sourceFeedId.equals=" + (sourceFeedId + 1));
    }


    @Test
    @Transactional
    public void getAllDataAssetsBySourceDatabaseIsEqualToSomething() throws Exception {
        // Initialize the database
        SourceDatabase sourceDatabase = SourceDatabaseResourceIntTest.createEntity(em);
        em.persist(sourceDatabase);
        em.flush();
        dataAsset.addSourceDatabase(sourceDatabase);
        dataAssetRepository.saveAndFlush(dataAsset);
        Long sourceDatabaseId = sourceDatabase.getId();

        // Get all the dataAssetList where sourceDatabase equals to sourceDatabaseId
        defaultDataAssetShouldBeFound("sourceDatabaseId.equals=" + sourceDatabaseId);

        // Get all the dataAssetList where sourceDatabase equals to sourceDatabaseId + 1
        defaultDataAssetShouldNotBeFound("sourceDatabaseId.equals=" + (sourceDatabaseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDataAssetShouldBeFound(String filter) throws Exception {
        restDataAssetMockMvc.perform(get("/api/data-assets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataAsset.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].assetFileName").value(hasItem(DEFAULT_ASSET_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].storedProcedureName").value(hasItem(DEFAULT_STORED_PROCEDURE_NAME.toString())))
            .andExpect(jsonPath("$.[*].queryLogic").value(hasItem(DEFAULT_QUERY_LOGIC.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].edhAssetName").value(hasItem(DEFAULT_EDH_ASSET_NAME.toString())))
            .andExpect(jsonPath("$.[*].emailDistribution").value(hasItem(DEFAULT_EMAIL_DISTRIBUTION.toString())));

        // Check, that the count call also returns 1
        restDataAssetMockMvc.perform(get("/api/data-assets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDataAssetShouldNotBeFound(String filter) throws Exception {
        restDataAssetMockMvc.perform(get("/api/data-assets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDataAssetMockMvc.perform(get("/api/data-assets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDataAsset() throws Exception {
        // Get the dataAsset
        restDataAssetMockMvc.perform(get("/api/data-assets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataAsset() throws Exception {
        // Initialize the database
        dataAssetService.save(dataAsset);

        int databaseSizeBeforeUpdate = dataAssetRepository.findAll().size();

        // Update the dataAsset
        DataAsset updatedDataAsset = dataAssetRepository.findById(dataAsset.getId()).get();
        // Disconnect from session so that the updates on updatedDataAsset are not directly saved in db
        em.detach(updatedDataAsset);
        updatedDataAsset
            .name(UPDATED_NAME)
            .assetFileName(UPDATED_ASSET_FILE_NAME)
            .type(UPDATED_TYPE)
            .frequency(UPDATED_FREQUENCY)
            .storedProcedureName(UPDATED_STORED_PROCEDURE_NAME)
            .queryLogic(UPDATED_QUERY_LOGIC)
            .remarks(UPDATED_REMARKS)
            .edhAssetName(UPDATED_EDH_ASSET_NAME)
            .emailDistribution(UPDATED_EMAIL_DISTRIBUTION);

        restDataAssetMockMvc.perform(put("/api/data-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDataAsset)))
            .andExpect(status().isOk());

        // Validate the DataAsset in the database
        List<DataAsset> dataAssetList = dataAssetRepository.findAll();
        assertThat(dataAssetList).hasSize(databaseSizeBeforeUpdate);
        DataAsset testDataAsset = dataAssetList.get(dataAssetList.size() - 1);
        assertThat(testDataAsset.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataAsset.getAssetFileName()).isEqualTo(UPDATED_ASSET_FILE_NAME);
        assertThat(testDataAsset.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDataAsset.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testDataAsset.getStoredProcedureName()).isEqualTo(UPDATED_STORED_PROCEDURE_NAME);
        assertThat(testDataAsset.getQueryLogic()).isEqualTo(UPDATED_QUERY_LOGIC);
        assertThat(testDataAsset.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testDataAsset.getEdhAssetName()).isEqualTo(UPDATED_EDH_ASSET_NAME);
        assertThat(testDataAsset.getEmailDistribution()).isEqualTo(UPDATED_EMAIL_DISTRIBUTION);
    }

    @Test
    @Transactional
    public void updateNonExistingDataAsset() throws Exception {
        int databaseSizeBeforeUpdate = dataAssetRepository.findAll().size();

        // Create the DataAsset

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataAssetMockMvc.perform(put("/api/data-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataAsset)))
            .andExpect(status().isBadRequest());

        // Validate the DataAsset in the database
        List<DataAsset> dataAssetList = dataAssetRepository.findAll();
        assertThat(dataAssetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataAsset() throws Exception {
        // Initialize the database
        dataAssetService.save(dataAsset);

        int databaseSizeBeforeDelete = dataAssetRepository.findAll().size();

        // Get the dataAsset
        restDataAssetMockMvc.perform(delete("/api/data-assets/{id}", dataAsset.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DataAsset> dataAssetList = dataAssetRepository.findAll();
        assertThat(dataAssetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataAsset.class);
        DataAsset dataAsset1 = new DataAsset();
        dataAsset1.setId(1L);
        DataAsset dataAsset2 = new DataAsset();
        dataAsset2.setId(dataAsset1.getId());
        assertThat(dataAsset1).isEqualTo(dataAsset2);
        dataAsset2.setId(2L);
        assertThat(dataAsset1).isNotEqualTo(dataAsset2);
        dataAsset1.setId(null);
        assertThat(dataAsset1).isNotEqualTo(dataAsset2);
    }
}
