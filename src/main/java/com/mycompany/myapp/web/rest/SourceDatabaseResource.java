package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.SourceDatabase;
import com.mycompany.myapp.service.SourceDatabaseService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.SourceDatabaseCriteria;
import com.mycompany.myapp.service.SourceDatabaseQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SourceDatabase.
 */
@RestController
@RequestMapping("/api")
public class SourceDatabaseResource {

    private final Logger log = LoggerFactory.getLogger(SourceDatabaseResource.class);

    private static final String ENTITY_NAME = "sourceDatabase";

    private final SourceDatabaseService sourceDatabaseService;

    private final SourceDatabaseQueryService sourceDatabaseQueryService;

    public SourceDatabaseResource(SourceDatabaseService sourceDatabaseService, SourceDatabaseQueryService sourceDatabaseQueryService) {
        this.sourceDatabaseService = sourceDatabaseService;
        this.sourceDatabaseQueryService = sourceDatabaseQueryService;
    }

    /**
     * POST  /source-databases : Create a new sourceDatabase.
     *
     * @param sourceDatabase the sourceDatabase to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sourceDatabase, or with status 400 (Bad Request) if the sourceDatabase has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/source-databases")
    @Timed
    public ResponseEntity<SourceDatabase> createSourceDatabase(@Valid @RequestBody SourceDatabase sourceDatabase) throws URISyntaxException {
        log.debug("REST request to save SourceDatabase : {}", sourceDatabase);
        if (sourceDatabase.getId() != null) {
            throw new BadRequestAlertException("A new sourceDatabase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SourceDatabase result = sourceDatabaseService.save(sourceDatabase);
        return ResponseEntity.created(new URI("/api/source-databases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /source-databases : Updates an existing sourceDatabase.
     *
     * @param sourceDatabase the sourceDatabase to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sourceDatabase,
     * or with status 400 (Bad Request) if the sourceDatabase is not valid,
     * or with status 500 (Internal Server Error) if the sourceDatabase couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/source-databases")
    @Timed
    public ResponseEntity<SourceDatabase> updateSourceDatabase(@Valid @RequestBody SourceDatabase sourceDatabase) throws URISyntaxException {
        log.debug("REST request to update SourceDatabase : {}", sourceDatabase);
        if (sourceDatabase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SourceDatabase result = sourceDatabaseService.save(sourceDatabase);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sourceDatabase.getId().toString()))
            .body(result);
    }

    /**
     * GET  /source-databases : get all the sourceDatabases.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of sourceDatabases in body
     */
    @GetMapping("/source-databases")
    @Timed
    public ResponseEntity<List<SourceDatabase>> getAllSourceDatabases(SourceDatabaseCriteria criteria) {
        log.debug("REST request to get SourceDatabases by criteria: {}", criteria);
        List<SourceDatabase> entityList = sourceDatabaseQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /source-databases/count : count all the sourceDatabases.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/source-databases/count")
    @Timed
    public ResponseEntity<Long> countSourceDatabases (SourceDatabaseCriteria criteria) {
        log.debug("REST request to count SourceDatabases by criteria: {}", criteria);
        return ResponseEntity.ok().body(sourceDatabaseQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /source-databases/:id : get the "id" sourceDatabase.
     *
     * @param id the id of the sourceDatabase to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sourceDatabase, or with status 404 (Not Found)
     */
    @GetMapping("/source-databases/{id}")
    @Timed
    public ResponseEntity<SourceDatabase> getSourceDatabase(@PathVariable Long id) {
        log.debug("REST request to get SourceDatabase : {}", id);
        Optional<SourceDatabase> sourceDatabase = sourceDatabaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sourceDatabase);
    }

    /**
     * DELETE  /source-databases/:id : delete the "id" sourceDatabase.
     *
     * @param id the id of the sourceDatabase to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/source-databases/{id}")
    @Timed
    public ResponseEntity<Void> deleteSourceDatabase(@PathVariable Long id) {
        log.debug("REST request to delete SourceDatabase : {}", id);
        sourceDatabaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
