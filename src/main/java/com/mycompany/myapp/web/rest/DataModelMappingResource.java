package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.DataModelMapping;
import com.mycompany.myapp.service.DataModelMappingService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.DataModelMappingCriteria;
import com.mycompany.myapp.service.DataModelMappingQueryService;
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
 * REST controller for managing DataModelMapping.
 */
@RestController
@RequestMapping("/api")
public class DataModelMappingResource {

    private final Logger log = LoggerFactory.getLogger(DataModelMappingResource.class);

    private static final String ENTITY_NAME = "dataModelMapping";

    private final DataModelMappingService dataModelMappingService;

    private final DataModelMappingQueryService dataModelMappingQueryService;

    public DataModelMappingResource(DataModelMappingService dataModelMappingService, DataModelMappingQueryService dataModelMappingQueryService) {
        this.dataModelMappingService = dataModelMappingService;
        this.dataModelMappingQueryService = dataModelMappingQueryService;
    }

    /**
     * POST  /data-model-mappings : Create a new dataModelMapping.
     *
     * @param dataModelMapping the dataModelMapping to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dataModelMapping, or with status 400 (Bad Request) if the dataModelMapping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/data-model-mappings")
    @Timed
    public ResponseEntity<DataModelMapping> createDataModelMapping(@Valid @RequestBody DataModelMapping dataModelMapping) throws URISyntaxException {
        log.debug("REST request to save DataModelMapping : {}", dataModelMapping);
        if (dataModelMapping.getId() != null) {
            throw new BadRequestAlertException("A new dataModelMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataModelMapping result = dataModelMappingService.save(dataModelMapping);
        return ResponseEntity.created(new URI("/api/data-model-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /data-model-mappings : Updates an existing dataModelMapping.
     *
     * @param dataModelMapping the dataModelMapping to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dataModelMapping,
     * or with status 400 (Bad Request) if the dataModelMapping is not valid,
     * or with status 500 (Internal Server Error) if the dataModelMapping couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/data-model-mappings")
    @Timed
    public ResponseEntity<DataModelMapping> updateDataModelMapping(@Valid @RequestBody DataModelMapping dataModelMapping) throws URISyntaxException {
        log.debug("REST request to update DataModelMapping : {}", dataModelMapping);
        if (dataModelMapping.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataModelMapping result = dataModelMappingService.save(dataModelMapping);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dataModelMapping.getId().toString()))
            .body(result);
    }

    /**
     * GET  /data-model-mappings : get all the dataModelMappings.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of dataModelMappings in body
     */
    @GetMapping("/data-model-mappings")
    @Timed
    public ResponseEntity<List<DataModelMapping>> getAllDataModelMappings(DataModelMappingCriteria criteria) {
        log.debug("REST request to get DataModelMappings by criteria: {}", criteria);
        List<DataModelMapping> entityList = dataModelMappingQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /data-model-mappings/count : count all the dataModelMappings.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/data-model-mappings/count")
    @Timed
    public ResponseEntity<Long> countDataModelMappings (DataModelMappingCriteria criteria) {
        log.debug("REST request to count DataModelMappings by criteria: {}", criteria);
        return ResponseEntity.ok().body(dataModelMappingQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /data-model-mappings/:id : get the "id" dataModelMapping.
     *
     * @param id the id of the dataModelMapping to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dataModelMapping, or with status 404 (Not Found)
     */
    @GetMapping("/data-model-mappings/{id}")
    @Timed
    public ResponseEntity<DataModelMapping> getDataModelMapping(@PathVariable Long id) {
        log.debug("REST request to get DataModelMapping : {}", id);
        Optional<DataModelMapping> dataModelMapping = dataModelMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataModelMapping);
    }

    /**
     * DELETE  /data-model-mappings/:id : delete the "id" dataModelMapping.
     *
     * @param id the id of the dataModelMapping to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/data-model-mappings/{id}")
    @Timed
    public ResponseEntity<Void> deleteDataModelMapping(@PathVariable Long id) {
        log.debug("REST request to delete DataModelMapping : {}", id);
        dataModelMappingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
