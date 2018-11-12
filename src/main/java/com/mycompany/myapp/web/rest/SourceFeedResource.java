package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.SourceFeed;
import com.mycompany.myapp.service.SourceFeedService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.SourceFeedCriteria;
import com.mycompany.myapp.service.SourceFeedQueryService;
import io.github.jhipster.service.filter.LongFilter;
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
 * REST controller for managing SourceFeed.
 */
@RestController
@RequestMapping("/api")
public class SourceFeedResource {

    private final Logger log = LoggerFactory.getLogger(SourceFeedResource.class);

    private static final String ENTITY_NAME = "sourceFeed";

    private final SourceFeedService sourceFeedService;

    private final SourceFeedQueryService sourceFeedQueryService;

    public SourceFeedResource(SourceFeedService sourceFeedService, SourceFeedQueryService sourceFeedQueryService) {
        this.sourceFeedService = sourceFeedService;
        this.sourceFeedQueryService = sourceFeedQueryService;
    }

    /**
     * POST  /source-feeds : Create a new sourceFeed.
     *
     * @param sourceFeed the sourceFeed to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sourceFeed, or with status 400 (Bad Request) if the sourceFeed has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/source-feeds")
    @Timed
    public ResponseEntity<SourceFeed> createSourceFeed(@Valid @RequestBody SourceFeed sourceFeed) throws URISyntaxException {
        log.debug("REST request to save SourceFeed : {}", sourceFeed);
        if (sourceFeed.getId() != null) {
            throw new BadRequestAlertException("A new sourceFeed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SourceFeed result = sourceFeedService.save(sourceFeed);
        return ResponseEntity.created(new URI("/api/source-feeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /source-feeds : Updates an existing sourceFeed.
     *
     * @param sourceFeed the sourceFeed to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sourceFeed,
     * or with status 400 (Bad Request) if the sourceFeed is not valid,
     * or with status 500 (Internal Server Error) if the sourceFeed couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/source-feeds")
    @Timed
    public ResponseEntity<SourceFeed> updateSourceFeed(@Valid @RequestBody SourceFeed sourceFeed) throws URISyntaxException {
        log.debug("REST request to update SourceFeed : {}", sourceFeed);
        if (sourceFeed.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SourceFeed result = sourceFeedService.save(sourceFeed);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sourceFeed.getId().toString()))
            .body(result);
    }

    /**
     * GET  /source-feeds : get all the sourceFeeds.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of sourceFeeds in body
     */
    @GetMapping("/source-feeds")
    @Timed
    public ResponseEntity<List<SourceFeed>> getAllSourceFeeds(SourceFeedCriteria criteria) {
        log.debug("REST request to get SourceFeeds by criteria: {}", criteria);
        //LongFilter appID = new LongFilter();
        //appID.setEquals(66L);
        //criteria.setApplicationId(appID);
        List<SourceFeed> entityList = sourceFeedQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /source-feeds/count : count all the sourceFeeds.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/source-feeds/count")
    @Timed
    public ResponseEntity<Long> countSourceFeeds (SourceFeedCriteria criteria) {
        log.debug("REST request to count SourceFeeds by criteria: {}", criteria);
        return ResponseEntity.ok().body(sourceFeedQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /source-feeds/:id : get the "id" sourceFeed.
     *
     * @param id the id of the sourceFeed to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sourceFeed, or with status 404 (Not Found)
     */
    @GetMapping("/source-feeds/{id}")
    @Timed
    public ResponseEntity<SourceFeed> getSourceFeed(@PathVariable Long id) {
        log.debug("REST request to get SourceFeed : {}", id);
        Optional<SourceFeed> sourceFeed = sourceFeedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sourceFeed);
    }

    /**
     * DELETE  /source-feeds/:id : delete the "id" sourceFeed.
     *
     * @param id the id of the sourceFeed to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/source-feeds/{id}")
    @Timed
    public ResponseEntity<Void> deleteSourceFeed(@PathVariable Long id) {
        log.debug("REST request to delete SourceFeed : {}", id);
        sourceFeedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
