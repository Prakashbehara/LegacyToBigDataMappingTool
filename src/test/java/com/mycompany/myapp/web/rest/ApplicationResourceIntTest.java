package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyApp;

import com.mycompany.myapp.domain.Application;
import com.mycompany.myapp.domain.SourceFeed;
import com.mycompany.myapp.domain.DataAsset;
import com.mycompany.myapp.domain.SourceDatabase;
import com.mycompany.myapp.repository.ApplicationRepository;
import com.mycompany.myapp.service.ApplicationService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.ApplicationCriteria;
import com.mycompany.myapp.service.ApplicationQueryService;

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
import com.mycompany.myapp.domain.enumeration.ProjectType;
/**
 * Test class for the ApplicationResource REST controller.
 *
 * @see ApplicationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApp.class)
public class ApplicationResourceIntTest {

    private static final String DEFAULT_APPLICATION_CODE = "AAAAAA";
    private static final String UPDATED_APPLICATION_CODE = "BBBBBB";

    private static final Domain DEFAULT_DOMAIN = Domain.POLICY;
    private static final Domain UPDATED_DOMAIN = Domain.FINANCE;

    private static final ProjectType DEFAULT_PROJECT_TYPE = ProjectType.CONVERSION;
    private static final ProjectType UPDATED_PROJECT_TYPE = ProjectType.NEW;

    private static final String DEFAULT_DESCRIPTION = "y";
    private static final String UPDATED_DESCRIPTION = "x";

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationQueryService applicationQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApplicationMockMvc;

    private Application application;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationResource applicationResource = new ApplicationResource(applicationService, applicationQueryService);
        this.restApplicationMockMvc = MockMvcBuilders.standaloneSetup(applicationResource)
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
    public static Application createEntity(EntityManager em) {
        Application application = new Application()
            .applicationCode(DEFAULT_APPLICATION_CODE)
            .domain(DEFAULT_DOMAIN)
            .projectType(DEFAULT_PROJECT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .owner(DEFAULT_OWNER);
        return application;
    }

    @Before
    public void initTest() {
        application = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplication() throws Exception {
        int databaseSizeBeforeCreate = applicationRepository.findAll().size();

        // Create the Application
        restApplicationMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isCreated());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate + 1);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getApplicationCode()).isEqualTo(DEFAULT_APPLICATION_CODE);
        assertThat(testApplication.getDomain()).isEqualTo(DEFAULT_DOMAIN);
        assertThat(testApplication.getProjectType()).isEqualTo(DEFAULT_PROJECT_TYPE);
        assertThat(testApplication.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApplication.getOwner()).isEqualTo(DEFAULT_OWNER);
    }

    @Test
    @Transactional
    public void createApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationRepository.findAll().size();

        // Create the Application with an existing ID
        application.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkApplicationCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setApplicationCode(null);

        // Create the Application, which fails.

        restApplicationMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDomainIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setDomain(null);

        // Create the Application, which fails.

        restApplicationMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setProjectType(null);

        // Create the Application, which fails.

        restApplicationMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setDescription(null);

        // Create the Application, which fails.

        restApplicationMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplications() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList
        restApplicationMockMvc.perform(get("/api/applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(application.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationCode").value(hasItem(DEFAULT_APPLICATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN.toString())))
            .andExpect(jsonPath("$.[*].projectType").value(hasItem(DEFAULT_PROJECT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER.toString())));
    }
    
    @Test
    @Transactional
    public void getApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get the application
        restApplicationMockMvc.perform(get("/api/applications/{id}", application.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(application.getId().intValue()))
            .andExpect(jsonPath("$.applicationCode").value(DEFAULT_APPLICATION_CODE.toString()))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN.toString()))
            .andExpect(jsonPath("$.projectType").value(DEFAULT_PROJECT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER.toString()));
    }

    @Test
    @Transactional
    public void getAllApplicationsByApplicationCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where applicationCode equals to DEFAULT_APPLICATION_CODE
        defaultApplicationShouldBeFound("applicationCode.equals=" + DEFAULT_APPLICATION_CODE);

        // Get all the applicationList where applicationCode equals to UPDATED_APPLICATION_CODE
        defaultApplicationShouldNotBeFound("applicationCode.equals=" + UPDATED_APPLICATION_CODE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByApplicationCodeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where applicationCode in DEFAULT_APPLICATION_CODE or UPDATED_APPLICATION_CODE
        defaultApplicationShouldBeFound("applicationCode.in=" + DEFAULT_APPLICATION_CODE + "," + UPDATED_APPLICATION_CODE);

        // Get all the applicationList where applicationCode equals to UPDATED_APPLICATION_CODE
        defaultApplicationShouldNotBeFound("applicationCode.in=" + UPDATED_APPLICATION_CODE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByApplicationCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where applicationCode is not null
        defaultApplicationShouldBeFound("applicationCode.specified=true");

        // Get all the applicationList where applicationCode is null
        defaultApplicationShouldNotBeFound("applicationCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByDomainIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where domain equals to DEFAULT_DOMAIN
        defaultApplicationShouldBeFound("domain.equals=" + DEFAULT_DOMAIN);

        // Get all the applicationList where domain equals to UPDATED_DOMAIN
        defaultApplicationShouldNotBeFound("domain.equals=" + UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void getAllApplicationsByDomainIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where domain in DEFAULT_DOMAIN or UPDATED_DOMAIN
        defaultApplicationShouldBeFound("domain.in=" + DEFAULT_DOMAIN + "," + UPDATED_DOMAIN);

        // Get all the applicationList where domain equals to UPDATED_DOMAIN
        defaultApplicationShouldNotBeFound("domain.in=" + UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void getAllApplicationsByDomainIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where domain is not null
        defaultApplicationShouldBeFound("domain.specified=true");

        // Get all the applicationList where domain is null
        defaultApplicationShouldNotBeFound("domain.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByProjectTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where projectType equals to DEFAULT_PROJECT_TYPE
        defaultApplicationShouldBeFound("projectType.equals=" + DEFAULT_PROJECT_TYPE);

        // Get all the applicationList where projectType equals to UPDATED_PROJECT_TYPE
        defaultApplicationShouldNotBeFound("projectType.equals=" + UPDATED_PROJECT_TYPE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByProjectTypeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where projectType in DEFAULT_PROJECT_TYPE or UPDATED_PROJECT_TYPE
        defaultApplicationShouldBeFound("projectType.in=" + DEFAULT_PROJECT_TYPE + "," + UPDATED_PROJECT_TYPE);

        // Get all the applicationList where projectType equals to UPDATED_PROJECT_TYPE
        defaultApplicationShouldNotBeFound("projectType.in=" + UPDATED_PROJECT_TYPE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByProjectTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where projectType is not null
        defaultApplicationShouldBeFound("projectType.specified=true");

        // Get all the applicationList where projectType is null
        defaultApplicationShouldNotBeFound("projectType.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where description equals to DEFAULT_DESCRIPTION
        defaultApplicationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the applicationList where description equals to UPDATED_DESCRIPTION
        defaultApplicationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultApplicationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the applicationList where description equals to UPDATED_DESCRIPTION
        defaultApplicationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where description is not null
        defaultApplicationShouldBeFound("description.specified=true");

        // Get all the applicationList where description is null
        defaultApplicationShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where owner equals to DEFAULT_OWNER
        defaultApplicationShouldBeFound("owner.equals=" + DEFAULT_OWNER);

        // Get all the applicationList where owner equals to UPDATED_OWNER
        defaultApplicationShouldNotBeFound("owner.equals=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    public void getAllApplicationsByOwnerIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where owner in DEFAULT_OWNER or UPDATED_OWNER
        defaultApplicationShouldBeFound("owner.in=" + DEFAULT_OWNER + "," + UPDATED_OWNER);

        // Get all the applicationList where owner equals to UPDATED_OWNER
        defaultApplicationShouldNotBeFound("owner.in=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    public void getAllApplicationsByOwnerIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where owner is not null
        defaultApplicationShouldBeFound("owner.specified=true");

        // Get all the applicationList where owner is null
        defaultApplicationShouldNotBeFound("owner.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsBySourceFeedIsEqualToSomething() throws Exception {
        // Initialize the database
        SourceFeed sourceFeed = SourceFeedResourceIntTest.createEntity(em);
        em.persist(sourceFeed);
        em.flush();
        application.addSourceFeed(sourceFeed);
        applicationRepository.saveAndFlush(application);
        Long sourceFeedId = sourceFeed.getId();

        // Get all the applicationList where sourceFeed equals to sourceFeedId
        defaultApplicationShouldBeFound("sourceFeedId.equals=" + sourceFeedId);

        // Get all the applicationList where sourceFeed equals to sourceFeedId + 1
        defaultApplicationShouldNotBeFound("sourceFeedId.equals=" + (sourceFeedId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicationsByDataAssetIsEqualToSomething() throws Exception {
        // Initialize the database
        DataAsset dataAsset = DataAssetResourceIntTest.createEntity(em);
        em.persist(dataAsset);
        em.flush();
        application.addDataAsset(dataAsset);
        applicationRepository.saveAndFlush(application);
        Long dataAssetId = dataAsset.getId();

        // Get all the applicationList where dataAsset equals to dataAssetId
        defaultApplicationShouldBeFound("dataAssetId.equals=" + dataAssetId);

        // Get all the applicationList where dataAsset equals to dataAssetId + 1
        defaultApplicationShouldNotBeFound("dataAssetId.equals=" + (dataAssetId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicationsBySourceDatabaseIsEqualToSomething() throws Exception {
        // Initialize the database
        SourceDatabase sourceDatabase = SourceDatabaseResourceIntTest.createEntity(em);
        em.persist(sourceDatabase);
        em.flush();
        application.addSourceDatabase(sourceDatabase);
        applicationRepository.saveAndFlush(application);
        Long sourceDatabaseId = sourceDatabase.getId();

        // Get all the applicationList where sourceDatabase equals to sourceDatabaseId
        defaultApplicationShouldBeFound("sourceDatabaseId.equals=" + sourceDatabaseId);

        // Get all the applicationList where sourceDatabase equals to sourceDatabaseId + 1
        defaultApplicationShouldNotBeFound("sourceDatabaseId.equals=" + (sourceDatabaseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultApplicationShouldBeFound(String filter) throws Exception {
        restApplicationMockMvc.perform(get("/api/applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(application.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationCode").value(hasItem(DEFAULT_APPLICATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN.toString())))
            .andExpect(jsonPath("$.[*].projectType").value(hasItem(DEFAULT_PROJECT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER.toString())));

        // Check, that the count call also returns 1
        restApplicationMockMvc.perform(get("/api/applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultApplicationShouldNotBeFound(String filter) throws Exception {
        restApplicationMockMvc.perform(get("/api/applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicationMockMvc.perform(get("/api/applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingApplication() throws Exception {
        // Get the application
        restApplicationMockMvc.perform(get("/api/applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplication() throws Exception {
        // Initialize the database
        applicationService.save(application);

        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Update the application
        Application updatedApplication = applicationRepository.findById(application.getId()).get();
        // Disconnect from session so that the updates on updatedApplication are not directly saved in db
        em.detach(updatedApplication);
        updatedApplication
            .applicationCode(UPDATED_APPLICATION_CODE)
            .domain(UPDATED_DOMAIN)
            .projectType(UPDATED_PROJECT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .owner(UPDATED_OWNER);

        restApplicationMockMvc.perform(put("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplication)))
            .andExpect(status().isOk());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getApplicationCode()).isEqualTo(UPDATED_APPLICATION_CODE);
        assertThat(testApplication.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testApplication.getProjectType()).isEqualTo(UPDATED_PROJECT_TYPE);
        assertThat(testApplication.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApplication.getOwner()).isEqualTo(UPDATED_OWNER);
    }

    @Test
    @Transactional
    public void updateNonExistingApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Create the Application

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationMockMvc.perform(put("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplication() throws Exception {
        // Initialize the database
        applicationService.save(application);

        int databaseSizeBeforeDelete = applicationRepository.findAll().size();

        // Get the application
        restApplicationMockMvc.perform(delete("/api/applications/{id}", application.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Application.class);
        Application application1 = new Application();
        application1.setId(1L);
        Application application2 = new Application();
        application2.setId(application1.getId());
        assertThat(application1).isEqualTo(application2);
        application2.setId(2L);
        assertThat(application1).isNotEqualTo(application2);
        application1.setId(null);
        assertThat(application1).isNotEqualTo(application2);
    }
}
