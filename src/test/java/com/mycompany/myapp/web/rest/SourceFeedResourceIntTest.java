package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyApp;

import com.mycompany.myapp.domain.SourceFeed;
import com.mycompany.myapp.domain.Application;
import com.mycompany.myapp.domain.SourceDatabase;
import com.mycompany.myapp.domain.SourceFeedMapping;
import com.mycompany.myapp.repository.SourceFeedRepository;
import com.mycompany.myapp.service.SourceFeedService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.SourceFeedCriteria;
import com.mycompany.myapp.service.SourceFeedQueryService;

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

import com.mycompany.myapp.domain.enumeration.Frequency;
/**
 * Test class for the SourceFeedResource REST controller.
 *
 * @see SourceFeedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApp.class)
public class SourceFeedResourceIntTest {

    private static final String DEFAULT_FEED_CODE = "AAAAAAAAAA";
    private static final String UPDATED_FEED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME_PATTERN = "BBBBBBBBBB";

    private static final Integer DEFAULT_HEADER_COUNT = 1;
    private static final Integer UPDATED_HEADER_COUNT = 2;

    private static final Integer DEFAULT_TRAILER_COUNT = 1;
    private static final Integer UPDATED_TRAILER_COUNT = 2;

    private static final String DEFAULT_TRAILER_RECORD_STARTS_WITH = "AAAAAAAAAA";
    private static final String UPDATED_TRAILER_RECORD_STARTS_WITH = "BBBBBBBBBB";

    private static final Frequency DEFAULT_FEED_FREQUENCY = Frequency.DAILY;
    private static final Frequency UPDATED_FEED_FREQUENCY = Frequency.WEEKLY;

    private static final String DEFAULT_SLA = "AAAAAAAAAA";
    private static final String UPDATED_SLA = "BBBBBBBBBB";

    @Autowired
    private SourceFeedRepository sourceFeedRepository;
    
    @Autowired
    private SourceFeedService sourceFeedService;

    @Autowired
    private SourceFeedQueryService sourceFeedQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSourceFeedMockMvc;

    private SourceFeed sourceFeed;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SourceFeedResource sourceFeedResource = new SourceFeedResource(sourceFeedService, sourceFeedQueryService);
        this.restSourceFeedMockMvc = MockMvcBuilders.standaloneSetup(sourceFeedResource)
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
    public static SourceFeed createEntity(EntityManager em) {
        SourceFeed sourceFeed = new SourceFeed()
            .feedCode(DEFAULT_FEED_CODE)
            .fileNamePattern(DEFAULT_FILE_NAME_PATTERN)
            .headerCount(DEFAULT_HEADER_COUNT)
            .trailerCount(DEFAULT_TRAILER_COUNT)
            .trailerRecordStartsWith(DEFAULT_TRAILER_RECORD_STARTS_WITH)
            .feedFrequency(DEFAULT_FEED_FREQUENCY)
            .sla(DEFAULT_SLA);
        return sourceFeed;
    }

    @Before
    public void initTest() {
        sourceFeed = createEntity(em);
    }

    @Test
    @Transactional
    public void createSourceFeed() throws Exception {
        int databaseSizeBeforeCreate = sourceFeedRepository.findAll().size();

        // Create the SourceFeed
        restSourceFeedMockMvc.perform(post("/api/source-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceFeed)))
            .andExpect(status().isCreated());

        // Validate the SourceFeed in the database
        List<SourceFeed> sourceFeedList = sourceFeedRepository.findAll();
        assertThat(sourceFeedList).hasSize(databaseSizeBeforeCreate + 1);
        SourceFeed testSourceFeed = sourceFeedList.get(sourceFeedList.size() - 1);
        assertThat(testSourceFeed.getFeedCode()).isEqualTo(DEFAULT_FEED_CODE);
        assertThat(testSourceFeed.getFileNamePattern()).isEqualTo(DEFAULT_FILE_NAME_PATTERN);
        assertThat(testSourceFeed.getHeaderCount()).isEqualTo(DEFAULT_HEADER_COUNT);
        assertThat(testSourceFeed.getTrailerCount()).isEqualTo(DEFAULT_TRAILER_COUNT);
        assertThat(testSourceFeed.getTrailerRecordStartsWith()).isEqualTo(DEFAULT_TRAILER_RECORD_STARTS_WITH);
        assertThat(testSourceFeed.getFeedFrequency()).isEqualTo(DEFAULT_FEED_FREQUENCY);
        assertThat(testSourceFeed.getSla()).isEqualTo(DEFAULT_SLA);
    }

    @Test
    @Transactional
    public void createSourceFeedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sourceFeedRepository.findAll().size();

        // Create the SourceFeed with an existing ID
        sourceFeed.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSourceFeedMockMvc.perform(post("/api/source-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceFeed)))
            .andExpect(status().isBadRequest());

        // Validate the SourceFeed in the database
        List<SourceFeed> sourceFeedList = sourceFeedRepository.findAll();
        assertThat(sourceFeedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFeedCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceFeedRepository.findAll().size();
        // set the field null
        sourceFeed.setFeedCode(null);

        // Create the SourceFeed, which fails.

        restSourceFeedMockMvc.perform(post("/api/source-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceFeed)))
            .andExpect(status().isBadRequest());

        List<SourceFeed> sourceFeedList = sourceFeedRepository.findAll();
        assertThat(sourceFeedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileNamePatternIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceFeedRepository.findAll().size();
        // set the field null
        sourceFeed.setFileNamePattern(null);

        // Create the SourceFeed, which fails.

        restSourceFeedMockMvc.perform(post("/api/source-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceFeed)))
            .andExpect(status().isBadRequest());

        List<SourceFeed> sourceFeedList = sourceFeedRepository.findAll();
        assertThat(sourceFeedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeaderCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceFeedRepository.findAll().size();
        // set the field null
        sourceFeed.setHeaderCount(null);

        // Create the SourceFeed, which fails.

        restSourceFeedMockMvc.perform(post("/api/source-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceFeed)))
            .andExpect(status().isBadRequest());

        List<SourceFeed> sourceFeedList = sourceFeedRepository.findAll();
        assertThat(sourceFeedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrailerCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceFeedRepository.findAll().size();
        // set the field null
        sourceFeed.setTrailerCount(null);

        // Create the SourceFeed, which fails.

        restSourceFeedMockMvc.perform(post("/api/source-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceFeed)))
            .andExpect(status().isBadRequest());

        List<SourceFeed> sourceFeedList = sourceFeedRepository.findAll();
        assertThat(sourceFeedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFeedFrequencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceFeedRepository.findAll().size();
        // set the field null
        sourceFeed.setFeedFrequency(null);

        // Create the SourceFeed, which fails.

        restSourceFeedMockMvc.perform(post("/api/source-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceFeed)))
            .andExpect(status().isBadRequest());

        List<SourceFeed> sourceFeedList = sourceFeedRepository.findAll();
        assertThat(sourceFeedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSourceFeeds() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList
        restSourceFeedMockMvc.perform(get("/api/source-feeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sourceFeed.getId().intValue())))
            .andExpect(jsonPath("$.[*].feedCode").value(hasItem(DEFAULT_FEED_CODE.toString())))
            .andExpect(jsonPath("$.[*].fileNamePattern").value(hasItem(DEFAULT_FILE_NAME_PATTERN.toString())))
            .andExpect(jsonPath("$.[*].headerCount").value(hasItem(DEFAULT_HEADER_COUNT)))
            .andExpect(jsonPath("$.[*].trailerCount").value(hasItem(DEFAULT_TRAILER_COUNT)))
            .andExpect(jsonPath("$.[*].trailerRecordStartsWith").value(hasItem(DEFAULT_TRAILER_RECORD_STARTS_WITH.toString())))
            .andExpect(jsonPath("$.[*].feedFrequency").value(hasItem(DEFAULT_FEED_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].sla").value(hasItem(DEFAULT_SLA.toString())));
    }
    
    @Test
    @Transactional
    public void getSourceFeed() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get the sourceFeed
        restSourceFeedMockMvc.perform(get("/api/source-feeds/{id}", sourceFeed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sourceFeed.getId().intValue()))
            .andExpect(jsonPath("$.feedCode").value(DEFAULT_FEED_CODE.toString()))
            .andExpect(jsonPath("$.fileNamePattern").value(DEFAULT_FILE_NAME_PATTERN.toString()))
            .andExpect(jsonPath("$.headerCount").value(DEFAULT_HEADER_COUNT))
            .andExpect(jsonPath("$.trailerCount").value(DEFAULT_TRAILER_COUNT))
            .andExpect(jsonPath("$.trailerRecordStartsWith").value(DEFAULT_TRAILER_RECORD_STARTS_WITH.toString()))
            .andExpect(jsonPath("$.feedFrequency").value(DEFAULT_FEED_FREQUENCY.toString()))
            .andExpect(jsonPath("$.sla").value(DEFAULT_SLA.toString()));
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByFeedCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where feedCode equals to DEFAULT_FEED_CODE
        defaultSourceFeedShouldBeFound("feedCode.equals=" + DEFAULT_FEED_CODE);

        // Get all the sourceFeedList where feedCode equals to UPDATED_FEED_CODE
        defaultSourceFeedShouldNotBeFound("feedCode.equals=" + UPDATED_FEED_CODE);
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByFeedCodeIsInShouldWork() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where feedCode in DEFAULT_FEED_CODE or UPDATED_FEED_CODE
        defaultSourceFeedShouldBeFound("feedCode.in=" + DEFAULT_FEED_CODE + "," + UPDATED_FEED_CODE);

        // Get all the sourceFeedList where feedCode equals to UPDATED_FEED_CODE
        defaultSourceFeedShouldNotBeFound("feedCode.in=" + UPDATED_FEED_CODE);
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByFeedCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where feedCode is not null
        defaultSourceFeedShouldBeFound("feedCode.specified=true");

        // Get all the sourceFeedList where feedCode is null
        defaultSourceFeedShouldNotBeFound("feedCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByFileNamePatternIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where fileNamePattern equals to DEFAULT_FILE_NAME_PATTERN
        defaultSourceFeedShouldBeFound("fileNamePattern.equals=" + DEFAULT_FILE_NAME_PATTERN);

        // Get all the sourceFeedList where fileNamePattern equals to UPDATED_FILE_NAME_PATTERN
        defaultSourceFeedShouldNotBeFound("fileNamePattern.equals=" + UPDATED_FILE_NAME_PATTERN);
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByFileNamePatternIsInShouldWork() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where fileNamePattern in DEFAULT_FILE_NAME_PATTERN or UPDATED_FILE_NAME_PATTERN
        defaultSourceFeedShouldBeFound("fileNamePattern.in=" + DEFAULT_FILE_NAME_PATTERN + "," + UPDATED_FILE_NAME_PATTERN);

        // Get all the sourceFeedList where fileNamePattern equals to UPDATED_FILE_NAME_PATTERN
        defaultSourceFeedShouldNotBeFound("fileNamePattern.in=" + UPDATED_FILE_NAME_PATTERN);
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByFileNamePatternIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where fileNamePattern is not null
        defaultSourceFeedShouldBeFound("fileNamePattern.specified=true");

        // Get all the sourceFeedList where fileNamePattern is null
        defaultSourceFeedShouldNotBeFound("fileNamePattern.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByHeaderCountIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where headerCount equals to DEFAULT_HEADER_COUNT
        defaultSourceFeedShouldBeFound("headerCount.equals=" + DEFAULT_HEADER_COUNT);

        // Get all the sourceFeedList where headerCount equals to UPDATED_HEADER_COUNT
        defaultSourceFeedShouldNotBeFound("headerCount.equals=" + UPDATED_HEADER_COUNT);
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByHeaderCountIsInShouldWork() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where headerCount in DEFAULT_HEADER_COUNT or UPDATED_HEADER_COUNT
        defaultSourceFeedShouldBeFound("headerCount.in=" + DEFAULT_HEADER_COUNT + "," + UPDATED_HEADER_COUNT);

        // Get all the sourceFeedList where headerCount equals to UPDATED_HEADER_COUNT
        defaultSourceFeedShouldNotBeFound("headerCount.in=" + UPDATED_HEADER_COUNT);
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByHeaderCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where headerCount is not null
        defaultSourceFeedShouldBeFound("headerCount.specified=true");

        // Get all the sourceFeedList where headerCount is null
        defaultSourceFeedShouldNotBeFound("headerCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByHeaderCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where headerCount greater than or equals to DEFAULT_HEADER_COUNT
        defaultSourceFeedShouldBeFound("headerCount.greaterOrEqualThan=" + DEFAULT_HEADER_COUNT);

        // Get all the sourceFeedList where headerCount greater than or equals to UPDATED_HEADER_COUNT
        defaultSourceFeedShouldNotBeFound("headerCount.greaterOrEqualThan=" + UPDATED_HEADER_COUNT);
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByHeaderCountIsLessThanSomething() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where headerCount less than or equals to DEFAULT_HEADER_COUNT
        defaultSourceFeedShouldNotBeFound("headerCount.lessThan=" + DEFAULT_HEADER_COUNT);

        // Get all the sourceFeedList where headerCount less than or equals to UPDATED_HEADER_COUNT
        defaultSourceFeedShouldBeFound("headerCount.lessThan=" + UPDATED_HEADER_COUNT);
    }


    @Test
    @Transactional
    public void getAllSourceFeedsByTrailerCountIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where trailerCount equals to DEFAULT_TRAILER_COUNT
        defaultSourceFeedShouldBeFound("trailerCount.equals=" + DEFAULT_TRAILER_COUNT);

        // Get all the sourceFeedList where trailerCount equals to UPDATED_TRAILER_COUNT
        defaultSourceFeedShouldNotBeFound("trailerCount.equals=" + UPDATED_TRAILER_COUNT);
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByTrailerCountIsInShouldWork() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where trailerCount in DEFAULT_TRAILER_COUNT or UPDATED_TRAILER_COUNT
        defaultSourceFeedShouldBeFound("trailerCount.in=" + DEFAULT_TRAILER_COUNT + "," + UPDATED_TRAILER_COUNT);

        // Get all the sourceFeedList where trailerCount equals to UPDATED_TRAILER_COUNT
        defaultSourceFeedShouldNotBeFound("trailerCount.in=" + UPDATED_TRAILER_COUNT);
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByTrailerCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where trailerCount is not null
        defaultSourceFeedShouldBeFound("trailerCount.specified=true");

        // Get all the sourceFeedList where trailerCount is null
        defaultSourceFeedShouldNotBeFound("trailerCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByTrailerCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where trailerCount greater than or equals to DEFAULT_TRAILER_COUNT
        defaultSourceFeedShouldBeFound("trailerCount.greaterOrEqualThan=" + DEFAULT_TRAILER_COUNT);

        // Get all the sourceFeedList where trailerCount greater than or equals to UPDATED_TRAILER_COUNT
        defaultSourceFeedShouldNotBeFound("trailerCount.greaterOrEqualThan=" + UPDATED_TRAILER_COUNT);
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByTrailerCountIsLessThanSomething() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where trailerCount less than or equals to DEFAULT_TRAILER_COUNT
        defaultSourceFeedShouldNotBeFound("trailerCount.lessThan=" + DEFAULT_TRAILER_COUNT);

        // Get all the sourceFeedList where trailerCount less than or equals to UPDATED_TRAILER_COUNT
        defaultSourceFeedShouldBeFound("trailerCount.lessThan=" + UPDATED_TRAILER_COUNT);
    }


    @Test
    @Transactional
    public void getAllSourceFeedsByTrailerRecordStartsWithIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where trailerRecordStartsWith equals to DEFAULT_TRAILER_RECORD_STARTS_WITH
        defaultSourceFeedShouldBeFound("trailerRecordStartsWith.equals=" + DEFAULT_TRAILER_RECORD_STARTS_WITH);

        // Get all the sourceFeedList where trailerRecordStartsWith equals to UPDATED_TRAILER_RECORD_STARTS_WITH
        defaultSourceFeedShouldNotBeFound("trailerRecordStartsWith.equals=" + UPDATED_TRAILER_RECORD_STARTS_WITH);
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByTrailerRecordStartsWithIsInShouldWork() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where trailerRecordStartsWith in DEFAULT_TRAILER_RECORD_STARTS_WITH or UPDATED_TRAILER_RECORD_STARTS_WITH
        defaultSourceFeedShouldBeFound("trailerRecordStartsWith.in=" + DEFAULT_TRAILER_RECORD_STARTS_WITH + "," + UPDATED_TRAILER_RECORD_STARTS_WITH);

        // Get all the sourceFeedList where trailerRecordStartsWith equals to UPDATED_TRAILER_RECORD_STARTS_WITH
        defaultSourceFeedShouldNotBeFound("trailerRecordStartsWith.in=" + UPDATED_TRAILER_RECORD_STARTS_WITH);
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByTrailerRecordStartsWithIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where trailerRecordStartsWith is not null
        defaultSourceFeedShouldBeFound("trailerRecordStartsWith.specified=true");

        // Get all the sourceFeedList where trailerRecordStartsWith is null
        defaultSourceFeedShouldNotBeFound("trailerRecordStartsWith.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByFeedFrequencyIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where feedFrequency equals to DEFAULT_FEED_FREQUENCY
        defaultSourceFeedShouldBeFound("feedFrequency.equals=" + DEFAULT_FEED_FREQUENCY);

        // Get all the sourceFeedList where feedFrequency equals to UPDATED_FEED_FREQUENCY
        defaultSourceFeedShouldNotBeFound("feedFrequency.equals=" + UPDATED_FEED_FREQUENCY);
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByFeedFrequencyIsInShouldWork() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where feedFrequency in DEFAULT_FEED_FREQUENCY or UPDATED_FEED_FREQUENCY
        defaultSourceFeedShouldBeFound("feedFrequency.in=" + DEFAULT_FEED_FREQUENCY + "," + UPDATED_FEED_FREQUENCY);

        // Get all the sourceFeedList where feedFrequency equals to UPDATED_FEED_FREQUENCY
        defaultSourceFeedShouldNotBeFound("feedFrequency.in=" + UPDATED_FEED_FREQUENCY);
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByFeedFrequencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where feedFrequency is not null
        defaultSourceFeedShouldBeFound("feedFrequency.specified=true");

        // Get all the sourceFeedList where feedFrequency is null
        defaultSourceFeedShouldNotBeFound("feedFrequency.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceFeedsBySlaIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where sla equals to DEFAULT_SLA
        defaultSourceFeedShouldBeFound("sla.equals=" + DEFAULT_SLA);

        // Get all the sourceFeedList where sla equals to UPDATED_SLA
        defaultSourceFeedShouldNotBeFound("sla.equals=" + UPDATED_SLA);
    }

    @Test
    @Transactional
    public void getAllSourceFeedsBySlaIsInShouldWork() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where sla in DEFAULT_SLA or UPDATED_SLA
        defaultSourceFeedShouldBeFound("sla.in=" + DEFAULT_SLA + "," + UPDATED_SLA);

        // Get all the sourceFeedList where sla equals to UPDATED_SLA
        defaultSourceFeedShouldNotBeFound("sla.in=" + UPDATED_SLA);
    }

    @Test
    @Transactional
    public void getAllSourceFeedsBySlaIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceFeedRepository.saveAndFlush(sourceFeed);

        // Get all the sourceFeedList where sla is not null
        defaultSourceFeedShouldBeFound("sla.specified=true");

        // Get all the sourceFeedList where sla is null
        defaultSourceFeedShouldNotBeFound("sla.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceFeedsByApplicationIsEqualToSomething() throws Exception {
        // Initialize the database
        Application application = ApplicationResourceIntTest.createEntity(em);
        em.persist(application);
        em.flush();
        sourceFeed.setApplication(application);
        sourceFeedRepository.saveAndFlush(sourceFeed);
        Long applicationId = application.getId();

        // Get all the sourceFeedList where application equals to applicationId
        defaultSourceFeedShouldBeFound("applicationId.equals=" + applicationId);

        // Get all the sourceFeedList where application equals to applicationId + 1
        defaultSourceFeedShouldNotBeFound("applicationId.equals=" + (applicationId + 1));
    }


    @Test
    @Transactional
    public void getAllSourceFeedsBySourceDatabaseIsEqualToSomething() throws Exception {
        // Initialize the database
        SourceDatabase sourceDatabase = SourceDatabaseResourceIntTest.createEntity(em);
        em.persist(sourceDatabase);
        em.flush();
        sourceFeed.setSourceDatabase(sourceDatabase);
        sourceFeedRepository.saveAndFlush(sourceFeed);
        Long sourceDatabaseId = sourceDatabase.getId();

        // Get all the sourceFeedList where sourceDatabase equals to sourceDatabaseId
        defaultSourceFeedShouldBeFound("sourceDatabaseId.equals=" + sourceDatabaseId);

        // Get all the sourceFeedList where sourceDatabase equals to sourceDatabaseId + 1
        defaultSourceFeedShouldNotBeFound("sourceDatabaseId.equals=" + (sourceDatabaseId + 1));
    }


    @Test
    @Transactional
    public void getAllSourceFeedsBySourceFeedMappingIsEqualToSomething() throws Exception {
        // Initialize the database
        SourceFeedMapping sourceFeedMapping = SourceFeedMappingResourceIntTest.createEntity(em);
        em.persist(sourceFeedMapping);
        em.flush();
        sourceFeed.addSourceFeedMapping(sourceFeedMapping);
        sourceFeedRepository.saveAndFlush(sourceFeed);
        Long sourceFeedMappingId = sourceFeedMapping.getId();

        // Get all the sourceFeedList where sourceFeedMapping equals to sourceFeedMappingId
        defaultSourceFeedShouldBeFound("sourceFeedMappingId.equals=" + sourceFeedMappingId);

        // Get all the sourceFeedList where sourceFeedMapping equals to sourceFeedMappingId + 1
        defaultSourceFeedShouldNotBeFound("sourceFeedMappingId.equals=" + (sourceFeedMappingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSourceFeedShouldBeFound(String filter) throws Exception {
        restSourceFeedMockMvc.perform(get("/api/source-feeds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sourceFeed.getId().intValue())))
            .andExpect(jsonPath("$.[*].feedCode").value(hasItem(DEFAULT_FEED_CODE.toString())))
            .andExpect(jsonPath("$.[*].fileNamePattern").value(hasItem(DEFAULT_FILE_NAME_PATTERN.toString())))
            .andExpect(jsonPath("$.[*].headerCount").value(hasItem(DEFAULT_HEADER_COUNT)))
            .andExpect(jsonPath("$.[*].trailerCount").value(hasItem(DEFAULT_TRAILER_COUNT)))
            .andExpect(jsonPath("$.[*].trailerRecordStartsWith").value(hasItem(DEFAULT_TRAILER_RECORD_STARTS_WITH.toString())))
            .andExpect(jsonPath("$.[*].feedFrequency").value(hasItem(DEFAULT_FEED_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].sla").value(hasItem(DEFAULT_SLA.toString())));

        // Check, that the count call also returns 1
        restSourceFeedMockMvc.perform(get("/api/source-feeds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSourceFeedShouldNotBeFound(String filter) throws Exception {
        restSourceFeedMockMvc.perform(get("/api/source-feeds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSourceFeedMockMvc.perform(get("/api/source-feeds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSourceFeed() throws Exception {
        // Get the sourceFeed
        restSourceFeedMockMvc.perform(get("/api/source-feeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSourceFeed() throws Exception {
        // Initialize the database
        sourceFeedService.save(sourceFeed);

        int databaseSizeBeforeUpdate = sourceFeedRepository.findAll().size();

        // Update the sourceFeed
        SourceFeed updatedSourceFeed = sourceFeedRepository.findById(sourceFeed.getId()).get();
        // Disconnect from session so that the updates on updatedSourceFeed are not directly saved in db
        em.detach(updatedSourceFeed);
        updatedSourceFeed
            .feedCode(UPDATED_FEED_CODE)
            .fileNamePattern(UPDATED_FILE_NAME_PATTERN)
            .headerCount(UPDATED_HEADER_COUNT)
            .trailerCount(UPDATED_TRAILER_COUNT)
            .trailerRecordStartsWith(UPDATED_TRAILER_RECORD_STARTS_WITH)
            .feedFrequency(UPDATED_FEED_FREQUENCY)
            .sla(UPDATED_SLA);

        restSourceFeedMockMvc.perform(put("/api/source-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSourceFeed)))
            .andExpect(status().isOk());

        // Validate the SourceFeed in the database
        List<SourceFeed> sourceFeedList = sourceFeedRepository.findAll();
        assertThat(sourceFeedList).hasSize(databaseSizeBeforeUpdate);
        SourceFeed testSourceFeed = sourceFeedList.get(sourceFeedList.size() - 1);
        assertThat(testSourceFeed.getFeedCode()).isEqualTo(UPDATED_FEED_CODE);
        assertThat(testSourceFeed.getFileNamePattern()).isEqualTo(UPDATED_FILE_NAME_PATTERN);
        assertThat(testSourceFeed.getHeaderCount()).isEqualTo(UPDATED_HEADER_COUNT);
        assertThat(testSourceFeed.getTrailerCount()).isEqualTo(UPDATED_TRAILER_COUNT);
        assertThat(testSourceFeed.getTrailerRecordStartsWith()).isEqualTo(UPDATED_TRAILER_RECORD_STARTS_WITH);
        assertThat(testSourceFeed.getFeedFrequency()).isEqualTo(UPDATED_FEED_FREQUENCY);
        assertThat(testSourceFeed.getSla()).isEqualTo(UPDATED_SLA);
    }

    @Test
    @Transactional
    public void updateNonExistingSourceFeed() throws Exception {
        int databaseSizeBeforeUpdate = sourceFeedRepository.findAll().size();

        // Create the SourceFeed

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSourceFeedMockMvc.perform(put("/api/source-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceFeed)))
            .andExpect(status().isBadRequest());

        // Validate the SourceFeed in the database
        List<SourceFeed> sourceFeedList = sourceFeedRepository.findAll();
        assertThat(sourceFeedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSourceFeed() throws Exception {
        // Initialize the database
        sourceFeedService.save(sourceFeed);

        int databaseSizeBeforeDelete = sourceFeedRepository.findAll().size();

        // Get the sourceFeed
        restSourceFeedMockMvc.perform(delete("/api/source-feeds/{id}", sourceFeed.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SourceFeed> sourceFeedList = sourceFeedRepository.findAll();
        assertThat(sourceFeedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SourceFeed.class);
        SourceFeed sourceFeed1 = new SourceFeed();
        sourceFeed1.setId(1L);
        SourceFeed sourceFeed2 = new SourceFeed();
        sourceFeed2.setId(sourceFeed1.getId());
        assertThat(sourceFeed1).isEqualTo(sourceFeed2);
        sourceFeed2.setId(2L);
        assertThat(sourceFeed1).isNotEqualTo(sourceFeed2);
        sourceFeed1.setId(null);
        assertThat(sourceFeed1).isNotEqualTo(sourceFeed2);
    }
}
