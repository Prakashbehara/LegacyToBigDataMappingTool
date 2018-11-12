package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.DataAsset;
import com.mycompany.myapp.service.DataAssetService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.DataAssetCriteria;
import com.mycompany.myapp.service.DataAssetQueryService;
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
 * REST controller for managing DataAsset.
 */
@RestController
@RequestMapping("/api")
public class DataAssetResource {

    private final Logger log = LoggerFactory.getLogger(DataAssetResource.class);

    private static final String ENTITY_NAME = "dataAsset";

    private final DataAssetService dataAssetService;

    private final DataAssetQueryService dataAssetQueryService;

    public DataAssetResource(DataAssetService dataAssetService, DataAssetQueryService dataAssetQueryService) {
        this.dataAssetService = dataAssetService;
        this.dataAssetQueryService = dataAssetQueryService;
    }

    /**
     * POST  /data-assets : Create a new dataAsset.
     *
     * @param dataAsset the dataAsset to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dataAsset, or with status 400 (Bad Request) if the dataAsset has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/data-assets")
    @Timed
    public ResponseEntity<DataAsset> createDataAsset(@Valid @RequestBody DataAsset dataAsset) throws URISyntaxException {
        log.debug("REST request to save DataAsset : {}", dataAsset);
        if (dataAsset.getId() != null) {
            throw new BadRequestAlertException("A new dataAsset cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataAsset result = dataAssetService.save(dataAsset);
        return ResponseEntity.created(new URI("/api/data-assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /data-assets : Updates an existing dataAsset.
     *
     * @param dataAsset the dataAsset to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dataAsset,
     * or with status 400 (Bad Request) if the dataAsset is not valid,
     * or with status 500 (Internal Server Error) if the dataAsset couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/data-assets")
    @Timed
    public ResponseEntity<DataAsset> updateDataAsset(@Valid @RequestBody DataAsset dataAsset) throws URISyntaxException {
        log.debug("REST request to update DataAsset : {}", dataAsset);
        if (dataAsset.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataAsset result = dataAssetService.save(dataAsset);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dataAsset.getId().toString()))
            .body(result);
    }

    /**
     * GET  /data-assets : get all the dataAssets.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of dataAssets in body
     */
    @GetMapping("/data-assets")
    @Timed
    public ResponseEntity<List<DataAsset>> getAllDataAssets(DataAssetCriteria criteria) {
        log.debug("REST request to get DataAssets by criteria: {}", criteria);
        List<DataAsset> entityList = dataAssetQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /data-assets/count : count all the dataAssets.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/data-assets/count")
    @Timed
    public ResponseEntity<Long> countDataAssets (DataAssetCriteria criteria) {
        log.debug("REST request to count DataAssets by criteria: {}", criteria);
        return ResponseEntity.ok().body(dataAssetQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /data-assets/:id : get the "id" dataAsset.
     *
     * @param id the id of the dataAsset to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dataAsset, or with status 404 (Not Found)
     */
    @GetMapping("/data-assets/{id}")
    @Timed
    public ResponseEntity<DataAsset> getDataAsset(@PathVariable Long id) {
        log.debug("REST request to get DataAsset : {}", id);
        Optional<DataAsset> dataAsset = dataAssetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataAsset);
    }

    /**
     * DELETE  /data-assets/:id : delete the "id" dataAsset.
     *
     * @param id the id of the dataAsset to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/data-assets/{id}")
    @Timed
    public ResponseEntity<Void> deleteDataAsset(@PathVariable Long id) {
        log.debug("REST request to delete DataAsset : {}", id);
        dataAssetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
