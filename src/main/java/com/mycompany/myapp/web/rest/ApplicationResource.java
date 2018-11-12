package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Application;
import com.mycompany.myapp.service.ApplicationService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.ApplicationCriteria;
import com.mycompany.myapp.service.ApplicationQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Application.
 */
@RestController
@RequestMapping("/api")
public class ApplicationResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationResource.class);

    private static final String ENTITY_NAME = "application";

    private final ApplicationService applicationService;

    private final ApplicationQueryService applicationQueryService;

    public ApplicationResource(ApplicationService applicationService, ApplicationQueryService applicationQueryService) {
        this.applicationService = applicationService;
        this.applicationQueryService = applicationQueryService;
    }

    /**
     * POST  /applications : Create a new application.
     *
     * @param application the application to create
     * @return the ResponseEntity with status 201 (Created) and with body the new application, or with status 400 (Bad Request) if the application has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/applications")
    @Timed
    public ResponseEntity<Application> createApplication(@Valid @RequestBody Application application) throws URISyntaxException {
        log.debug("REST request to save Application : {}", application);
        if (application.getId() != null) {
            throw new BadRequestAlertException("A new application cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Application result = applicationService.save(application);
        return ResponseEntity.created(new URI("/api/applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /applications : Updates an existing application.
     *
     * @param application the application to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated application,
     * or with status 400 (Bad Request) if the application is not valid,
     * or with status 500 (Internal Server Error) if the application couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/applications")
    @Timed
    public ResponseEntity<Application> updateApplication(@Valid @RequestBody Application application) throws URISyntaxException {
        log.debug("REST request to update Application : {}", application);
        if (application.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Application result = applicationService.save(application);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, application.getId().toString()))
            .body(result);
    }

    /**
     * GET  /applications : get all the applications.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of applications in body
     */
    @GetMapping("/applications")
    @Timed
    public ResponseEntity<List<Application>> getAllApplications(ApplicationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Applications by criteria: {}", criteria);
        Page<Application> page = applicationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/applications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
    * GET  /applications/count : count all the applications.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/applications/count")
    @Timed
    public ResponseEntity<Long> countApplications (ApplicationCriteria criteria) {
        log.debug("REST request to count Applications by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /applications/:id : get the "id" application.
     *
     * @param id the id of the application to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the application, or with status 404 (Not Found)
     */
    @GetMapping("/applications/{id}")
    @Timed
    public ResponseEntity<Application> getApplication(@PathVariable Long id) {
        log.debug("REST request to get Application : {}", id);
        Optional<Application> application = applicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(application);
    }

    /**
     * DELETE  /applications/:id : delete the "id" application.
     *
     * @param id the id of the application to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/applications/{id}")
    @Timed
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        log.debug("REST request to delete Application : {}", id);
        applicationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
