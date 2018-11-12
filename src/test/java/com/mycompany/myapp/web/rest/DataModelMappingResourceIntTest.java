package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyApp;

import com.mycompany.myapp.domain.DataModelMapping;
import com.mycompany.myapp.domain.DataModel;
import com.mycompany.myapp.repository.DataModelMappingRepository;
import com.mycompany.myapp.service.DataModelMappingService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.DataModelMappingCriteria;
import com.mycompany.myapp.service.DataModelMappingQueryService;

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
import com.mycompany.myapp.domain.enumeration.DataCategory;
/**
 * Test class for the DataModelMappingResource REST controller.
 *
 * @see DataModelMappingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApp.class)
public class DataModelMappingResourceIntTest {

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final FieldDataType DEFAULT_FIELD_DATA_TYPE = FieldDataType.STRING;
    private static final FieldDataType UPDATED_FIELD_DATA_TYPE = FieldDataType.NUMBER;

    private static final Integer DEFAULT_FIELD_ORDER_NUMBER = 1;
    private static final Integer UPDATED_FIELD_ORDER_NUMBER = 2;

    private static final Integer DEFAULT_FIELD_SCALE = 1;
    private static final Integer UPDATED_FIELD_SCALE = 2;

    private static final Integer DEFAULT_FIELD_PRECISION = 1;
    private static final Integer UPDATED_FIELD_PRECISION = 2;

    private static final Boolean DEFAULT_PII = false;
    private static final Boolean UPDATED_PII = true;

    private static final DataCategory DEFAULT_DATA_CATEGORY = DataCategory.UDG;
    private static final DataCategory UPDATED_DATA_CATEGORY = DataCategory.EDG;

    @Autowired
    private DataModelMappingRepository dataModelMappingRepository;
    
    @Autowired
    private DataModelMappingService dataModelMappingService;

    @Autowired
    private DataModelMappingQueryService dataModelMappingQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDataModelMappingMockMvc;

    private DataModelMapping dataModelMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataModelMappingResource dataModelMappingResource = new DataModelMappingResource(dataModelMappingService, dataModelMappingQueryService);
        this.restDataModelMappingMockMvc = MockMvcBuilders.standaloneSetup(dataModelMappingResource)
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
    public static DataModelMapping createEntity(EntityManager em) {
        DataModelMapping dataModelMapping = new DataModelMapping()
            .fieldName(DEFAULT_FIELD_NAME)
            .fieldDataType(DEFAULT_FIELD_DATA_TYPE)
            .fieldOrderNumber(DEFAULT_FIELD_ORDER_NUMBER)
            .fieldScale(DEFAULT_FIELD_SCALE)
            .fieldPrecision(DEFAULT_FIELD_PRECISION)
            .pii(DEFAULT_PII)
            .dataCategory(DEFAULT_DATA_CATEGORY);
        return dataModelMapping;
    }

    @Before
    public void initTest() {
        dataModelMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataModelMapping() throws Exception {
        int databaseSizeBeforeCreate = dataModelMappingRepository.findAll().size();

        // Create the DataModelMapping
        restDataModelMappingMockMvc.perform(post("/api/data-model-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataModelMapping)))
            .andExpect(status().isCreated());

        // Validate the DataModelMapping in the database
        List<DataModelMapping> dataModelMappingList = dataModelMappingRepository.findAll();
        assertThat(dataModelMappingList).hasSize(databaseSizeBeforeCreate + 1);
        DataModelMapping testDataModelMapping = dataModelMappingList.get(dataModelMappingList.size() - 1);
        assertThat(testDataModelMapping.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testDataModelMapping.getFieldDataType()).isEqualTo(DEFAULT_FIELD_DATA_TYPE);
        assertThat(testDataModelMapping.getFieldOrderNumber()).isEqualTo(DEFAULT_FIELD_ORDER_NUMBER);
        assertThat(testDataModelMapping.getFieldScale()).isEqualTo(DEFAULT_FIELD_SCALE);
        assertThat(testDataModelMapping.getFieldPrecision()).isEqualTo(DEFAULT_FIELD_PRECISION);
        assertThat(testDataModelMapping.isPii()).isEqualTo(DEFAULT_PII);
        assertThat(testDataModelMapping.getDataCategory()).isEqualTo(DEFAULT_DATA_CATEGORY);
    }

    @Test
    @Transactional
    public void createDataModelMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataModelMappingRepository.findAll().size();

        // Create the DataModelMapping with an existing ID
        dataModelMapping.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataModelMappingMockMvc.perform(post("/api/data-model-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataModelMapping)))
            .andExpect(status().isBadRequest());

        // Validate the DataModelMapping in the database
        List<DataModelMapping> dataModelMappingList = dataModelMappingRepository.findAll();
        assertThat(dataModelMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFieldNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataModelMappingRepository.findAll().size();
        // set the field null
        dataModelMapping.setFieldName(null);

        // Create the DataModelMapping, which fails.

        restDataModelMappingMockMvc.perform(post("/api/data-model-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataModelMapping)))
            .andExpect(status().isBadRequest());

        List<DataModelMapping> dataModelMappingList = dataModelMappingRepository.findAll();
        assertThat(dataModelMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFieldDataTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataModelMappingRepository.findAll().size();
        // set the field null
        dataModelMapping.setFieldDataType(null);

        // Create the DataModelMapping, which fails.

        restDataModelMappingMockMvc.perform(post("/api/data-model-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataModelMapping)))
            .andExpect(status().isBadRequest());

        List<DataModelMapping> dataModelMappingList = dataModelMappingRepository.findAll();
        assertThat(dataModelMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFieldOrderNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataModelMappingRepository.findAll().size();
        // set the field null
        dataModelMapping.setFieldOrderNumber(null);

        // Create the DataModelMapping, which fails.

        restDataModelMappingMockMvc.perform(post("/api/data-model-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataModelMapping)))
            .andExpect(status().isBadRequest());

        List<DataModelMapping> dataModelMappingList = dataModelMappingRepository.findAll();
        assertThat(dataModelMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPiiIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataModelMappingRepository.findAll().size();
        // set the field null
        dataModelMapping.setPii(null);

        // Create the DataModelMapping, which fails.

        restDataModelMappingMockMvc.perform(post("/api/data-model-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataModelMapping)))
            .andExpect(status().isBadRequest());

        List<DataModelMapping> dataModelMappingList = dataModelMappingRepository.findAll();
        assertThat(dataModelMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataModelMappingRepository.findAll().size();
        // set the field null
        dataModelMapping.setDataCategory(null);

        // Create the DataModelMapping, which fails.

        restDataModelMappingMockMvc.perform(post("/api/data-model-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataModelMapping)))
            .andExpect(status().isBadRequest());

        List<DataModelMapping> dataModelMappingList = dataModelMappingRepository.findAll();
        assertThat(dataModelMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDataModelMappings() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList
        restDataModelMappingMockMvc.perform(get("/api/data-model-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataModelMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME.toString())))
            .andExpect(jsonPath("$.[*].fieldDataType").value(hasItem(DEFAULT_FIELD_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fieldOrderNumber").value(hasItem(DEFAULT_FIELD_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].fieldScale").value(hasItem(DEFAULT_FIELD_SCALE)))
            .andExpect(jsonPath("$.[*].fieldPrecision").value(hasItem(DEFAULT_FIELD_PRECISION)))
            .andExpect(jsonPath("$.[*].pii").value(hasItem(DEFAULT_PII.booleanValue())))
            .andExpect(jsonPath("$.[*].dataCategory").value(hasItem(DEFAULT_DATA_CATEGORY.toString())));
    }
    
    @Test
    @Transactional
    public void getDataModelMapping() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get the dataModelMapping
        restDataModelMappingMockMvc.perform(get("/api/data-model-mappings/{id}", dataModelMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataModelMapping.getId().intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME.toString()))
            .andExpect(jsonPath("$.fieldDataType").value(DEFAULT_FIELD_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.fieldOrderNumber").value(DEFAULT_FIELD_ORDER_NUMBER))
            .andExpect(jsonPath("$.fieldScale").value(DEFAULT_FIELD_SCALE))
            .andExpect(jsonPath("$.fieldPrecision").value(DEFAULT_FIELD_PRECISION))
            .andExpect(jsonPath("$.pii").value(DEFAULT_PII.booleanValue()))
            .andExpect(jsonPath("$.dataCategory").value(DEFAULT_DATA_CATEGORY.toString()));
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldName equals to DEFAULT_FIELD_NAME
        defaultDataModelMappingShouldBeFound("fieldName.equals=" + DEFAULT_FIELD_NAME);

        // Get all the dataModelMappingList where fieldName equals to UPDATED_FIELD_NAME
        defaultDataModelMappingShouldNotBeFound("fieldName.equals=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldNameIsInShouldWork() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldName in DEFAULT_FIELD_NAME or UPDATED_FIELD_NAME
        defaultDataModelMappingShouldBeFound("fieldName.in=" + DEFAULT_FIELD_NAME + "," + UPDATED_FIELD_NAME);

        // Get all the dataModelMappingList where fieldName equals to UPDATED_FIELD_NAME
        defaultDataModelMappingShouldNotBeFound("fieldName.in=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldName is not null
        defaultDataModelMappingShouldBeFound("fieldName.specified=true");

        // Get all the dataModelMappingList where fieldName is null
        defaultDataModelMappingShouldNotBeFound("fieldName.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldDataTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldDataType equals to DEFAULT_FIELD_DATA_TYPE
        defaultDataModelMappingShouldBeFound("fieldDataType.equals=" + DEFAULT_FIELD_DATA_TYPE);

        // Get all the dataModelMappingList where fieldDataType equals to UPDATED_FIELD_DATA_TYPE
        defaultDataModelMappingShouldNotBeFound("fieldDataType.equals=" + UPDATED_FIELD_DATA_TYPE);
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldDataTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldDataType in DEFAULT_FIELD_DATA_TYPE or UPDATED_FIELD_DATA_TYPE
        defaultDataModelMappingShouldBeFound("fieldDataType.in=" + DEFAULT_FIELD_DATA_TYPE + "," + UPDATED_FIELD_DATA_TYPE);

        // Get all the dataModelMappingList where fieldDataType equals to UPDATED_FIELD_DATA_TYPE
        defaultDataModelMappingShouldNotBeFound("fieldDataType.in=" + UPDATED_FIELD_DATA_TYPE);
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldDataTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldDataType is not null
        defaultDataModelMappingShouldBeFound("fieldDataType.specified=true");

        // Get all the dataModelMappingList where fieldDataType is null
        defaultDataModelMappingShouldNotBeFound("fieldDataType.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldOrderNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldOrderNumber equals to DEFAULT_FIELD_ORDER_NUMBER
        defaultDataModelMappingShouldBeFound("fieldOrderNumber.equals=" + DEFAULT_FIELD_ORDER_NUMBER);

        // Get all the dataModelMappingList where fieldOrderNumber equals to UPDATED_FIELD_ORDER_NUMBER
        defaultDataModelMappingShouldNotBeFound("fieldOrderNumber.equals=" + UPDATED_FIELD_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldOrderNumberIsInShouldWork() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldOrderNumber in DEFAULT_FIELD_ORDER_NUMBER or UPDATED_FIELD_ORDER_NUMBER
        defaultDataModelMappingShouldBeFound("fieldOrderNumber.in=" + DEFAULT_FIELD_ORDER_NUMBER + "," + UPDATED_FIELD_ORDER_NUMBER);

        // Get all the dataModelMappingList where fieldOrderNumber equals to UPDATED_FIELD_ORDER_NUMBER
        defaultDataModelMappingShouldNotBeFound("fieldOrderNumber.in=" + UPDATED_FIELD_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldOrderNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldOrderNumber is not null
        defaultDataModelMappingShouldBeFound("fieldOrderNumber.specified=true");

        // Get all the dataModelMappingList where fieldOrderNumber is null
        defaultDataModelMappingShouldNotBeFound("fieldOrderNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldOrderNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldOrderNumber greater than or equals to DEFAULT_FIELD_ORDER_NUMBER
        defaultDataModelMappingShouldBeFound("fieldOrderNumber.greaterOrEqualThan=" + DEFAULT_FIELD_ORDER_NUMBER);

        // Get all the dataModelMappingList where fieldOrderNumber greater than or equals to UPDATED_FIELD_ORDER_NUMBER
        defaultDataModelMappingShouldNotBeFound("fieldOrderNumber.greaterOrEqualThan=" + UPDATED_FIELD_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldOrderNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldOrderNumber less than or equals to DEFAULT_FIELD_ORDER_NUMBER
        defaultDataModelMappingShouldNotBeFound("fieldOrderNumber.lessThan=" + DEFAULT_FIELD_ORDER_NUMBER);

        // Get all the dataModelMappingList where fieldOrderNumber less than or equals to UPDATED_FIELD_ORDER_NUMBER
        defaultDataModelMappingShouldBeFound("fieldOrderNumber.lessThan=" + UPDATED_FIELD_ORDER_NUMBER);
    }


    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldScaleIsEqualToSomething() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldScale equals to DEFAULT_FIELD_SCALE
        defaultDataModelMappingShouldBeFound("fieldScale.equals=" + DEFAULT_FIELD_SCALE);

        // Get all the dataModelMappingList where fieldScale equals to UPDATED_FIELD_SCALE
        defaultDataModelMappingShouldNotBeFound("fieldScale.equals=" + UPDATED_FIELD_SCALE);
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldScaleIsInShouldWork() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldScale in DEFAULT_FIELD_SCALE or UPDATED_FIELD_SCALE
        defaultDataModelMappingShouldBeFound("fieldScale.in=" + DEFAULT_FIELD_SCALE + "," + UPDATED_FIELD_SCALE);

        // Get all the dataModelMappingList where fieldScale equals to UPDATED_FIELD_SCALE
        defaultDataModelMappingShouldNotBeFound("fieldScale.in=" + UPDATED_FIELD_SCALE);
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldScaleIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldScale is not null
        defaultDataModelMappingShouldBeFound("fieldScale.specified=true");

        // Get all the dataModelMappingList where fieldScale is null
        defaultDataModelMappingShouldNotBeFound("fieldScale.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldScaleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldScale greater than or equals to DEFAULT_FIELD_SCALE
        defaultDataModelMappingShouldBeFound("fieldScale.greaterOrEqualThan=" + DEFAULT_FIELD_SCALE);

        // Get all the dataModelMappingList where fieldScale greater than or equals to UPDATED_FIELD_SCALE
        defaultDataModelMappingShouldNotBeFound("fieldScale.greaterOrEqualThan=" + UPDATED_FIELD_SCALE);
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldScaleIsLessThanSomething() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldScale less than or equals to DEFAULT_FIELD_SCALE
        defaultDataModelMappingShouldNotBeFound("fieldScale.lessThan=" + DEFAULT_FIELD_SCALE);

        // Get all the dataModelMappingList where fieldScale less than or equals to UPDATED_FIELD_SCALE
        defaultDataModelMappingShouldBeFound("fieldScale.lessThan=" + UPDATED_FIELD_SCALE);
    }


    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldPrecisionIsEqualToSomething() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldPrecision equals to DEFAULT_FIELD_PRECISION
        defaultDataModelMappingShouldBeFound("fieldPrecision.equals=" + DEFAULT_FIELD_PRECISION);

        // Get all the dataModelMappingList where fieldPrecision equals to UPDATED_FIELD_PRECISION
        defaultDataModelMappingShouldNotBeFound("fieldPrecision.equals=" + UPDATED_FIELD_PRECISION);
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldPrecisionIsInShouldWork() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldPrecision in DEFAULT_FIELD_PRECISION or UPDATED_FIELD_PRECISION
        defaultDataModelMappingShouldBeFound("fieldPrecision.in=" + DEFAULT_FIELD_PRECISION + "," + UPDATED_FIELD_PRECISION);

        // Get all the dataModelMappingList where fieldPrecision equals to UPDATED_FIELD_PRECISION
        defaultDataModelMappingShouldNotBeFound("fieldPrecision.in=" + UPDATED_FIELD_PRECISION);
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldPrecisionIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldPrecision is not null
        defaultDataModelMappingShouldBeFound("fieldPrecision.specified=true");

        // Get all the dataModelMappingList where fieldPrecision is null
        defaultDataModelMappingShouldNotBeFound("fieldPrecision.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldPrecisionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldPrecision greater than or equals to DEFAULT_FIELD_PRECISION
        defaultDataModelMappingShouldBeFound("fieldPrecision.greaterOrEqualThan=" + DEFAULT_FIELD_PRECISION);

        // Get all the dataModelMappingList where fieldPrecision greater than or equals to UPDATED_FIELD_PRECISION
        defaultDataModelMappingShouldNotBeFound("fieldPrecision.greaterOrEqualThan=" + UPDATED_FIELD_PRECISION);
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByFieldPrecisionIsLessThanSomething() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where fieldPrecision less than or equals to DEFAULT_FIELD_PRECISION
        defaultDataModelMappingShouldNotBeFound("fieldPrecision.lessThan=" + DEFAULT_FIELD_PRECISION);

        // Get all the dataModelMappingList where fieldPrecision less than or equals to UPDATED_FIELD_PRECISION
        defaultDataModelMappingShouldBeFound("fieldPrecision.lessThan=" + UPDATED_FIELD_PRECISION);
    }


    @Test
    @Transactional
    public void getAllDataModelMappingsByPiiIsEqualToSomething() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where pii equals to DEFAULT_PII
        defaultDataModelMappingShouldBeFound("pii.equals=" + DEFAULT_PII);

        // Get all the dataModelMappingList where pii equals to UPDATED_PII
        defaultDataModelMappingShouldNotBeFound("pii.equals=" + UPDATED_PII);
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByPiiIsInShouldWork() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where pii in DEFAULT_PII or UPDATED_PII
        defaultDataModelMappingShouldBeFound("pii.in=" + DEFAULT_PII + "," + UPDATED_PII);

        // Get all the dataModelMappingList where pii equals to UPDATED_PII
        defaultDataModelMappingShouldNotBeFound("pii.in=" + UPDATED_PII);
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByPiiIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where pii is not null
        defaultDataModelMappingShouldBeFound("pii.specified=true");

        // Get all the dataModelMappingList where pii is null
        defaultDataModelMappingShouldNotBeFound("pii.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByDataCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where dataCategory equals to DEFAULT_DATA_CATEGORY
        defaultDataModelMappingShouldBeFound("dataCategory.equals=" + DEFAULT_DATA_CATEGORY);

        // Get all the dataModelMappingList where dataCategory equals to UPDATED_DATA_CATEGORY
        defaultDataModelMappingShouldNotBeFound("dataCategory.equals=" + UPDATED_DATA_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByDataCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where dataCategory in DEFAULT_DATA_CATEGORY or UPDATED_DATA_CATEGORY
        defaultDataModelMappingShouldBeFound("dataCategory.in=" + DEFAULT_DATA_CATEGORY + "," + UPDATED_DATA_CATEGORY);

        // Get all the dataModelMappingList where dataCategory equals to UPDATED_DATA_CATEGORY
        defaultDataModelMappingShouldNotBeFound("dataCategory.in=" + UPDATED_DATA_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByDataCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataModelMappingRepository.saveAndFlush(dataModelMapping);

        // Get all the dataModelMappingList where dataCategory is not null
        defaultDataModelMappingShouldBeFound("dataCategory.specified=true");

        // Get all the dataModelMappingList where dataCategory is null
        defaultDataModelMappingShouldNotBeFound("dataCategory.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataModelMappingsByDataModelIsEqualToSomething() throws Exception {
        // Initialize the database
        DataModel dataModel = DataModelResourceIntTest.createEntity(em);
        em.persist(dataModel);
        em.flush();
        dataModelMapping.setDataModel(dataModel);
        dataModelMappingRepository.saveAndFlush(dataModelMapping);
        Long dataModelId = dataModel.getId();

        // Get all the dataModelMappingList where dataModel equals to dataModelId
        defaultDataModelMappingShouldBeFound("dataModelId.equals=" + dataModelId);

        // Get all the dataModelMappingList where dataModel equals to dataModelId + 1
        defaultDataModelMappingShouldNotBeFound("dataModelId.equals=" + (dataModelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDataModelMappingShouldBeFound(String filter) throws Exception {
        restDataModelMappingMockMvc.perform(get("/api/data-model-mappings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataModelMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME.toString())))
            .andExpect(jsonPath("$.[*].fieldDataType").value(hasItem(DEFAULT_FIELD_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fieldOrderNumber").value(hasItem(DEFAULT_FIELD_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].fieldScale").value(hasItem(DEFAULT_FIELD_SCALE)))
            .andExpect(jsonPath("$.[*].fieldPrecision").value(hasItem(DEFAULT_FIELD_PRECISION)))
            .andExpect(jsonPath("$.[*].pii").value(hasItem(DEFAULT_PII.booleanValue())))
            .andExpect(jsonPath("$.[*].dataCategory").value(hasItem(DEFAULT_DATA_CATEGORY.toString())));

        // Check, that the count call also returns 1
        restDataModelMappingMockMvc.perform(get("/api/data-model-mappings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDataModelMappingShouldNotBeFound(String filter) throws Exception {
        restDataModelMappingMockMvc.perform(get("/api/data-model-mappings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDataModelMappingMockMvc.perform(get("/api/data-model-mappings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDataModelMapping() throws Exception {
        // Get the dataModelMapping
        restDataModelMappingMockMvc.perform(get("/api/data-model-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataModelMapping() throws Exception {
        // Initialize the database
        dataModelMappingService.save(dataModelMapping);

        int databaseSizeBeforeUpdate = dataModelMappingRepository.findAll().size();

        // Update the dataModelMapping
        DataModelMapping updatedDataModelMapping = dataModelMappingRepository.findById(dataModelMapping.getId()).get();
        // Disconnect from session so that the updates on updatedDataModelMapping are not directly saved in db
        em.detach(updatedDataModelMapping);
        updatedDataModelMapping
            .fieldName(UPDATED_FIELD_NAME)
            .fieldDataType(UPDATED_FIELD_DATA_TYPE)
            .fieldOrderNumber(UPDATED_FIELD_ORDER_NUMBER)
            .fieldScale(UPDATED_FIELD_SCALE)
            .fieldPrecision(UPDATED_FIELD_PRECISION)
            .pii(UPDATED_PII)
            .dataCategory(UPDATED_DATA_CATEGORY);

        restDataModelMappingMockMvc.perform(put("/api/data-model-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDataModelMapping)))
            .andExpect(status().isOk());

        // Validate the DataModelMapping in the database
        List<DataModelMapping> dataModelMappingList = dataModelMappingRepository.findAll();
        assertThat(dataModelMappingList).hasSize(databaseSizeBeforeUpdate);
        DataModelMapping testDataModelMapping = dataModelMappingList.get(dataModelMappingList.size() - 1);
        assertThat(testDataModelMapping.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testDataModelMapping.getFieldDataType()).isEqualTo(UPDATED_FIELD_DATA_TYPE);
        assertThat(testDataModelMapping.getFieldOrderNumber()).isEqualTo(UPDATED_FIELD_ORDER_NUMBER);
        assertThat(testDataModelMapping.getFieldScale()).isEqualTo(UPDATED_FIELD_SCALE);
        assertThat(testDataModelMapping.getFieldPrecision()).isEqualTo(UPDATED_FIELD_PRECISION);
        assertThat(testDataModelMapping.isPii()).isEqualTo(UPDATED_PII);
        assertThat(testDataModelMapping.getDataCategory()).isEqualTo(UPDATED_DATA_CATEGORY);
    }

    @Test
    @Transactional
    public void updateNonExistingDataModelMapping() throws Exception {
        int databaseSizeBeforeUpdate = dataModelMappingRepository.findAll().size();

        // Create the DataModelMapping

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataModelMappingMockMvc.perform(put("/api/data-model-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataModelMapping)))
            .andExpect(status().isBadRequest());

        // Validate the DataModelMapping in the database
        List<DataModelMapping> dataModelMappingList = dataModelMappingRepository.findAll();
        assertThat(dataModelMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataModelMapping() throws Exception {
        // Initialize the database
        dataModelMappingService.save(dataModelMapping);

        int databaseSizeBeforeDelete = dataModelMappingRepository.findAll().size();

        // Get the dataModelMapping
        restDataModelMappingMockMvc.perform(delete("/api/data-model-mappings/{id}", dataModelMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DataModelMapping> dataModelMappingList = dataModelMappingRepository.findAll();
        assertThat(dataModelMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataModelMapping.class);
        DataModelMapping dataModelMapping1 = new DataModelMapping();
        dataModelMapping1.setId(1L);
        DataModelMapping dataModelMapping2 = new DataModelMapping();
        dataModelMapping2.setId(dataModelMapping1.getId());
        assertThat(dataModelMapping1).isEqualTo(dataModelMapping2);
        dataModelMapping2.setId(2L);
        assertThat(dataModelMapping1).isNotEqualTo(dataModelMapping2);
        dataModelMapping1.setId(null);
        assertThat(dataModelMapping1).isNotEqualTo(dataModelMapping2);
    }
}
