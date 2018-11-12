package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyApp;

import com.mycompany.myapp.domain.SourceFeedMapping;
import com.mycompany.myapp.domain.SourceFeed;
import com.mycompany.myapp.domain.DataModelMapping;
import com.mycompany.myapp.domain.SourceDatabaseMapping;
import com.mycompany.myapp.domain.DataAsset;
import com.mycompany.myapp.repository.SourceFeedMappingRepository;
import com.mycompany.myapp.service.SourceFeedMappingService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.SourceFeedMappingCriteria;
import com.mycompany.myapp.service.SourceFeedMappingQueryService;

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

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.FieldDataType;
import com.mycompany.myapp.domain.enumeration.DataCategory;
/**
 * Test class for the SourceFeedMappingResource REST controller.
 *
 * @see SourceFeedMappingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApp.class)
public class SourceFeedMappingResourceIntTest {

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_FIELD_ORDER_NUMBER = 1;
    private static final Integer UPDATED_FIELD_ORDER_NUMBER = 2;

    private static final FieldDataType DEFAULT_FIELD_DATA_TYPE = FieldDataType.STRING;
    private static final FieldDataType UPDATED_FIELD_DATA_TYPE = FieldDataType.NUMBER;

    private static final Integer DEFAULT_FIELD_SCALE = 1;
    private static final Integer UPDATED_FIELD_SCALE = 2;

    private static final Integer DEFAULT_FIELD_PRECISION = 1;
    private static final Integer UPDATED_FIELD_PRECISION = 2;

    private static final Boolean DEFAULT_CDE = false;
    private static final Boolean UPDATED_CDE = true;

    private static final Boolean DEFAULT_PII = false;
    private static final Boolean UPDATED_PII = true;

    private static final DataCategory DEFAULT_DATA_CATEGORY = DataCategory.UDG;
    private static final DataCategory UPDATED_DATA_CATEGORY = DataCategory.EDG;

    private static final String DEFAULT_DATA_QUALITY_RULE = "AAAAAAAAAA";
    private static final String UPDATED_DATA_QUALITY_RULE = "BBBBBBBBBB";

    @Autowired
    private SourceFeedMappingRepository sourceFeedMappingRepository;

    @Mock
    private SourceFeedMappingRepository sourceFeedMappingRepositoryMock;
    

    @Mock
    private SourceFeedMappingService sourceFeedMappingServiceMock;

    @Autowired
    private SourceFeedMappingService sourceFeedMappingService;

    @Autowired
    private SourceFeedMappingQueryService sourceFeedMappingQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSourceFeedMappingMockMvc;

    private SourceFeedMapping sourceFeedMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SourceFeedMappingResource sourceFeedMappingResource = new SourceFeedMappingResource(sourceFeedMappingService, sourceFeedMappingQueryService);
        this.restSourceFeedMappingMockMvc = MockMvcBuilders.standaloneSetup(sourceFeedMappingResource)
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
    public static SourceFeedMapping createEntity(EntityManager em) {
        SourceFeedMapping sourceFeedMapping = new SourceFeedMapping()
            .fieldName(DEFAULT_FIELD_NAME)
            .fieldOrderNumber(DEFAULT_FIELD_ORDER_NUMBER)
            .fieldDataType(DEFAULT_FIELD_DATA_TYPE)
            .fieldScale(DEFAULT_FIELD_SCALE)
            .fieldPrecision(DEFAULT_FIELD_PRECISION)
            .cde(DEFAULT_CDE)
            .pii(DEFAULT_PII)
            .dataCategory(DEFAULT_DATA_CATEGORY)
            .dataQualityRule(DEFAULT_DATA_QUALITY_RULE);
        return sourceFeedMapping;
    }

    @Before
    public void initTest() {
        sourceFeedMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createSourceFeedMapping() throws Exception {
        int databaseSizeBeforeCreate = sourceFeedMappingRepository.findAll().size();

        // Create the SourceFeedMapping
        restSourceFeedMappingMockMvc.perform(post("/api/source-feed-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceFeedMapping)))
            .andExpect(status().isCreated());

        // Validate the SourceFeedMapping in the database
        List<SourceFeedMapping> sourceFeedMappingList = sourceFeedMappingRepository.findAll();
        assertThat(sourceFeedMappingList).hasSize(databaseSizeBeforeCreate + 1);
        SourceFeedMapping testSourceFeedMapping = sourceFeedMappingList.get(sourceFeedMappingList.size() - 1);
        assertThat(testSourceFeedMapping.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testSourceFeedMapping.getFieldOrderNumber()).isEqualTo(DEFAULT_FIELD_ORDER_NUMBER);
        assertThat(testSourceFeedMapping.getFieldDataType()).isEqualTo(DEFAULT_FIELD_DATA_TYPE);
        assertThat(testSourceFeedMapping.getFieldScale()).isEqualTo(DEFAULT_FIELD_SCALE);
        assertThat(testSourceFeedMapping.getFieldPrecision()).isEqualTo(DEFAULT_FIELD_PRECISION);
        assertThat(testSourceFeedMapping.isCde()).isEqualTo(DEFAULT_CDE);
        assertThat(testSourceFeedMapping.isPii()).isEqualTo(DEFAULT_PII);
        assertThat(testSourceFeedMapping.getDataCategory()).isEqualTo(DEFAULT_DATA_CATEGORY);
        assertThat(testSourceFeedMapping.getDataQualityRule()).isEqualTo(DEFAULT_DATA_QUALITY_RULE);
    }

    @Test
    @Transactional
    public void createSourceFeedMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sourceFeedMappingRepository.findAll().size();

        // Create the SourceFeedMapping with an existing ID
        sourceFeedMapping.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSourceFeedMappingMockMvc.perform(post("/api/source-feed-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceFeedMapping)))
            .andExpect(status().isBadRequest());

        // Validate the SourceFeedMapping in the database
        List<SourceFeedMapping> sourceFeedMappingList = sourceFeedMappingRepository.findAll();
        assertThat(sourceFeedMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFieldNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceFeedMappingRepository.findAll().size();
        // set the field null
        sourceFeedMapping.setFieldName(null);

        // Create the SourceFeedMapping, which fails.

        restSourceFeedMappingMockMvc.perform(post("/api/source-feed-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceFeedMapping)))
            .andExpect(status().isBadRequest());

        List<SourceFeedMapping> sourceFeedMappingList = sourceFeedMappingRepository.findAll();
        assertThat(sourceFeedMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFieldOrderNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceFeedMappingRepository.findAll().size();
        // set the field null
        sourceFeedMapping.setFieldOrderNumber(null);

        // Create the SourceFeedMapping, which fails.

        restSourceFeedMappingMockMvc.perform(post("/api/source-feed-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceFeedMapping)))
            .andExpect(status().isBadRequest());

        List<SourceFeedMapping> sourceFeedMappingList = sourceFeedMappingRepository.findAll();
        assertThat(sourceFeedMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFieldDataTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceFeedMappingRepository.findAll().size();
        // set the field null
        sourceFeedMapping.setFieldDataType(null);

        // Create the SourceFeedMapping, which fails.

        restSourceFeedMappingMockMvc.perform(post("/api/source-feed-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceFeedMapping)))
            .andExpect(status().isBadRequest());

        List<SourceFeedMapping> sourceFeedMappingList = sourceFeedMappingRepository.findAll();
        assertThat(sourceFeedMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCdeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceFeedMappingRepository.findAll().size();
        // set the field null
        sourceFeedMapping.setCde(null);

        // Create the SourceFeedMapping, which fails.

        restSourceFeedMappingMockMvc.perform(post("/api/source-feed-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceFeedMapping)))
            .andExpect(status().isBadRequest());

        List<SourceFeedMapping> sourceFeedMappingList = sourceFeedMappingRepository.findAll();
        assertThat(sourceFeedMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPiiIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceFeedMappingRepository.findAll().size();
        // set the field null
        sourceFeedMapping.setPii(null);

        // Create the SourceFeedMapping, which fails.

        restSourceFeedMappingMockMvc.perform(post("/api/source-feed-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceFeedMapping)))
            .andExpect(status().isBadRequest());

        List<SourceFeedMapping> sourceFeedMappingList = sourceFeedMappingRepository.findAll();
        assertThat(sourceFeedMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceFeedMappingRepository.findAll().size();
        // set the field null
        sourceFeedMapping.setDataCategory(null);

        // Create the SourceFeedMapping, which fails.

        restSourceFeedMappingMockMvc.perform(post("/api/source-feed-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceFeedMapping)))
            .andExpect(status().isBadRequest());

        List<SourceFeedMapping> sourceFeedMappingList = sourceFeedMappingRepository.findAll();
        assertThat(sourceFeedMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappings() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList
        restSourceFeedMappingMockMvc.perform(get("/api/source-feed-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sourceFeedMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME.toString())))
            .andExpect(jsonPath("$.[*].fieldOrderNumber").value(hasItem(DEFAULT_FIELD_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].fieldDataType").value(hasItem(DEFAULT_FIELD_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fieldScale").value(hasItem(DEFAULT_FIELD_SCALE)))
            .andExpect(jsonPath("$.[*].fieldPrecision").value(hasItem(DEFAULT_FIELD_PRECISION)))
            .andExpect(jsonPath("$.[*].cde").value(hasItem(DEFAULT_CDE.booleanValue())))
            .andExpect(jsonPath("$.[*].pii").value(hasItem(DEFAULT_PII.booleanValue())))
            .andExpect(jsonPath("$.[*].dataCategory").value(hasItem(DEFAULT_DATA_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].dataQualityRule").value(hasItem(DEFAULT_DATA_QUALITY_RULE.toString())));
    }
    
    public void getAllSourceFeedMappingsWithEagerRelationshipsIsEnabled() throws Exception {
        SourceFeedMappingResource sourceFeedMappingResource = new SourceFeedMappingResource(sourceFeedMappingServiceMock, sourceFeedMappingQueryService);
        when(sourceFeedMappingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restSourceFeedMappingMockMvc = MockMvcBuilders.standaloneSetup(sourceFeedMappingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSourceFeedMappingMockMvc.perform(get("/api/source-feed-mappings?eagerload=true"))
        .andExpect(status().isOk());

        verify(sourceFeedMappingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllSourceFeedMappingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        SourceFeedMappingResource sourceFeedMappingResource = new SourceFeedMappingResource(sourceFeedMappingServiceMock, sourceFeedMappingQueryService);
            when(sourceFeedMappingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restSourceFeedMappingMockMvc = MockMvcBuilders.standaloneSetup(sourceFeedMappingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSourceFeedMappingMockMvc.perform(get("/api/source-feed-mappings?eagerload=true"))
        .andExpect(status().isOk());

            verify(sourceFeedMappingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSourceFeedMapping() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get the sourceFeedMapping
        restSourceFeedMappingMockMvc.perform(get("/api/source-feed-mappings/{id}", sourceFeedMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sourceFeedMapping.getId().intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME.toString()))
            .andExpect(jsonPath("$.fieldOrderNumber").value(DEFAULT_FIELD_ORDER_NUMBER))
            .andExpect(jsonPath("$.fieldDataType").value(DEFAULT_FIELD_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.fieldScale").value(DEFAULT_FIELD_SCALE))
            .andExpect(jsonPath("$.fieldPrecision").value(DEFAULT_FIELD_PRECISION))
            .andExpect(jsonPath("$.cde").value(DEFAULT_CDE.booleanValue()))
            .andExpect(jsonPath("$.pii").value(DEFAULT_PII.booleanValue()))
            .andExpect(jsonPath("$.dataCategory").value(DEFAULT_DATA_CATEGORY.toString()))
            .andExpect(jsonPath("$.dataQualityRule").value(DEFAULT_DATA_QUALITY_RULE.toString()));
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldNameIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldName equals to DEFAULT_FIELD_NAME
        defaultSourceFeedMappingShouldBeFound("fieldName.equals=" + DEFAULT_FIELD_NAME);

        // Get all the sourceFeedMappingList where fieldName equals to UPDATED_FIELD_NAME
        defaultSourceFeedMappingShouldNotBeFound("fieldName.equals=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldNameIsInShouldWork() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldName in DEFAULT_FIELD_NAME or UPDATED_FIELD_NAME
        defaultSourceFeedMappingShouldBeFound("fieldName.in=" + DEFAULT_FIELD_NAME + "," + UPDATED_FIELD_NAME);

        // Get all the sourceFeedMappingList where fieldName equals to UPDATED_FIELD_NAME
        defaultSourceFeedMappingShouldNotBeFound("fieldName.in=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldName is not null
        defaultSourceFeedMappingShouldBeFound("fieldName.specified=true");

        // Get all the sourceFeedMappingList where fieldName is null
        defaultSourceFeedMappingShouldNotBeFound("fieldName.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldOrderNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldOrderNumber equals to DEFAULT_FIELD_ORDER_NUMBER
        defaultSourceFeedMappingShouldBeFound("fieldOrderNumber.equals=" + DEFAULT_FIELD_ORDER_NUMBER);

        // Get all the sourceFeedMappingList where fieldOrderNumber equals to UPDATED_FIELD_ORDER_NUMBER
        defaultSourceFeedMappingShouldNotBeFound("fieldOrderNumber.equals=" + UPDATED_FIELD_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldOrderNumberIsInShouldWork() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldOrderNumber in DEFAULT_FIELD_ORDER_NUMBER or UPDATED_FIELD_ORDER_NUMBER
        defaultSourceFeedMappingShouldBeFound("fieldOrderNumber.in=" + DEFAULT_FIELD_ORDER_NUMBER + "," + UPDATED_FIELD_ORDER_NUMBER);

        // Get all the sourceFeedMappingList where fieldOrderNumber equals to UPDATED_FIELD_ORDER_NUMBER
        defaultSourceFeedMappingShouldNotBeFound("fieldOrderNumber.in=" + UPDATED_FIELD_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldOrderNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldOrderNumber is not null
        defaultSourceFeedMappingShouldBeFound("fieldOrderNumber.specified=true");

        // Get all the sourceFeedMappingList where fieldOrderNumber is null
        defaultSourceFeedMappingShouldNotBeFound("fieldOrderNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldOrderNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldOrderNumber greater than or equals to DEFAULT_FIELD_ORDER_NUMBER
        defaultSourceFeedMappingShouldBeFound("fieldOrderNumber.greaterOrEqualThan=" + DEFAULT_FIELD_ORDER_NUMBER);

        // Get all the sourceFeedMappingList where fieldOrderNumber greater than or equals to UPDATED_FIELD_ORDER_NUMBER
        defaultSourceFeedMappingShouldNotBeFound("fieldOrderNumber.greaterOrEqualThan=" + UPDATED_FIELD_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldOrderNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldOrderNumber less than or equals to DEFAULT_FIELD_ORDER_NUMBER
        defaultSourceFeedMappingShouldNotBeFound("fieldOrderNumber.lessThan=" + DEFAULT_FIELD_ORDER_NUMBER);

        // Get all the sourceFeedMappingList where fieldOrderNumber less than or equals to UPDATED_FIELD_ORDER_NUMBER
        defaultSourceFeedMappingShouldBeFound("fieldOrderNumber.lessThan=" + UPDATED_FIELD_ORDER_NUMBER);
    }


    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldDataTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldDataType equals to DEFAULT_FIELD_DATA_TYPE
        defaultSourceFeedMappingShouldBeFound("fieldDataType.equals=" + DEFAULT_FIELD_DATA_TYPE);

        // Get all the sourceFeedMappingList where fieldDataType equals to UPDATED_FIELD_DATA_TYPE
        defaultSourceFeedMappingShouldNotBeFound("fieldDataType.equals=" + UPDATED_FIELD_DATA_TYPE);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldDataTypeIsInShouldWork() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldDataType in DEFAULT_FIELD_DATA_TYPE or UPDATED_FIELD_DATA_TYPE
        defaultSourceFeedMappingShouldBeFound("fieldDataType.in=" + DEFAULT_FIELD_DATA_TYPE + "," + UPDATED_FIELD_DATA_TYPE);

        // Get all the sourceFeedMappingList where fieldDataType equals to UPDATED_FIELD_DATA_TYPE
        defaultSourceFeedMappingShouldNotBeFound("fieldDataType.in=" + UPDATED_FIELD_DATA_TYPE);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldDataTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldDataType is not null
        defaultSourceFeedMappingShouldBeFound("fieldDataType.specified=true");

        // Get all the sourceFeedMappingList where fieldDataType is null
        defaultSourceFeedMappingShouldNotBeFound("fieldDataType.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldScaleIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldScale equals to DEFAULT_FIELD_SCALE
        defaultSourceFeedMappingShouldBeFound("fieldScale.equals=" + DEFAULT_FIELD_SCALE);

        // Get all the sourceFeedMappingList where fieldScale equals to UPDATED_FIELD_SCALE
        defaultSourceFeedMappingShouldNotBeFound("fieldScale.equals=" + UPDATED_FIELD_SCALE);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldScaleIsInShouldWork() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldScale in DEFAULT_FIELD_SCALE or UPDATED_FIELD_SCALE
        defaultSourceFeedMappingShouldBeFound("fieldScale.in=" + DEFAULT_FIELD_SCALE + "," + UPDATED_FIELD_SCALE);

        // Get all the sourceFeedMappingList where fieldScale equals to UPDATED_FIELD_SCALE
        defaultSourceFeedMappingShouldNotBeFound("fieldScale.in=" + UPDATED_FIELD_SCALE);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldScaleIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldScale is not null
        defaultSourceFeedMappingShouldBeFound("fieldScale.specified=true");

        // Get all the sourceFeedMappingList where fieldScale is null
        defaultSourceFeedMappingShouldNotBeFound("fieldScale.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldScaleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldScale greater than or equals to DEFAULT_FIELD_SCALE
        defaultSourceFeedMappingShouldBeFound("fieldScale.greaterOrEqualThan=" + DEFAULT_FIELD_SCALE);

        // Get all the sourceFeedMappingList where fieldScale greater than or equals to UPDATED_FIELD_SCALE
        defaultSourceFeedMappingShouldNotBeFound("fieldScale.greaterOrEqualThan=" + UPDATED_FIELD_SCALE);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldScaleIsLessThanSomething() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldScale less than or equals to DEFAULT_FIELD_SCALE
        defaultSourceFeedMappingShouldNotBeFound("fieldScale.lessThan=" + DEFAULT_FIELD_SCALE);

        // Get all the sourceFeedMappingList where fieldScale less than or equals to UPDATED_FIELD_SCALE
        defaultSourceFeedMappingShouldBeFound("fieldScale.lessThan=" + UPDATED_FIELD_SCALE);
    }


    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldPrecisionIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldPrecision equals to DEFAULT_FIELD_PRECISION
        defaultSourceFeedMappingShouldBeFound("fieldPrecision.equals=" + DEFAULT_FIELD_PRECISION);

        // Get all the sourceFeedMappingList where fieldPrecision equals to UPDATED_FIELD_PRECISION
        defaultSourceFeedMappingShouldNotBeFound("fieldPrecision.equals=" + UPDATED_FIELD_PRECISION);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldPrecisionIsInShouldWork() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldPrecision in DEFAULT_FIELD_PRECISION or UPDATED_FIELD_PRECISION
        defaultSourceFeedMappingShouldBeFound("fieldPrecision.in=" + DEFAULT_FIELD_PRECISION + "," + UPDATED_FIELD_PRECISION);

        // Get all the sourceFeedMappingList where fieldPrecision equals to UPDATED_FIELD_PRECISION
        defaultSourceFeedMappingShouldNotBeFound("fieldPrecision.in=" + UPDATED_FIELD_PRECISION);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldPrecisionIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldPrecision is not null
        defaultSourceFeedMappingShouldBeFound("fieldPrecision.specified=true");

        // Get all the sourceFeedMappingList where fieldPrecision is null
        defaultSourceFeedMappingShouldNotBeFound("fieldPrecision.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldPrecisionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldPrecision greater than or equals to DEFAULT_FIELD_PRECISION
        defaultSourceFeedMappingShouldBeFound("fieldPrecision.greaterOrEqualThan=" + DEFAULT_FIELD_PRECISION);

        // Get all the sourceFeedMappingList where fieldPrecision greater than or equals to UPDATED_FIELD_PRECISION
        defaultSourceFeedMappingShouldNotBeFound("fieldPrecision.greaterOrEqualThan=" + UPDATED_FIELD_PRECISION);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByFieldPrecisionIsLessThanSomething() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where fieldPrecision less than or equals to DEFAULT_FIELD_PRECISION
        defaultSourceFeedMappingShouldNotBeFound("fieldPrecision.lessThan=" + DEFAULT_FIELD_PRECISION);

        // Get all the sourceFeedMappingList where fieldPrecision less than or equals to UPDATED_FIELD_PRECISION
        defaultSourceFeedMappingShouldBeFound("fieldPrecision.lessThan=" + UPDATED_FIELD_PRECISION);
    }


    @Test
    @Transactional
    public void getAllSourceFeedMappingsByCdeIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where cde equals to DEFAULT_CDE
        defaultSourceFeedMappingShouldBeFound("cde.equals=" + DEFAULT_CDE);

        // Get all the sourceFeedMappingList where cde equals to UPDATED_CDE
        defaultSourceFeedMappingShouldNotBeFound("cde.equals=" + UPDATED_CDE);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByCdeIsInShouldWork() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where cde in DEFAULT_CDE or UPDATED_CDE
        defaultSourceFeedMappingShouldBeFound("cde.in=" + DEFAULT_CDE + "," + UPDATED_CDE);

        // Get all the sourceFeedMappingList where cde equals to UPDATED_CDE
        defaultSourceFeedMappingShouldNotBeFound("cde.in=" + UPDATED_CDE);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByCdeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where cde is not null
        defaultSourceFeedMappingShouldBeFound("cde.specified=true");

        // Get all the sourceFeedMappingList where cde is null
        defaultSourceFeedMappingShouldNotBeFound("cde.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByPiiIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where pii equals to DEFAULT_PII
        defaultSourceFeedMappingShouldBeFound("pii.equals=" + DEFAULT_PII);

        // Get all the sourceFeedMappingList where pii equals to UPDATED_PII
        defaultSourceFeedMappingShouldNotBeFound("pii.equals=" + UPDATED_PII);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByPiiIsInShouldWork() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where pii in DEFAULT_PII or UPDATED_PII
        defaultSourceFeedMappingShouldBeFound("pii.in=" + DEFAULT_PII + "," + UPDATED_PII);

        // Get all the sourceFeedMappingList where pii equals to UPDATED_PII
        defaultSourceFeedMappingShouldNotBeFound("pii.in=" + UPDATED_PII);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByPiiIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where pii is not null
        defaultSourceFeedMappingShouldBeFound("pii.specified=true");

        // Get all the sourceFeedMappingList where pii is null
        defaultSourceFeedMappingShouldNotBeFound("pii.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByDataCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where dataCategory equals to DEFAULT_DATA_CATEGORY
        defaultSourceFeedMappingShouldBeFound("dataCategory.equals=" + DEFAULT_DATA_CATEGORY);

        // Get all the sourceFeedMappingList where dataCategory equals to UPDATED_DATA_CATEGORY
        defaultSourceFeedMappingShouldNotBeFound("dataCategory.equals=" + UPDATED_DATA_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByDataCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where dataCategory in DEFAULT_DATA_CATEGORY or UPDATED_DATA_CATEGORY
        defaultSourceFeedMappingShouldBeFound("dataCategory.in=" + DEFAULT_DATA_CATEGORY + "," + UPDATED_DATA_CATEGORY);

        // Get all the sourceFeedMappingList where dataCategory equals to UPDATED_DATA_CATEGORY
        defaultSourceFeedMappingShouldNotBeFound("dataCategory.in=" + UPDATED_DATA_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByDataCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where dataCategory is not null
        defaultSourceFeedMappingShouldBeFound("dataCategory.specified=true");

        // Get all the sourceFeedMappingList where dataCategory is null
        defaultSourceFeedMappingShouldNotBeFound("dataCategory.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByDataQualityRuleIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where dataQualityRule equals to DEFAULT_DATA_QUALITY_RULE
        defaultSourceFeedMappingShouldBeFound("dataQualityRule.equals=" + DEFAULT_DATA_QUALITY_RULE);

        // Get all the sourceFeedMappingList where dataQualityRule equals to UPDATED_DATA_QUALITY_RULE
        defaultSourceFeedMappingShouldNotBeFound("dataQualityRule.equals=" + UPDATED_DATA_QUALITY_RULE);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByDataQualityRuleIsInShouldWork() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where dataQualityRule in DEFAULT_DATA_QUALITY_RULE or UPDATED_DATA_QUALITY_RULE
        defaultSourceFeedMappingShouldBeFound("dataQualityRule.in=" + DEFAULT_DATA_QUALITY_RULE + "," + UPDATED_DATA_QUALITY_RULE);

        // Get all the sourceFeedMappingList where dataQualityRule equals to UPDATED_DATA_QUALITY_RULE
        defaultSourceFeedMappingShouldNotBeFound("dataQualityRule.in=" + UPDATED_DATA_QUALITY_RULE);
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsByDataQualityRuleIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);

        // Get all the sourceFeedMappingList where dataQualityRule is not null
        defaultSourceFeedMappingShouldBeFound("dataQualityRule.specified=true");

        // Get all the sourceFeedMappingList where dataQualityRule is null
        defaultSourceFeedMappingShouldNotBeFound("dataQualityRule.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceFeedMappingsBySourceFeedIsEqualToSomething() throws Exception {
        // Initialize the database
        SourceFeed sourceFeed = SourceFeedResourceIntTest.createEntity(em);
        em.persist(sourceFeed);
        em.flush();
        sourceFeedMapping.setSourceFeed(sourceFeed);
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);
        Long sourceFeedId = sourceFeed.getId();

        // Get all the sourceFeedMappingList where sourceFeed equals to sourceFeedId
        defaultSourceFeedMappingShouldBeFound("sourceFeedId.equals=" + sourceFeedId);

        // Get all the sourceFeedMappingList where sourceFeed equals to sourceFeedId + 1
        defaultSourceFeedMappingShouldNotBeFound("sourceFeedId.equals=" + (sourceFeedId + 1));
    }


    @Test
    @Transactional
    public void getAllSourceFeedMappingsByDataModelMappingIsEqualToSomething() throws Exception {
        // Initialize the database
        DataModelMapping dataModelMapping = DataModelMappingResourceIntTest.createEntity(em);
        em.persist(dataModelMapping);
        em.flush();
        sourceFeedMapping.setDataModelMapping(dataModelMapping);
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);
        Long dataModelMappingId = dataModelMapping.getId();

        // Get all the sourceFeedMappingList where dataModelMapping equals to dataModelMappingId
        defaultSourceFeedMappingShouldBeFound("dataModelMappingId.equals=" + dataModelMappingId);

        // Get all the sourceFeedMappingList where dataModelMapping equals to dataModelMappingId + 1
        defaultSourceFeedMappingShouldNotBeFound("dataModelMappingId.equals=" + (dataModelMappingId + 1));
    }


    @Test
    @Transactional
    public void getAllSourceFeedMappingsBySourceDatabaseMappingIsEqualToSomething() throws Exception {
        // Initialize the database
        SourceDatabaseMapping sourceDatabaseMapping = SourceDatabaseMappingResourceIntTest.createEntity(em);
        em.persist(sourceDatabaseMapping);
        em.flush();
        sourceFeedMapping.setSourceDatabaseMapping(sourceDatabaseMapping);
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);
        Long sourceDatabaseMappingId = sourceDatabaseMapping.getId();

        // Get all the sourceFeedMappingList where sourceDatabaseMapping equals to sourceDatabaseMappingId
        defaultSourceFeedMappingShouldBeFound("sourceDatabaseMappingId.equals=" + sourceDatabaseMappingId);

        // Get all the sourceFeedMappingList where sourceDatabaseMapping equals to sourceDatabaseMappingId + 1
        defaultSourceFeedMappingShouldNotBeFound("sourceDatabaseMappingId.equals=" + (sourceDatabaseMappingId + 1));
    }


    @Test
    @Transactional
    public void getAllSourceFeedMappingsByDataAssetIsEqualToSomething() throws Exception {
        // Initialize the database
        DataAsset dataAsset = DataAssetResourceIntTest.createEntity(em);
        em.persist(dataAsset);
        em.flush();
        sourceFeedMapping.addDataAsset(dataAsset);
        sourceFeedMappingRepository.saveAndFlush(sourceFeedMapping);
        Long dataAssetId = dataAsset.getId();

        // Get all the sourceFeedMappingList where dataAsset equals to dataAssetId
        defaultSourceFeedMappingShouldBeFound("dataAssetId.equals=" + dataAssetId);

        // Get all the sourceFeedMappingList where dataAsset equals to dataAssetId + 1
        defaultSourceFeedMappingShouldNotBeFound("dataAssetId.equals=" + (dataAssetId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSourceFeedMappingShouldBeFound(String filter) throws Exception {
        restSourceFeedMappingMockMvc.perform(get("/api/source-feed-mappings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sourceFeedMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME.toString())))
            .andExpect(jsonPath("$.[*].fieldOrderNumber").value(hasItem(DEFAULT_FIELD_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].fieldDataType").value(hasItem(DEFAULT_FIELD_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fieldScale").value(hasItem(DEFAULT_FIELD_SCALE)))
            .andExpect(jsonPath("$.[*].fieldPrecision").value(hasItem(DEFAULT_FIELD_PRECISION)))
            .andExpect(jsonPath("$.[*].cde").value(hasItem(DEFAULT_CDE.booleanValue())))
            .andExpect(jsonPath("$.[*].pii").value(hasItem(DEFAULT_PII.booleanValue())))
            .andExpect(jsonPath("$.[*].dataCategory").value(hasItem(DEFAULT_DATA_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].dataQualityRule").value(hasItem(DEFAULT_DATA_QUALITY_RULE.toString())));

        // Check, that the count call also returns 1
        restSourceFeedMappingMockMvc.perform(get("/api/source-feed-mappings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSourceFeedMappingShouldNotBeFound(String filter) throws Exception {
        restSourceFeedMappingMockMvc.perform(get("/api/source-feed-mappings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSourceFeedMappingMockMvc.perform(get("/api/source-feed-mappings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSourceFeedMapping() throws Exception {
        // Get the sourceFeedMapping
        restSourceFeedMappingMockMvc.perform(get("/api/source-feed-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSourceFeedMapping() throws Exception {
        // Initialize the database
        sourceFeedMappingService.save(sourceFeedMapping);

        int databaseSizeBeforeUpdate = sourceFeedMappingRepository.findAll().size();

        // Update the sourceFeedMapping
        SourceFeedMapping updatedSourceFeedMapping = sourceFeedMappingRepository.findById(sourceFeedMapping.getId()).get();
        // Disconnect from session so that the updates on updatedSourceFeedMapping are not directly saved in db
        em.detach(updatedSourceFeedMapping);
        updatedSourceFeedMapping
            .fieldName(UPDATED_FIELD_NAME)
            .fieldOrderNumber(UPDATED_FIELD_ORDER_NUMBER)
            .fieldDataType(UPDATED_FIELD_DATA_TYPE)
            .fieldScale(UPDATED_FIELD_SCALE)
            .fieldPrecision(UPDATED_FIELD_PRECISION)
            .cde(UPDATED_CDE)
            .pii(UPDATED_PII)
            .dataCategory(UPDATED_DATA_CATEGORY)
            .dataQualityRule(UPDATED_DATA_QUALITY_RULE);

        restSourceFeedMappingMockMvc.perform(put("/api/source-feed-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSourceFeedMapping)))
            .andExpect(status().isOk());

        // Validate the SourceFeedMapping in the database
        List<SourceFeedMapping> sourceFeedMappingList = sourceFeedMappingRepository.findAll();
        assertThat(sourceFeedMappingList).hasSize(databaseSizeBeforeUpdate);
        SourceFeedMapping testSourceFeedMapping = sourceFeedMappingList.get(sourceFeedMappingList.size() - 1);
        assertThat(testSourceFeedMapping.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testSourceFeedMapping.getFieldOrderNumber()).isEqualTo(UPDATED_FIELD_ORDER_NUMBER);
        assertThat(testSourceFeedMapping.getFieldDataType()).isEqualTo(UPDATED_FIELD_DATA_TYPE);
        assertThat(testSourceFeedMapping.getFieldScale()).isEqualTo(UPDATED_FIELD_SCALE);
        assertThat(testSourceFeedMapping.getFieldPrecision()).isEqualTo(UPDATED_FIELD_PRECISION);
        assertThat(testSourceFeedMapping.isCde()).isEqualTo(UPDATED_CDE);
        assertThat(testSourceFeedMapping.isPii()).isEqualTo(UPDATED_PII);
        assertThat(testSourceFeedMapping.getDataCategory()).isEqualTo(UPDATED_DATA_CATEGORY);
        assertThat(testSourceFeedMapping.getDataQualityRule()).isEqualTo(UPDATED_DATA_QUALITY_RULE);
    }

    @Test
    @Transactional
    public void updateNonExistingSourceFeedMapping() throws Exception {
        int databaseSizeBeforeUpdate = sourceFeedMappingRepository.findAll().size();

        // Create the SourceFeedMapping

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSourceFeedMappingMockMvc.perform(put("/api/source-feed-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceFeedMapping)))
            .andExpect(status().isBadRequest());

        // Validate the SourceFeedMapping in the database
        List<SourceFeedMapping> sourceFeedMappingList = sourceFeedMappingRepository.findAll();
        assertThat(sourceFeedMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSourceFeedMapping() throws Exception {
        // Initialize the database
        sourceFeedMappingService.save(sourceFeedMapping);

        int databaseSizeBeforeDelete = sourceFeedMappingRepository.findAll().size();

        // Get the sourceFeedMapping
        restSourceFeedMappingMockMvc.perform(delete("/api/source-feed-mappings/{id}", sourceFeedMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SourceFeedMapping> sourceFeedMappingList = sourceFeedMappingRepository.findAll();
        assertThat(sourceFeedMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SourceFeedMapping.class);
        SourceFeedMapping sourceFeedMapping1 = new SourceFeedMapping();
        sourceFeedMapping1.setId(1L);
        SourceFeedMapping sourceFeedMapping2 = new SourceFeedMapping();
        sourceFeedMapping2.setId(sourceFeedMapping1.getId());
        assertThat(sourceFeedMapping1).isEqualTo(sourceFeedMapping2);
        sourceFeedMapping2.setId(2L);
        assertThat(sourceFeedMapping1).isNotEqualTo(sourceFeedMapping2);
        sourceFeedMapping1.setId(null);
        assertThat(sourceFeedMapping1).isNotEqualTo(sourceFeedMapping2);
    }
}
