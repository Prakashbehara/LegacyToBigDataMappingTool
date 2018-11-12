package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.SourceFeedMapping;
import com.mycompany.myapp.service.SourceFeedMappingService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.SourceFeedMappingCriteria;
import com.mycompany.myapp.service.SourceFeedMappingQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SourceFeedMapping.
 */
@RestController
@RequestMapping("/api")
public class SourceFeedMappingResource {

    private final Logger log = LoggerFactory.getLogger(SourceFeedMappingResource.class);

    private static final String ENTITY_NAME = "sourceFeedMapping";

    private final SourceFeedMappingService sourceFeedMappingService;

    private final SourceFeedMappingQueryService sourceFeedMappingQueryService;

    public SourceFeedMappingResource(SourceFeedMappingService sourceFeedMappingService, SourceFeedMappingQueryService sourceFeedMappingQueryService) {
        this.sourceFeedMappingService = sourceFeedMappingService;
        this.sourceFeedMappingQueryService = sourceFeedMappingQueryService;
    }

    /**
     * POST  /source-feed-mappings : Create a new sourceFeedMapping.
     *
     * @param sourceFeedMapping the sourceFeedMapping to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sourceFeedMapping, or with status 400 (Bad Request) if the sourceFeedMapping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/source-feed-mappings")
    @Timed
    public ResponseEntity<SourceFeedMapping> createSourceFeedMapping(@Valid @RequestBody SourceFeedMapping sourceFeedMapping) throws URISyntaxException {
        log.debug("REST request to save SourceFeedMapping : {}", sourceFeedMapping);
        if (sourceFeedMapping.getId() != null) {
            throw new BadRequestAlertException("A new sourceFeedMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SourceFeedMapping result = sourceFeedMappingService.save(sourceFeedMapping);
        return ResponseEntity.created(new URI("/api/source-feed-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /source-feed-mappings : Updates an existing sourceFeedMapping.
     *
     * @param sourceFeedMapping the sourceFeedMapping to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sourceFeedMapping,
     * or with status 400 (Bad Request) if the sourceFeedMapping is not valid,
     * or with status 500 (Internal Server Error) if the sourceFeedMapping couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/source-feed-mappings")
    @Timed
    public ResponseEntity<SourceFeedMapping> updateSourceFeedMapping(@Valid @RequestBody SourceFeedMapping sourceFeedMapping) throws URISyntaxException {
        log.debug("REST request to update SourceFeedMapping : {}", sourceFeedMapping);
        if (sourceFeedMapping.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SourceFeedMapping result = sourceFeedMappingService.save(sourceFeedMapping);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sourceFeedMapping.getId().toString()))
            .body(result);
    }

    /**
     * GET  /source-feed-mappings : get all the sourceFeedMappings.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of sourceFeedMappings in body
     */
    @GetMapping("/source-feed-mappings")
    @Timed
    public ResponseEntity<List<SourceFeedMapping>> getAllSourceFeedMappings(SourceFeedMappingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SourceFeedMappings by criteria: {}", criteria);
        Page<SourceFeedMapping> page = sourceFeedMappingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/source-feed-mappings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
    * GET  /source-feed-mappings/count : count all the sourceFeedMappings.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/source-feed-mappings/count")
    @Timed
    public ResponseEntity<Long> countSourceFeedMappings (SourceFeedMappingCriteria criteria) {
        log.debug("REST request to count SourceFeedMappings by criteria: {}", criteria);
        return ResponseEntity.ok().body(sourceFeedMappingQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /source-feed-mappings/:id : get the "id" sourceFeedMapping.
     *
     * @param id the id of the sourceFeedMapping to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sourceFeedMapping, or with status 404 (Not Found)
     */
    @GetMapping("/source-feed-mappings/{id}")
    @Timed
    public ResponseEntity<SourceFeedMapping> getSourceFeedMapping(@PathVariable Long id) {
        log.debug("REST request to get SourceFeedMapping : {}", id);
        Optional<SourceFeedMapping> sourceFeedMapping = sourceFeedMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sourceFeedMapping);
    }

    /**
     * DELETE  /source-feed-mappings/:id : delete the "id" sourceFeedMapping.
     *
     * @param id the id of the sourceFeedMapping to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/source-feed-mappings/{id}")
    @Timed
    public ResponseEntity<Void> deleteSourceFeedMapping(@PathVariable Long id) {
        log.debug("REST request to delete SourceFeedMapping : {}", id);
        sourceFeedMappingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/source-feed-mappings/upload")
    @Timed
    public ResponseEntity<SourceFeedMapping>  uploadFile(@RequestPart(value = "file") MultipartFile multiPartFile) throws IOException {
        File convFile = new File(multiPartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multiPartFile.getBytes());
        fos.close();
        log.debug("REST request to uploadFile SourceFeedMapping : {}", convFile.getName());
        return null;
    }


}
