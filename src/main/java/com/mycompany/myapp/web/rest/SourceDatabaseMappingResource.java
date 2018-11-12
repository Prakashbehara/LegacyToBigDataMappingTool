package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.SourceDatabaseMapping;
import com.mycompany.myapp.service.SourceDatabaseMappingService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.SourceDatabaseMappingCriteria;
import com.mycompany.myapp.service.SourceDatabaseMappingQueryService;
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
 * REST controller for managing SourceDatabaseMapping.
 */
@RestController
@RequestMapping("/api")
public class SourceDatabaseMappingResource {

    private final Logger log = LoggerFactory.getLogger(SourceDatabaseMappingResource.class);

    private static final String ENTITY_NAME = "sourceDatabaseMapping";

    private final SourceDatabaseMappingService sourceDatabaseMappingService;

    private final SourceDatabaseMappingQueryService sourceDatabaseMappingQueryService;

    public SourceDatabaseMappingResource(SourceDatabaseMappingService sourceDatabaseMappingService, SourceDatabaseMappingQueryService sourceDatabaseMappingQueryService) {
        this.sourceDatabaseMappingService = sourceDatabaseMappingService;
        this.sourceDatabaseMappingQueryService = sourceDatabaseMappingQueryService;
    }

    /**
     * POST  /source-database-mappings : Create a new sourceDatabaseMapping.
     *
     * @param sourceDatabaseMapping the sourceDatabaseMapping to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sourceDatabaseMapping, or with status 400 (Bad Request) if the sourceDatabaseMapping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/source-database-mappings")
    @Timed
    public ResponseEntity<SourceDatabaseMapping> createSourceDatabaseMapping(@Valid @RequestBody SourceDatabaseMapping sourceDatabaseMapping) throws URISyntaxException {
        log.debug("REST request to save SourceDatabaseMapping : {}", sourceDatabaseMapping);
        if (sourceDatabaseMapping.getId() != null) {
            throw new BadRequestAlertException("A new sourceDatabaseMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SourceDatabaseMapping result = sourceDatabaseMappingService.save(sourceDatabaseMapping);
        return ResponseEntity.created(new URI("/api/source-database-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /source-database-mappings : Updates an existing sourceDatabaseMapping.
     *
     * @param sourceDatabaseMapping the sourceDatabaseMapping to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sourceDatabaseMapping,
     * or with status 400 (Bad Request) if the sourceDatabaseMapping is not valid,
     * or with status 500 (Internal Server Error) if the sourceDatabaseMapping couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/source-database-mappings")
    @Timed
    public ResponseEntity<SourceDatabaseMapping> updateSourceDatabaseMapping(@Valid @RequestBody SourceDatabaseMapping sourceDatabaseMapping) throws URISyntaxException {
        log.debug("REST request to update SourceDatabaseMapping : {}", sourceDatabaseMapping);
        if (sourceDatabaseMapping.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SourceDatabaseMapping result = sourceDatabaseMappingService.save(sourceDatabaseMapping);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sourceDatabaseMapping.getId().toString()))
            .body(result);
    }

    /**
     * GET  /source-database-mappings : get all the sourceDatabaseMappings.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of sourceDatabaseMappings in body
     */
    @GetMapping("/source-database-mappings")
    @Timed
    public ResponseEntity<List<SourceDatabaseMapping>> getAllSourceDatabaseMappings(SourceDatabaseMappingCriteria criteria) {
        log.debug("REST request to get SourceDatabaseMappings by criteria: {}", criteria);
        List<SourceDatabaseMapping> entityList = sourceDatabaseMappingQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /source-database-mappings/count : count all the sourceDatabaseMappings.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/source-database-mappings/count")
    @Timed
    public ResponseEntity<Long> countSourceDatabaseMappings (SourceDatabaseMappingCriteria criteria) {
        log.debug("REST request to count SourceDatabaseMappings by criteria: {}", criteria);
        return ResponseEntity.ok().body(sourceDatabaseMappingQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /source-database-mappings/:id : get the "id" sourceDatabaseMapping.
     *
     * @param id the id of the sourceDatabaseMapping to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sourceDatabaseMapping, or with status 404 (Not Found)
     */
    @GetMapping("/source-database-mappings/{id}")
    @Timed
    public ResponseEntity<SourceDatabaseMapping> getSourceDatabaseMapping(@PathVariable Long id) {
        log.debug("REST request to get SourceDatabaseMapping : {}", id);
        Optional<SourceDatabaseMapping> sourceDatabaseMapping = sourceDatabaseMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sourceDatabaseMapping);
    }

    /**
     * DELETE  /source-database-mappings/:id : delete the "id" sourceDatabaseMapping.
     *
     * @param id the id of the sourceDatabaseMapping to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/source-database-mappings/{id}")
    @Timed
    public ResponseEntity<Void> deleteSourceDatabaseMapping(@PathVariable Long id) {
        log.debug("REST request to delete SourceDatabaseMapping : {}", id);
        sourceDatabaseMappingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
